package br.paliar.mayarafurtado.backend.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@EnableWebMvc
@OpenAPIDefinition(
    info = @Info(
        title = "API mayarafurtado.paliar.br",
        version = "1.0.0",
        description = "Documentação da API para controlar o gerenciamento da clinica de fisioterapia da Dra. Mayara Furtado.",
        contact = @Contact(name = "Matheus Eduardo", email = "matheuseduardo.jp@gmail.com")
    )
)
public class SpringDocConfig {
    
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**") // Ajuste para os endpoints que deseja incluir
                .build();
    }
}
