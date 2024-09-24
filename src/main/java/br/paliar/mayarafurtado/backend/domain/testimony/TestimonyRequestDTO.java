package br.paliar.mayarafurtado.backend.domain.testimony;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TestimonyRequestDTO {
  
  @NotEmpty(message = "Name is required")
  private String name;
  
  @NotEmpty(message = "Telephone is required")
  private String telephone;
  
  @NotEmpty(message = "Testimony is required")
  private String testimony;

}
