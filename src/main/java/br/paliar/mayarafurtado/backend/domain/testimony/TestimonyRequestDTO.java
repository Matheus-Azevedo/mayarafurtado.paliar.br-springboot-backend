package br.paliar.mayarafurtado.backend.domain.testimony;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TestimonyRequestDTO {
  
  @NotEmpty(message = "Nome é obrigatório")
  private String name;
  
  @NotEmpty(message = "Telefone é obrigatório")
  private String telephone;
  
  @NotEmpty(message = "Depoimento é obrigatório")
  private String testimony;

}
