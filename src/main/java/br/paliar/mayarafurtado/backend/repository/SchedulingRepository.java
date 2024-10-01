package br.paliar.mayarafurtado.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingModel;

@Repository
public interface SchedulingRepository extends JpaRepository<SchedulingModel, String> {

  boolean existsByPatientIdAndScheduled(String patientId, LocalDateTime scheduled);

  SchedulingModel findByScheduled(LocalDateTime scheduled);

  SchedulingModel findByPatientId(String patientId);

   List<SchedulingModel> findByScheduledBetween(LocalDateTime start, LocalDateTime end);
  
}
