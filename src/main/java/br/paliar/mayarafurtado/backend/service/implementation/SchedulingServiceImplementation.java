package br.paliar.mayarafurtado.backend.service.implementation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationModel;
import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationRole;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingModel;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingRequestDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingResponseDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingUpdateRequestDTO;
import br.paliar.mayarafurtado.backend.repository.ReactivationRepository;
import br.paliar.mayarafurtado.backend.repository.SchedulingRepository;
import br.paliar.mayarafurtado.backend.service.SchedulingService;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;
import java.time.Duration;

@Service
public class SchedulingServiceImplementation implements SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private final ReactivationRepository reactivationRepository;
    private final ModelMapper modelMapper;
    private final int START_HOUR = 8;  // Horário de início: 08h
    private final int END_HOUR = 18;   // Horário de término: 18h

    @Autowired
    public SchedulingServiceImplementation(SchedulingRepository schedulingRepository, ReactivationRepository reactivationRepository, ModelMapper modelMapper) {
        this.schedulingRepository = schedulingRepository;
        this.reactivationRepository = reactivationRepository;
        this.modelMapper = modelMapper;
    }

    public SchedulingResponseDTO save(SchedulingRequestDTO schedulingRequestDTO) {
        SchedulingModel schedulingModel = modelMapper.map(schedulingRequestDTO, SchedulingModel.class);
        
        if (schedulingModel.getPatient() == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
    
        LocalDateTime scheduled = schedulingModel.getScheduled();
        LocalDateTime now = LocalDateTime.now();
    
        // Business rule: A data e hora agendada não pode ser anterior à data e hora atual
        if (scheduled.isBefore(now)) {
            throw new IllegalArgumentException("A data e hora agendada não pode ser anterior à data e hora atual");
        }
    
        // Buscar todos os agendamentos do mesmo dia
        LocalDateTime startOfDay = scheduled.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = scheduled.toLocalDate().atTime(23, 59, 59);
        List<SchedulingModel> schedulingsOnSameDay = schedulingRepository.findByScheduledBetween(startOfDay, endOfDay);
    
        // Business rule: Verificar se há um agendamento em um intervalo menor que 59 minutos
        for (SchedulingModel existingScheduling : schedulingsOnSameDay) {
            if (Math.abs(Duration.between(existingScheduling.getScheduled(), scheduled).toMinutes()) < 59) {
                throw new IllegalArgumentException("Conflito, há um paciente agendado neste horário");
            }
        }
    
        // Business rule: Verificar se já existe um agendamento para o paciente nesse horário
        boolean exists = schedulingRepository.existsByPatientIdAndScheduled(schedulingModel.getPatient().getId(), scheduled);
        if (exists) {
            throw new IllegalArgumentException("Já existe um agendamento para o paciente nesse horário");
        }
    
        // Salvar o agendamento e retornar a resposta
        return toResponse(schedulingRepository.save(schedulingModel));
    }
    
    @Transactional
    public SchedulingResponseDTO update(String id, SchedulingUpdateRequestDTO schedulingRequestDTO) {
        SchedulingModel schedulingModel = schedulingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        schedulingModel.setScheduled(schedulingRequestDTO.getScheduled());
        schedulingModel.setAttended(schedulingRequestDTO.getAttended());

        if (schedulingRequestDTO.getAttended()) {
            handlePatientReactivation(schedulingModel);
        }

        return toResponse(schedulingRepository.save(schedulingModel));
    }

    private void handlePatientReactivation(SchedulingModel schedulingModel) {
        Optional<ReactivationModel> existingReactivation = 
                reactivationRepository.findByPatientId(schedulingModel.getPatient().getId());
        ReactivationModel reactivation = existingReactivation.orElse(new ReactivationModel());
        reactivation.setLastService(schedulingModel.getScheduled());
        reactivation.setClassification(ReactivationRole.HOT); // Classificação HOT (paciente quente)
        reactivation.setPatient(schedulingModel.getPatient());
        reactivation.setReactivateIn(calculateReactivationDate(schedulingModel.getScheduled()));
        reactivationRepository.save(reactivation);
    }

    public void delete(String id) {
        if (!schedulingRepository.existsById(id)) {
            throw new IllegalArgumentException("Agendamento não encontrado");
        }
        schedulingRepository.deleteById(id);
    }

    public List<SchedulingResponseDTO> findAll() {
        List<SchedulingModel> schedulings = schedulingRepository.findAll();
        if (schedulings.isEmpty()) {
            throw new IllegalArgumentException("Agendamento não encontrado");
        }
        return schedulings.stream()
                // Business rule: Ordena os agendamentos por data e hora agendada, do mais recente para o mais antigo
                .sorted(Comparator.comparing(SchedulingModel::getScheduled).reversed())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SchedulingResponseDTO findByScheduled(LocalDateTime scheduled) {
        SchedulingModel scheduling = schedulingRepository.findByScheduled(scheduled);
        if (scheduling == null) {
            throw new IllegalArgumentException("Agendamento não encontrado");
        }
        return toResponse(scheduling);
    }

    public SchedulingResponseDTO findByPatientId(String patientId) {
        SchedulingModel scheduling = schedulingRepository.findByPatientId(patientId);
        if (scheduling == null) {
            throw new IllegalArgumentException("Agendamento não encontrado");
        }
        return toResponse(scheduling);
    }

    public Map<String, String> getAvailableTimesForMonth() {
        Map<String, String> availableTimes = new HashMap<>();
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
    
        // Data inicial será a data atual se estivermos no mês atual
        LocalDate startDate = LocalDate.of(year, month, 1);
        if (startDate.isBefore(today)) {
            startDate = today; // Começa a partir da data atual se a data inicial for passada
        }
    
        // Último dia do mês
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
    
        // Formato de data dd/MM
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
        // Loop através de cada dia do mês
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Ignorar sábados e domingos
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
    
            // Gerar horários disponíveis para o dia
            List<LocalDateTime> availableSlots = getAvailableSlotsForDay(date);
    
            if (!availableSlots.isEmpty()) {
                // Formatar os horários disponíveis como "08:00 - 09:00 - 10:00"
                String formattedSlots = availableSlots.stream()
                        .map(slot -> slot.format(timeFormatter))
                        .reduce((slot1, slot2) -> slot1 + " - " + slot2)
                        .orElse("");
    
                // Adicionar ao mapa com o formato "dd/MM: 08:00 - 09:00 - 10:00"
                availableTimes.put(date.format(dateFormatter), formattedSlots);
            }
        }
    
        return availableTimes;
    }
    
    // Gera os horários disponíveis para um dia
    private List<LocalDateTime> getAvailableSlotsForDay(LocalDate date) {
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime startTime = date.atTime(START_HOUR, 0); // Começa às 08:00
        LocalDateTime endTime = date.atTime(END_HOUR, 0);     // Termina às 18:00
    
        // Obter os horários já ocupados para o dia
        List<LocalDateTime> occupiedSlots = getOccupiedSlotsForDay(date);
    
        // Loop a cada 1 hora
        while (startTime.isBefore(endTime)) {
            // Verifica se o horário está ocupado
            if (!occupiedSlots.contains(startTime)) {
                availableSlots.add(startTime); // Adiciona à lista se não estiver ocupado
            }
            startTime = startTime.plus(1, ChronoUnit.HOURS); // Intervalo de 1 hora
        }
    
        return availableSlots;
    }
    
    // Método para buscar os horários ocupados em um dia
    private List<LocalDateTime> getOccupiedSlotsForDay(LocalDate date) {
        // Cria a lista de horários ocupados
        List<LocalDateTime> occupiedSlots = new ArrayList<>();
        
        // Define o início e fim do dia para o qual deseja buscar os agendamentos
        LocalDateTime startOfDay = date.atStartOfDay(); // 00:00 do dia
        LocalDateTime endOfDay = date.atTime(23, 59, 59); // 23:59 do dia

        // Busca os agendamentos entre o início e o fim do dia
        List<SchedulingModel> schedulingsOnDay = schedulingRepository.findByScheduledBetween(startOfDay, endOfDay);

        // Itera sobre os agendamentos encontrados e adiciona o horário do agendamento à lista de horários ocupados
        for (SchedulingModel scheduling : schedulingsOnDay) {
            occupiedSlots.add(scheduling.getScheduled());
        }

        // Retorna a lista de horários ocupados
        return occupiedSlots;
    }


    private SchedulingResponseDTO toResponse(SchedulingModel scheduling) {
        SchedulingResponseDTO response = modelMapper.map(scheduling, SchedulingResponseDTO.class);
        
        if (scheduling.getPatient() != null) {
            response.setPatientName(scheduling.getPatient().getName());
            response.setPatientEmail(scheduling.getPatient().getEmail());
            response.setPatientPhone(scheduling.getPatient().getPhone());
        }
        
        return response;
    }

    private LocalDate calculateReactivationDate(LocalDateTime lastService) {
    // Business rule: A reativação deve ser feita 1 ano (12 meses) após o último serviço
    return lastService.toLocalDate().plusMonths(12);
    }

}
