package br.paliar.mayarafurtado.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paliar.mayarafurtado.backend.domain.reactivation.ReactivationResponseDTO;
import br.paliar.mayarafurtado.backend.service.ReactivationService;

@RestController
@RequestMapping("/reactivations")
public class ReactivationController {
    
    @Autowired
    private ReactivationService reactivationService;
  
    @GetMapping("/findAll")
    public ResponseEntity<List<ReactivationResponseDTO>> findAll() {
      return ResponseEntity.ok(reactivationService.findAll());
    }
  
}
