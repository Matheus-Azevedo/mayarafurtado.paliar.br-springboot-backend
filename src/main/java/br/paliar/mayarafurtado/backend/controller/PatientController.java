package br.paliar.mayarafurtado.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paliar.mayarafurtado.backend.domain.patient.PatientRequestDTO;
import br.paliar.mayarafurtado.backend.domain.patient.PatientResponseDTO;
import br.paliar.mayarafurtado.backend.service.PatientService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

  @Autowired
  private PatientService patientService;

  @PostMapping("/save")
  public ResponseEntity<PatientResponseDTO> save(@Valid @RequestBody PatientRequestDTO patient) {
    return ResponseEntity.ok(patientService.save(patient));
  }

  @PutMapping("path/{id}")
  public ResponseEntity<PatientResponseDTO> update(@PathVariable String id, @Valid @RequestBody PatientRequestDTO patient) {
    return ResponseEntity.ok(patientService.update(id, patient));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    patientService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/findAll")
  public ResponseEntity<List<PatientResponseDTO>> findAll() {
    return ResponseEntity.ok(patientService.findAll());
  }

  @GetMapping("/findByName/{name}")
  public ResponseEntity<PatientResponseDTO> findByName(@PathVariable String name) {
    return ResponseEntity.ok(patientService.findByName(name));
  }

  @GetMapping("/findByPhone/{phone}")
  public ResponseEntity<PatientResponseDTO> findByPhone(@PathVariable String phone) {
    return ResponseEntity.ok(patientService.findByPhone(phone));
  }
  
}
