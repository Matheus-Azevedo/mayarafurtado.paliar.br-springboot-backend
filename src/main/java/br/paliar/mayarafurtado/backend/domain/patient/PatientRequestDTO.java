package br.paliar.mayarafurtado.backend.domain.patient;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PatientRequestDTO {

  @NotEmpty(message = "Name is required")
  private String name;

  @NotEmpty(message = "Name is required")
  private String phone;

  @NotEmpty(message = "Name is required")
  private String email;

  @NotEmpty(message = "Name is required")
  private String birthDate;
  
  @NotEmpty(message = "Name is required")
  private String cpf;
  
  @NotEmpty(message = "Name is required")
  private String address;
  
  @NotEmpty(message = "Name is required")
  private String district;
  
  @NotEmpty(message = "Name is required")
  private String city;

  @NotEmpty(message = "Name is required")
  private String state;
  
  @NotEmpty(message = "Name is required")
  private String zipCode;
  
}
