package br.paliar.mayarafurtado.backend.domain.reactivation;

import lombok.Data;

@Data
public class ReactivationResponseDTO {

  private String id;

  private String lastService;
  
  private ReactivationRole classification;

  private String reactivateIn;

  private String patientId;

  private String patientName;
  
  private String patientPhone;
}
