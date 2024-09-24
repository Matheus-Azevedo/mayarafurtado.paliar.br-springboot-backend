package br.paliar.mayarafurtado.backend.service;

import java.util.List;

import br.paliar.mayarafurtado.backend.domain.patient.PatientResponseDTO;
import br.paliar.mayarafurtado.backend.domain.patient.PatientRequestDTO;

public interface PatientService {

    public PatientResponseDTO save(PatientRequestDTO patientRequestDTO);

    public PatientResponseDTO update(String id, PatientRequestDTO patientRequestDTO);

    public void delete(String id);

    public List<PatientResponseDTO> findAll();

    public PatientResponseDTO findByName(String name);

    public PatientResponseDTO findByPhone(String phone);
  
}
