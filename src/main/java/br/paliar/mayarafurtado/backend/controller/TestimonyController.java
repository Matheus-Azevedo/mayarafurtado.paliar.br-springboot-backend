package br.paliar.mayarafurtado.backend.controller;

import br.paliar.mayarafurtado.backend.service.TestimonyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyRequestDTO;
import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/testimonials")
public class TestimonyController {


  @Autowired
  private TestimonyService testimonyService;

  @PostMapping("/save")
  public ResponseEntity<TestimonyResponseDTO> save(@Valid @RequestBody TestimonyRequestDTO testimony) {
    return ResponseEntity.ok(testimonyService.save(testimony));
  }

  @PutMapping("path/{id}")
  public ResponseEntity<TestimonyResponseDTO> update(@PathVariable String id, @Valid @RequestBody TestimonyRequestDTO testimony) {
    return ResponseEntity.ok(testimonyService.update(id, testimony));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    testimonyService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/findAll")
  public ResponseEntity<List<TestimonyResponseDTO>> findAll() {
    return ResponseEntity.ok(testimonyService.findAll());
  }
  
  @GetMapping("/findByName/{name}")
  public ResponseEntity<TestimonyResponseDTO> findByName(@PathVariable String name) {
    return ResponseEntity.ok(testimonyService.findByName(name));
  }

  @GetMapping("/findByTelephone/{telephone}")
  public ResponseEntity<TestimonyResponseDTO> findByTelephone(@PathVariable String telephone) {
    return ResponseEntity.ok(testimonyService.findByTelephone(telephone));
  }

}
