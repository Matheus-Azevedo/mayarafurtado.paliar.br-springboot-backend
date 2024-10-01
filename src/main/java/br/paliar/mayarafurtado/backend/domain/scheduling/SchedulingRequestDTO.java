package br.paliar.mayarafurtado.backend.domain.scheduling;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchedulingRequestDTO {

  @NotNull(message = "Scheduled is required")
  private LocalDateTime scheduled;

  @NotEmpty(message = "Attended is required")
  private String patientId;

  @NotEmpty(message = "Role is required")
  private String role;
  
}
