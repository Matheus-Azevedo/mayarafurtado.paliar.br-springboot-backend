package br.paliar.mayarafurtado.backend.domain.scheduling;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchedulingUpdateRequestDTO {
  
  @NotNull(message = "Scheduled is required")
  private LocalDateTime scheduled;

  @NotNull(message = "Attended is required")
  private Boolean attended;

}
