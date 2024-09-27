package br.paliar.mayarafurtado.backend.domain.reactivation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactivationRequestDTO {
    
    @NotEmpty(message = "Patient ID is required")
    private String patientId;
    
    @NotEmpty(message = "Last service is required")
    private String lastService;
    
    @NotNull(message = "Classification is required")
    private ReactivationRole classification;
  
}
