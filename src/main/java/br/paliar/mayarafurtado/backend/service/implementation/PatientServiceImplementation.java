package br.paliar.mayarafurtado.backend.service.implementation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.paliar.mayarafurtado.backend.domain.patient.PatientModel;
import br.paliar.mayarafurtado.backend.domain.patient.PatientResponseDTO;
import br.paliar.mayarafurtado.backend.domain.patient.PatientRequestDTO;
import br.paliar.mayarafurtado.backend.repository.PatientRepository;
import br.paliar.mayarafurtado.backend.service.PatientService;

@Service
public class PatientServiceImplementation implements PatientService {
  
  @Autowired
  private final PatientRepository patientRepository;
  
  @Autowired
  private final ModelMapper modelMapper;
  
  public PatientServiceImplementation(PatientRepository patientRepository, ModelMapper modelMapper) {
    this.patientRepository = patientRepository;
    this.modelMapper = modelMapper;
  }
  
  public PatientResponseDTO save(PatientRequestDTO patientRequestDTO) {
    PatientModel patientModel = modelMapper.map(patientRequestDTO, PatientModel.class);
    patientModel.setId(UUID.randomUUID().toString());
    patientRepository.save(patientModel);
    return modelMapper.map(patientModel, PatientResponseDTO.class);
  }
  
  @Transactional
  public PatientResponseDTO update(String id, PatientRequestDTO patientRequestDTO) {
    PatientModel patientModel = patientRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Patient not found"));
    patientModel.setName(patientRequestDTO.getName());
    patientModel.setPhone(patientRequestDTO.getPhone());
    patientModel.setEmail(patientRequestDTO.getEmail());
    patientModel.setBirthDate(patientRequestDTO.getBirthDate());
    patientModel.setCpf(patientRequestDTO.getCpf());
    patientModel.setAddress(patientRequestDTO.getAddress());
    patientModel.setDistrict(patientRequestDTO.getDistrict());
    patientModel.setCity(patientRequestDTO.getCity());
    patientModel.setState(patientRequestDTO.getState());
    patientModel.setZipCode(patientRequestDTO.getZipCode());
    patientRepository.save(patientModel);
    return modelMapper.map(patientModel, PatientResponseDTO.class);
  }
  
  public void delete(String id) {
    patientRepository.deleteById(id);
  }
  
  public List<PatientResponseDTO> findAll() {
    return patientRepository.findAll()
      .stream()
      .map(patientModel -> modelMapper.map(patientModel, PatientResponseDTO.class))
      .collect(Collectors.toList());
  }
  
  public PatientResponseDTO findByName(String name) {
    PatientModel patientModel = patientRepository.findByName(name);
    return modelMapper.map(patientModel, PatientResponseDTO.class);
  }
  
  public PatientResponseDTO findByPhone(String phone) {
    PatientModel patientModel = patientRepository.findByPhone(phone);
    return modelMapper.map(patientModel, PatientResponseDTO.class);
  }
}
