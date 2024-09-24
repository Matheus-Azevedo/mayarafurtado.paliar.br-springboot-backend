package br.paliar.mayarafurtado.backend.service;

import java.time.LocalDateTime;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingRequestDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingResponseDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingUpdateRequestDTO;

import java.util.List;

public interface SchedulingService {

    public SchedulingResponseDTO save(SchedulingRequestDTO schedulingRequestDTO);

    public SchedulingResponseDTO update(String id, SchedulingUpdateRequestDTO schedulingRequestDTO);

    public void delete(String id);

    public List<SchedulingResponseDTO> findAll();

    public SchedulingResponseDTO findByScheduled(LocalDateTime scheduled);

    public SchedulingResponseDTO findByPatientId(String patientId);
  
}
