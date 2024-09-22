
# Mayara Furtado - Backend

Este é o repositório do backend do sistema `mayarafurtado.paliar.br`, responsável pela lógica de negócio e comunicação com o banco de dados para o gerenciamento de pacientes e depoimentos.

## Tecnologias Utilizadas

-   **Spring Boot**: Framework Java para desenvolvimento de APIs.
-   **Docker**: Plataforma de contêinerização para garantir consistência entre ambientes de desenvolvimento e produção.
-   **Render**: Plataforma de hospedagem escalável.
-   **TestLink e Vitest**: Ferramentas de gerenciamento e execução de testes.

## Funcionalidades

### Administradores (Fisioterapeuta)

1.  Autenticação de administradores.
2.  Gerenciamento de depoimentos (visualização, remoção, cadastro).
3.  Gerenciamento de pacientes (cadastro, visualização, classificação).

### Requisitos de Segurança e Confiabilidade

-   Autenticação obrigatória para acessar a área administrativa.
-   Criptografia de dados sensíveis.
-   Alta disponibilidade e baixo tempo de inatividade.

## Como Rodar o Projeto Localmente

1.  Clone o repositório:

    `git clone [link-do-repositorio]`

2.  Certifique-se de ter o Docker instalado e execute:

    `docker-compose up`

O backend estará disponível em `http://localhost:8080`.

## Endpoints Principais

-   `POST /login`: Autenticação.
-   `GET /patients`: Listar pacientes.
-   `POST /patients`: Cadastrar novo paciente.
-   `DELETE /testimonials/{id}`: Remover depoimento.

## Deploy

O deploy do backend será realizado via Render: [Link para o deploy (ainda não subimos)](#).