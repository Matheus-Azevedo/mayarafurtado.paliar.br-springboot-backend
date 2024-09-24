package br.paliar.mayarafurtado.backend.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingModel;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingRequestDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingResponseDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingUpdateRequestDTO;
import br.paliar.mayarafurtado.backend.repository.SchedulingRepository;
import br.paliar.mayarafurtado.backend.service.SchedulingService;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class SchedulingServiceImplementation implements SchedulingService {

   @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SchedulingResponseDTO save(SchedulingRequestDTO schedulingRequestDTO) {
        SchedulingModel schedulingModel = modelMapper.map(schedulingRequestDTO, SchedulingModel.class);
        return toResponse(schedulingRepository.save(schedulingModel));
    }

    @Transactional
    public SchedulingResponseDTO update(String id, SchedulingUpdateRequestDTO schedulingRequestDTO) {
        SchedulingModel schedulingModel = schedulingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scheduling not found"));
        schedulingModel.setScheduled(schedulingRequestDTO.getScheduled());
        schedulingModel.setAttended(schedulingRequestDTO.getAttended());
        return toResponse(schedulingRepository.save(schedulingModel));
    }

    public void delete(String id) {
        schedulingRepository.deleteById(id);
    }

    public List<SchedulingResponseDTO> findAll() {
        List<SchedulingModel> schedulings = schedulingRepository.findAll();
        return schedulings.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SchedulingResponseDTO findByScheduled(LocalDateTime scheduled) {
        SchedulingModel scheduling = schedulingRepository.findByScheduled(scheduled);
        return toResponse(scheduling);
    }

    public SchedulingResponseDTO findByPatientId(String patientId) {
        SchedulingModel scheduling = schedulingRepository.findByPatientId(patientId);
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

}
