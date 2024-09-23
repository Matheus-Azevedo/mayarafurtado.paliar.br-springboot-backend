# Use uma imagem base do Maven com JDK 17
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copie o código do projeto para o container
COPY . .

# Execute o Maven para compilar o projeto e criar o arquivo JAR
RUN mvn clean package -DskipTests

# Use uma imagem base do OpenJDK
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copie o JAR gerado do estágio de build anterior para o container
COPY --from=build /app/target/escalapro-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta 8085, que é a porta configurada no application.yml
EXPOSE 8080

# Configure o comando padrão para executar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]