package br.paliar.mayarafurtado.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.paliar.mayarafurtado.backend.domain.testimony.TestimonyModel;

@Repository
public interface TestimonyRepository extends JpaRepository<TestimonyModel, String> {

  TestimonyModel findByName(String name);

  TestimonyModel findByTelephone(String telephone);
}
