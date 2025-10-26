# ---------- STAGE 1: build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# ---------- STAGE 2: runtime ----------
FROM eclipse-temurin:21-jdk AS runtime

WORKDIR /app

# Copiamos el jar construido
COPY --from=builder /app/target/starwars-fan-0.0.1-SNAPSHOT.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Variable de entorno opcional
ENV SPRING_PROFILES_ACTIVE=local

ENTRYPOINT ["java","-jar","app.jar"]