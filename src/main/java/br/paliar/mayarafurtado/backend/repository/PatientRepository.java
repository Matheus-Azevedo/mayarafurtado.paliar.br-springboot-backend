package br.paliar.mayarafurtado.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.paliar.mayarafurtado.backend.domain.patient.PatientModel;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, String> {

  PatientModel findByName(String name);

  PatientModel findByPhone(String phone);
  
}
