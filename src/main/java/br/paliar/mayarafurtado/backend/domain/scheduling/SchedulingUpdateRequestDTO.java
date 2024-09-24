package br.paliar.mayarafurtado.backend.domain.scheduling;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SchedulingUpdateRequestDTO {
  
  @NotEmpty(message = "Scheduled is required")
  private LocalDateTime scheduled;

  @NotEmpty(message = "Attended is required")
  private Boolean attended;

}
