package br.paliar.mayarafurtado.backend.domain.reactivation;

import java.time.LocalDateTime;
import java.time.LocalDate;

import br.paliar.mayarafurtado.backend.domain.patient.PatientModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reactivations")
@Entity(name = "Reactivation")
@EqualsAndHashCode(of = "id")
public class ReactivationModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private LocalDateTime lastService;

  private ReactivationRole classification;

  private LocalDate reactivateIn;

  @OneToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
  private PatientModel patient;
  
}
