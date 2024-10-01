package br.paliar.mayarafurtado.backend.domain.scheduling;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SchedulingResponseDTO {

  private String id;

  private LocalDateTime scheduled;
  
  private Boolean attended;

  private String role;
  
  private String patientId;

  private String patientName;

  private String patientPhone;

  private String patientEmail;

}
