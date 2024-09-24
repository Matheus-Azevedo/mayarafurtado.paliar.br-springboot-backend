package br.paliar.mayarafurtado.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.paliar.mayarafurtado.backend.domain.user.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

  UserDetails findByEmail(String email);
  
}
