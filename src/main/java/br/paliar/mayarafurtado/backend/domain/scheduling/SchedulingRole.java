package br.paliar.mayarafurtado.backend.domain.scheduling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SchedulingRole {

  EVALUATION("evaluation"),
  SESSION("session");

  private final String role;
  
}
