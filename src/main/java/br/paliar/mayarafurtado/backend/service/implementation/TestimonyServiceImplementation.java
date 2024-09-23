package br.paliar.mayarafurtado.backend.service.implementation;

import br.paliar.mayarafurtado.backend.service.TestimonyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import br.paliar.mayarafurtado.backend.dto.request.TestimonyRequestDTO;
import br.paliar.mayarafurtado.backend.dto.response.TestimonyResponseDTO;
import br.paliar.mayarafurtado.backend.model.TestimonyModel;
import br.paliar.mayarafurtado.backend.repository.TestimonyRepository;
import java.util.stream.Collectors;


@Service
public class TestimonyServiceImplementation implements TestimonyService {
  
  @Autowired
  private TestimonyRepository testimonyRepository;

  @Autowired
  private ModelMapper modelMapper;

  public TestimonyResponseDTO save(TestimonyRequestDTO testimony) {
    TestimonyModel testimonyModel = modelMapper.map(testimony, TestimonyModel.class);
    return modelMapper.map(testimonyRepository.save(testimonyModel), TestimonyResponseDTO.class);
  }
  
  public TestimonyResponseDTO update(Integer id, TestimonyRequestDTO testimony) {
    TestimonyModel testimonyModel = testimonyRepository
                                    .findById(id)
                                    .orElseThrow(() -> new IllegalArgumentException("Testimony not found"));
    testimonyModel.setName(testimony.getName());
    testimonyModel.setTelephone(testimony.getTelephone());
    testimonyModel.setTestimony(testimony.getTestimony());
    return modelMapper.map(testimonyRepository.save(testimonyModel), TestimonyResponseDTO.class);
  }
  
  public void delete(Integer id) {
    testimonyRepository.deleteById(id);
  }
  
  public List<TestimonyResponseDTO> findAll() {
    List<TestimonyModel> testimonies = testimonyRepository.findAll();
    return testimonies.stream()
                      .map(testimony -> modelMapper.map(testimony, TestimonyResponseDTO.class))
                      .collect(Collectors.toList());
  }
  
  public TestimonyResponseDTO findByName(String name) {
    TestimonyModel testimony = testimonyRepository.findByName(name);
    return modelMapper.map(testimony, TestimonyResponseDTO.class);
  }
  
  public TestimonyResponseDTO findByTelephone(String telephone) {
    TestimonyModel testimony = testimonyRepository.findByTelephone(telephone);
    return modelMapper.map(testimony, TestimonyResponseDTO.class);
  }
  
}
