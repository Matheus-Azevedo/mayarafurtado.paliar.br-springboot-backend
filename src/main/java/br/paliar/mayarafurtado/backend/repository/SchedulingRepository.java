package br.paliar.mayarafurtado.backend.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingModel;

@Repository
public interface SchedulingRepository extends JpaRepository<SchedulingModel, String> {

  SchedulingModel findByScheduled(LocalDateTime scheduled);

  SchedulingModel findByPatientId(String patientId);
  
}
