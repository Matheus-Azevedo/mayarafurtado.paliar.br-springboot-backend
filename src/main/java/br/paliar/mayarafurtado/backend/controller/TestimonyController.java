package br.paliar.mayarafurtado.backend.controller;

import br.paliar.mayarafurtado.backend.service.TestimonyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paliar.mayarafurtado.backend.dto.request.TestimonyRequestDTO;
import br.paliar.mayarafurtado.backend.dto.response.TestimonyResponseDTO;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/testimony")
public class TestimonyController {


  @Autowired
  private TestimonyService testimonyService;

  @PostMapping("/save")
  public ResponseEntity<TestimonyResponseDTO> save(@Valid @RequestBody TestimonyRequestDTO testimony) {
    return ResponseEntity.ok(testimonyService.save(testimony));
  }

  @PutMapping("path/{id}")
  public ResponseEntity<TestimonyResponseDTO> update(@PathVariable Integer id, @RequestBody TestimonyRequestDTO testimony) {
    return ResponseEntity.ok(testimonyService.update(id, testimony));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    testimonyService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/findAll")
  public ResponseEntity<TestimonyResponseDTO> findAll() {
    return ResponseEntity.ok(testimonyService.findAll());
  }
  
  @GetMapping("/findByName/{name}")
  public ResponseEntity<TestimonyResponseDTO> findByName(@PathVariable String name) {
    return ResponseEntity.ok(testimonyService.findByName(name));
  }

  @GetMapping("/findByEmail/{email}")
  public ResponseEntity<TestimonyResponseDTO> findByEmail(@PathVariable String email) {
    return ResponseEntity.ok(testimonyService.findByEmail(email));
  }

}
