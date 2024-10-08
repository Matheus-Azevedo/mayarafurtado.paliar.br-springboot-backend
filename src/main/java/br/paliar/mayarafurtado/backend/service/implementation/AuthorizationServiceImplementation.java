package br.paliar.mayarafurtado.backend.service.implementation;

import br.paliar.mayarafurtado.backend.domain.user.AuthenticationDTO;
import br.paliar.mayarafurtado.backend.domain.user.LoginResponseDTO;
import br.paliar.mayarafurtado.backend.domain.user.RegisterDTO;
import br.paliar.mayarafurtado.backend.domain.user.UserModel;
import br.paliar.mayarafurtado.backend.infra.security.TokenService;
import br.paliar.mayarafurtado.backend.repository.UserRepository;
import br.paliar.mayarafurtado.backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImplementation implements AuthorizationService  {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthorizationServiceImplementation(@Lazy AuthenticationManager authenticationManager,
                                              UserRepository userRepository,
                                              TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        final var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        final var auth = this.authenticationManager.authenticate(usernamePassword);
        if (!auth.isAuthenticated()) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        final var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        if (token == null) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        return new LoginResponseDTO(token);
    }

    public void register(RegisterDTO data) {
        if(userRepository.findByEmail(data.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        UserModel newUser = new UserModel(data.getEmail(), encryptedPassword, data.getRole());
        userRepository.save(newUser);
    }
}
