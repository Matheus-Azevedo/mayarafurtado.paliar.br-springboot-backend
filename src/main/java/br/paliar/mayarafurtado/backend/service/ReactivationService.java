package br.paliar.mayarafurtado.backend.service;

import java.util.List;
import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationResponseDTO;
import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationUpdateRequestDTO;


public interface ReactivationService {
  
  public List<ReactivationResponseDTO> findAll();

  public ReactivationResponseDTO update(String id, ReactivationUpdateRequestDTO  reactivationUpdateRequestDTO);

}
