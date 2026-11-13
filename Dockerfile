# ETAPA 1: Compilar la aplicación con Maven usando JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y empaquetar sin ejecutar tests
COPY src ./src
RUN mvn package -DskipTests

# ETAPA 2: Imagen de ejecución con OpenJDK 21 (JRE Alpine)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el ejecutable generado en la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Render asigna el puerto mediante la variable de entorno PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
