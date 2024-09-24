package br.paliar.mayarafurtado.backend.domain.testimony;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "testimonials")
@Entity(name = "Testimony")
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
