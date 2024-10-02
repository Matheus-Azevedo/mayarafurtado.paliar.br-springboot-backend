package br.paliar.mayarafurtado.backend.domain.scheduling;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchedulingRequestDTO {

  @NotNull(message = "A Data de Agendamento é obrigatória")
  private LocalDateTime scheduled;

  @NotEmpty(message = "O ID do paciente é obrigatório")
  private String patientId;

  @NotEmpty(message = "O Tipo de Agendamento é obrigatório")
  private String role;
  
}
