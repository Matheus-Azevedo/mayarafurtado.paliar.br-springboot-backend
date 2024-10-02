package br.paliar.mayarafurtado.backend.domain.scheduling;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchedulingUpdateRequestDTO {
  
  @NotNull(message = "A Data de Agendamento é obrigatória")
  private LocalDateTime scheduled;

  @NotNull(message = "A Confirmação de Presença é obrigatória")
  private Boolean attended;

}
