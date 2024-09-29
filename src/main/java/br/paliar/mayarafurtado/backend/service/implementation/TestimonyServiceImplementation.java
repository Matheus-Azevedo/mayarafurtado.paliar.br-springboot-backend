package br.paliar.mayarafurtado.backend.service.implementation;

import br.paliar.mayarafurtado.backend.service.TestimonyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyModel;
import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyRequestDTO;
import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyResponseDTO;
import br.paliar.mayarafurtado.backend.repository.TestimonyRepository;
import java.util.stream.Collectors;


@Service
public class TestimonyServiceImplementation implements TestimonyService {
  
  private final TestimonyRepository testimonyRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public TestimonyServiceImplementation(TestimonyRepository testimonyRepository, ModelMapper modelMapper) {
      this.testimonyRepository = testimonyRepository;
      this.modelMapper = modelMapper;
  }

  public TestimonyResponseDTO save(TestimonyRequestDTO testimony) {
    TestimonyModel testimonyModel = modelMapper.map(testimony, TestimonyModel.class);
    if (testimonyRepository.existsByTelephone(testimony.getTelephone())) {
      throw new IllegalArgumentException("O Depoimento já existe");
    }
    return toResponse(testimonyRepository.save(testimonyModel));
  }
  
  @Transactional
  public TestimonyResponseDTO update(String id, TestimonyRequestDTO testimony) {
    TestimonyModel testimonyModel = testimonyRepository
                                    .findById(id)
                                    .orElseThrow(() -> new IllegalArgumentException("O Depoimento não foi encontrado"));
    testimonyModel.setName(testimony.getName());
    testimonyModel.setTelephone(testimony.getTelephone());
    testimonyModel.setTestimony(testimony.getTestimony());
    return toResponse(testimonyRepository.save(testimonyModel));
  }
  
  public void delete(String id) {
    if (!testimonyRepository.existsById(id)) {
      throw new IllegalArgumentException("O Depoimento não foi encontrado");
    }
    testimonyRepository.deleteById(id);
  }
  
  public List<TestimonyResponseDTO> findAll() {
    List<TestimonyModel> testimonies = testimonyRepository.findAll();
    return testimonies.stream()
                      .map(this::toResponse)
                      .collect(Collectors.toList());
  }
  
  public TestimonyResponseDTO findByName(String name) {
    TestimonyModel testimony = testimonyRepository.findByName(name);
    if (testimony == null) {
      throw new IllegalArgumentException("O Depoimento não foi encontrado");
    }
    return toResponse(testimony);
  }
  
  public TestimonyResponseDTO findByTelephone(String telephone) {
    TestimonyModel testimony = testimonyRepository.findByTelephone(telephone);
    if (testimony == null) {
      throw new IllegalArgumentException("O Depoimento não foi encontrado");
    }
    return toResponse(testimony);
  }

  private TestimonyResponseDTO toResponse(TestimonyModel testimony) {
    return modelMapper.map(testimony, TestimonyResponseDTO.class);
  }

  
}
