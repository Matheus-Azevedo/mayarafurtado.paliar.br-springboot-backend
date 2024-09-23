package br.paliar.mayarafurtado.backend.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "testimony")
public class TestimonyModel implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100)
  private String name;

  @Column(length = 100)
  private String telephone;

  @Column(length = 1000)
  private String testimony;
  
}
