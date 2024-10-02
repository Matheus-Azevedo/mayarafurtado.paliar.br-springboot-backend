package br.paliar.mayarafurtado.backend.domain.patient;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PatientRequestDTO {

  @NotEmpty(message = "Nome é obrigatório")
  private String name;

  @NotEmpty(message = "Telefone é obrigatório")
  private String phone;

  @NotEmpty(message = "Email é obrigatório")
  private String email;

  @NotEmpty(message = "Data de nascimento é obrigatório")
  private String birthDate;
  
  @NotEmpty(message = "CPF é obrigatório")
  private String cpf;
  
  @NotEmpty(message = "Endereço é obrigatório")
  private String address;
  
  @NotEmpty(message = "Bairro é obrigatório")
  private String district;
  
  @NotEmpty(message = "Cidade é obrigatório")
  private String city;

  @NotEmpty(message = "Estado é obrigatório")
  private String state;
  
  @NotEmpty(message = "CEP é obrigatório")
  private String zipCode;
  
}
