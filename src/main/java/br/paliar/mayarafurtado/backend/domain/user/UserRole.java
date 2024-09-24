package br.paliar.mayarafurtado.backend.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private final String role;
}
