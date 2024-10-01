package br.paliar.mayarafurtado.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestBody;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingRequestDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingResponseDTO;
import br.paliar.mayarafurtado.backend.domain.scheduling.SchedulingUpdateRequestDTO;
import br.paliar.mayarafurtado.backend.service.SchedulingService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

  @Autowired
  private SchedulingService schedulingService;

  @PostMapping("/save")
  public ResponseEntity<SchedulingResponseDTO> save(@Valid @RequestBody SchedulingRequestDTO scheduling) {
    return ResponseEntity.ok(schedulingService.save(scheduling));
  }

  @PutMapping("path/{id}")
  public ResponseEntity<SchedulingResponseDTO> update(@PathVariable String id, @Valid @RequestBody SchedulingUpdateRequestDTO scheduling) {
    return ResponseEntity.ok(schedulingService.update(id, scheduling));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    schedulingService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/findAll")
  public ResponseEntity<List<SchedulingResponseDTO>> findAll() {
    return ResponseEntity.ok(schedulingService.findAll());
  }

  @GetMapping("/findByScheduled/{scheduled}")
  public ResponseEntity<SchedulingResponseDTO> findByScheduled(@PathVariable LocalDateTime scheduled) {
    return ResponseEntity.ok(schedulingService.findByScheduled(scheduled));
  }

  @GetMapping("/findByPatientId/{patientId}")
  public ResponseEntity<SchedulingResponseDTO> findByPatientId(@PathVariable String patientId) {
    return ResponseEntity.ok(schedulingService.findByPatientId(patientId));
  }

  @GetMapping("/available-times/month")
    public Map<String, String> getAvailableTimesForMonth() {
        return schedulingService.getAvailableTimesForMonth();
  }

}
