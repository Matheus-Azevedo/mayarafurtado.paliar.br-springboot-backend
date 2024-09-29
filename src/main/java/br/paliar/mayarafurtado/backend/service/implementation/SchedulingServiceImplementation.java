package br.paliar.mayarafurtado.backend.service.implementation;

import java.util.Comparator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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

@Service
public class SchedulingServiceImplementation implements SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private final ReactivationRepository reactivationRepository;
    private final ModelMapper modelMapper;

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

        // Business rule: A data e hora agendada não pode ser anterior a data e hora atual
        if (scheduled.isBefore(now)) {
            throw new IllegalArgumentException("A data e hora agendada não pode ser anterior a data e hora atual");
        }

        boolean exists = schedulingRepository.existsByPatientIdAndScheduled(schedulingModel.getPatient().getId(), scheduled);

        // Business rule: Não pode haver dois agendamentos para o mesmo paciente no mesmo horário
        if (exists) {
            throw new IllegalArgumentException("Já existe um agendamento para o paciente nesse horário");
        }
        
        return toResponse(schedulingRepository.save(schedulingModel));
    }

    @Transactional
    public SchedulingResponseDTO update(String id, SchedulingUpdateRequestDTO schedulingRequestDTO) {
        SchedulingModel schedulingModel = schedulingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        schedulingModel.setScheduled(schedulingRequestDTO.getScheduled());
        schedulingModel.setAttended(schedulingRequestDTO.getAttended());
        // Business rule: Se o agendamento foi atendido, atualiza a reativação do paciente
        if (schedulingRequestDTO.getAttended()) {
            Optional<ReactivationModel> existingReactivation = reactivationRepository.findByPatientId(schedulingModel.getPatient().getId());
            if (existingReactivation.isPresent()) {
                ReactivationModel reactivation = existingReactivation.get();
                reactivation.setLastService(schedulingModel.getScheduled());
                reactivation.setClassification(ReactivationRole.HOT);
                // Business rule: Calcula a data de reativação
                reactivation.setReactivateIn(calculateReactivationDate(schedulingModel.getScheduled()));;
                reactivationRepository.save(reactivation);
            } else {
                ReactivationModel reactivation = new ReactivationModel();
                reactivation.setLastService(schedulingModel.getScheduled());
                reactivation.setClassification(ReactivationRole.HOT);
                reactivation.setPatient(schedulingModel.getPatient());
                reactivation.setReactivateIn(calculateReactivationDate(schedulingModel.getScheduled()));
                reactivationRepository.save(reactivation);
            }
        }
        return toResponse(schedulingRepository.save(schedulingModel));
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
    // Business rule: A reativação deve ser feita 6 meses após o último atendimento
    return lastService.toLocalDate().plusMonths(6);
}

}
