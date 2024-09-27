package br.paliar.mayarafurtado.backend.domain.reactivation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReactivationRole {

  HOT("hot"),
  COLD("cold"),
  QUIET("quiet");

  private final String role;
  
}
