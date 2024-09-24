package br.paliar.mayarafurtado.backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.paliar.mayarafurtado.backend.domain.user.AuthenticationDTO;
import br.paliar.mayarafurtado.backend.domain.user.LoginResponseDTO;
import br.paliar.mayarafurtado.backend.domain.user.RegisterDTO;

public interface AuthorizationService extends UserDetailsService {

    LoginResponseDTO login(AuthenticationDTO data);
    
    void register(RegisterDTO data);
}
