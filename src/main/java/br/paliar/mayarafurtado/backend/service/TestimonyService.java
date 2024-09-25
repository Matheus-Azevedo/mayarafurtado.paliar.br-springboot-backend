package br.paliar.mayarafurtado.backend.service;

import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyRequestDTO;
import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyResponseDTO;

import java.util.List;

public interface TestimonyService {

    public TestimonyResponseDTO save(TestimonyRequestDTO testimonyRequestDTO);

    public TestimonyResponseDTO update(String id, TestimonyRequestDTO testimonyRequestDTO);

    public void delete(String id);

    public List<TestimonyResponseDTO> findAll();

    public TestimonyResponseDTO findByName(String name);

    public TestimonyResponseDTO findByTelephone(String telephone);

}
