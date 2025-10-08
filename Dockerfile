# Usa a imagem slim do OpenJDK 21
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR para dentro do contêiner
COPY target/eucomida-*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação dentro do contêiner
CMD ["java", "-jar", "app.jar"]