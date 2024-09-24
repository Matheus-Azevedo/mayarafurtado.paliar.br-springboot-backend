package br.paliar.mayarafurtado.backend.domain.testimony;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "testimonials")
@Entity(name = "Testimony")
@EqualsAndHashCode(of = "id")
public class TestimonyModel implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(length = 100)
  private String name;

  @Column(length = 100)
  private String telephone;

  @Column(length = 1000)
  private String testimony;
  
}
