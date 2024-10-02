package br.paliar.mayarafurtado.backend.controller;

import java.util.List;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationResponseDTO;
import br.paliar.mayarafurtado.backend.service.ReactivationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reactivations")
public class ReactivationController {
    
    @Autowired
    private ReactivationService reactivationService;
  
    @GetMapping("/findAll")
    public ResponseEntity<List<ReactivationResponseDTO>> findAll() {
      return ResponseEntity.ok(reactivationService.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReactivationResponseDTO> update(@PathVariable String id, @Valid @RequestBody LocalDate currentDate) {
      return ResponseEntity.ok(reactivationService.update(id, currentDate));
    }
  
}
