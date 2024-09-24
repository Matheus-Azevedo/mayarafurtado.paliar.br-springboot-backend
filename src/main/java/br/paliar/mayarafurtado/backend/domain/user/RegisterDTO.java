package br.paliar.mayarafurtado.backend.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {

  @NotEmpty(message = "Name is required")
  @Email(message = "Invalid email")
  private String email;

  @NotEmpty(message = "Password is required")
  private String password;

  @NotNull(message = "Role is required")
  private UserRole role;
  
}
