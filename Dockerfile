# ETAPA 1: Compilar la aplicación con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias (optimiza la caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente de Java y empaquetar sin ejecutar tests
COPY src ./src
RUN mvn package -DskipTests

# ETAPA 2: Imagen liviana de ejecución con OpenJDK 17
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el ejecutable generado en la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Render asigna automáticamente el puerto a través de la variable de entorno PORT
ENV PORT=8080
EXPOSE 8080

# Iniciar la aplicación pasando el puerto que asigna el entorno
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]