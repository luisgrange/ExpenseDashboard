# Usar uma imagem base com Java (por exemplo, OpenJDK 17)
FROM openjdk:17-jdk-alpine

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR do Spring Boot para o container
COPY target/expenses-dashboard.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]