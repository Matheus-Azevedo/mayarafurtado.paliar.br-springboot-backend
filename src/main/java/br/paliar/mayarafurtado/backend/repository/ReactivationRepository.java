package br.paliar.mayarafurtado.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationModel;

@Repository
public interface ReactivationRepository extends JpaRepository<ReactivationModel, String> {

    Optional<ReactivationModel> findByPatientId(String patientId);

}
