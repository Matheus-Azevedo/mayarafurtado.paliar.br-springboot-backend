package br.paliar.mayarafurtado.backend.service;

import br.paliar.mayarafurtado.backend.dto.request.TestimonyRequestDTO;
import br.paliar.mayarafurtado.backend.dto.response.TestimonyResponseDTO;
import java.util.List;

public interface TestimonyService {

    TestimonyResponseDTO save(TestimonyRequestDTO testimonyRequestDTO);

    TestimonyResponseDTO update(Integer id, TestimonyRequestDTO testimonyRequestDTO);

    void delete(Integer id);

    List<TestimonyResponseDTO> findAll();

    TestimonyResponseDTO findByName(String name);

    TestimonyResponseDTO findByTelephone(String telephone);
}
