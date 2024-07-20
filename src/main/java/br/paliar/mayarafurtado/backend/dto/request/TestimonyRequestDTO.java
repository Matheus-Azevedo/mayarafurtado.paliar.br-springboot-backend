package br.paliar.mayarafurtado.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TestimonyRequestDTO {
  
  @NotEmpty(message = "Name is required")
  private String name;
  
  @NotEmpty(message = "Email is required")
  @Email(message = "Invalid email")
  private String email;
  
  @NotEmpty(message = "Testimony is required")
  private String testimony;

}
