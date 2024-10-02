package br.paliar.mayarafurtado.backend.domain.reactivation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactivationRequestDTO {
    
    @NotEmpty(message = "O ID do paciente é obrigatório")
    private String patientId;
    
    @NotEmpty(message = "O último serviço é obrigatório")
    private String lastService;
    
    @NotNull(message = "A classificação é obrigatória")
    private ReactivationRole classification;
  
}
