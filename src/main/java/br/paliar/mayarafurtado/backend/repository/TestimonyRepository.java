package br.paliar.mayarafurtado.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.paliar.mayarafurtado.backend.model.TestimonyModel;

@Repository
public interface TestimonyRepository extends JpaRepository<TestimonyModel, Integer> {

  public TestimonyModel findByName(String name);

  public TestimonyModel findByTelephone(String telephone);
}
