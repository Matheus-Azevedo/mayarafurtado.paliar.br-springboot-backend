package br.paliar.mayarafurtado.backend.domain.patient;

import java.util.List;

import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
@Entity(name = "Patient")
@EqualsAndHashCode(of = "id")
public class PatientModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(length = 100)
  private String name;

  @Column(length = 100)
  private String phone;

  @Column(length = 100, unique = true)
  private String email;

  @Column(length = 100)
  private String birthDate;
  
  @Column(length = 100)
  private String cpf;
  
  @Column(length = 100)
  private String address;
  
  @Column(length = 100)
  private String district;
  
  @Column(length = 100)
  private String city;

  @Column(length = 100)
  private String state;
  
  @Column(length = 100)
  private String zipCode;

  @OneToMany(mappedBy = "patient")
  private List<SchedulingModel> schedulings;
}
