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

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientServiceImplementation(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public PatientResponseDTO save(PatientRequestDTO patientRequestDTO) {
        PatientModel patientModel = modelMapper.map(patientRequestDTO, PatientModel.class);
        patientModel.setId(UUID.randomUUID().toString());
        return toResponse(patientRepository.save(patientModel));
    }

    @Transactional
    public PatientResponseDTO update(String id, PatientRequestDTO patientRequestDTO) {
        PatientModel patientModel = patientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
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
        return toResponse(patientRepository.save(patientModel));
    }

    public void delete(String id) {
      if (!patientRepository.existsById(id)) {
          throw new IllegalArgumentException("Paciente não encontrado");
      }
      patientRepository.deleteById(id);
    }

    public List<PatientResponseDTO> findAll() {
        List<PatientModel> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        return patients.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PatientResponseDTO findByName(String name) {
        PatientModel patientModel = patientRepository.findByName(name);
        if (patientModel == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        return toResponse(patientModel);
    }

    public PatientResponseDTO findByPhone(String phone) {
        PatientModel patientModel = patientRepository.findByPhone(phone);
        if (patientModel == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        return toResponse(patientModel);
    }

    private PatientResponseDTO toResponse(PatientModel patientModel) {
        return modelMapper.map(patientModel, PatientResponseDTO.class);
    }
}
