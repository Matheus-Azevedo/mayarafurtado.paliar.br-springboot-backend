package br.paliar.mayarafurtado.backend.domain.patient;

import lombok.Data;

@Data
public class PatientResponseDTO {
  
   private String id;

  private String name;

  private String phone;

  private String email;

  private String birthDate;
  
  private String cpf;
  
  private String address;
  
  private String district;
  
  private String city;

  private String state;
  
  private String zipCode;
}
