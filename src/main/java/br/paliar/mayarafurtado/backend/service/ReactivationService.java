package br.paliar.mayarafurtado.backend.service;

import java.util.List;
import java.time.LocalDate;
import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationResponseDTO;


public interface ReactivationService {
  
  public List<ReactivationResponseDTO> findAll();

  public ReactivationResponseDTO update(String id, LocalDate currentDate);

}
