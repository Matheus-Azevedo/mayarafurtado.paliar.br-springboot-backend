package br.paliar.mayarafurtado.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

  private String token;
  
}
