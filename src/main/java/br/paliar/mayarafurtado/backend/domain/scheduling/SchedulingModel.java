package br.paliar.mayarafurtado.backend.domain.scheduling;

import java.time.LocalDateTime;

import br.paliar.mayarafurtado.backend.domain.patient.PatientModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scheduling")
@Entity(name = "Scheduling")
@EqualsAndHashCode(of = "id")
public class SchedulingModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private LocalDateTime scheduled;
  
  private Boolean attended = false;

  private SchedulingRole role;
  
  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
  private PatientModel patient;
}
