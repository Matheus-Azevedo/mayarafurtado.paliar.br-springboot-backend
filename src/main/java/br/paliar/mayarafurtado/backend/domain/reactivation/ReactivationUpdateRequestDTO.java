package br.paliar.mayarafurtado.backend.domain.reactivation;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReactivationUpdateRequestDTO {

  @NotNull(message = "O campo com a data atual é obrigatório")
  private LocalDate currentDate;
  
}
