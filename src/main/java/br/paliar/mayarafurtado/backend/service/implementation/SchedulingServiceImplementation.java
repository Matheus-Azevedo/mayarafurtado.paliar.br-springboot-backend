package br.paliar.mayarafurtado.backend.service.implementation;

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
            throw new IllegalArgumentException("Patient not found");
        }

        LocalDateTime scheduled = schedulingModel.getScheduled();

        boolean exists = schedulingRepository.existsByPatientIdAndScheduled(schedulingModel.getPatient().getId(), scheduled);

        if (exists) {
            throw new IllegalArgumentException("Scheduling already exists");
        }
        
        return toResponse(schedulingRepository.save(schedulingModel));
    }

    @Transactional
    public SchedulingResponseDTO update(String id, SchedulingUpdateRequestDTO schedulingRequestDTO) {
        SchedulingModel schedulingModel = schedulingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scheduling not found"));
        schedulingModel.setScheduled(schedulingRequestDTO.getScheduled());
        schedulingModel.setAttended(schedulingRequestDTO.getAttended());
        // Trigger reactivation
        if (schedulingRequestDTO.getAttended()) {
            Optional<ReactivationModel> existingReactivation = reactivationRepository.findByPatientId(schedulingModel.getPatient().getId());
            if (existingReactivation.isPresent()) {
                ReactivationModel reactivation = existingReactivation.get();
                reactivation.setLastService(schedulingModel.getScheduled());
                reactivation.setClassification(ReactivationRole.HOT);
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
            throw new IllegalArgumentException("Scheduling not found");
        }
        schedulingRepository.deleteById(id);
    }

    public List<SchedulingResponseDTO> findAll() {
        List<SchedulingModel> schedulings = schedulingRepository.findAll();
        if (schedulings.isEmpty()) {
            throw new IllegalArgumentException("Scheduling not found");
        }
        return schedulings.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SchedulingResponseDTO findByScheduled(LocalDateTime scheduled) {
        SchedulingModel scheduling = schedulingRepository.findByScheduled(scheduled);
        if (scheduling == null) {
            throw new IllegalArgumentException("Scheduling not found");
        }
        return toResponse(scheduling);
    }

    public SchedulingResponseDTO findByPatientId(String patientId) {
        SchedulingModel scheduling = schedulingRepository.findByPatientId(patientId);
        if (scheduling == null) {
            throw new IllegalArgumentException("Scheduling not found");
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
    // Converte LocalDateTime para LocalDate e adiciona 6 meses
    return lastService.toLocalDate().plusMonths(6);
}

}
