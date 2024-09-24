package br.paliar.mayarafurtado.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paliar.mayarafurtado.backend.domain.user.AuthenticationDTO;
import br.paliar.mayarafurtado.backend.domain.user.LoginResponseDTO;
import br.paliar.mayarafurtado.backend.domain.user.RegisterDTO;
import br.paliar.mayarafurtado.backend.service.AuthorizationService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    @Lazy
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        LoginResponseDTO response = authorizationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        try {
            authorizationService.register(data);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
