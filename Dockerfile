FROM ubuntu:latest
LABEL authors="devma"

ENTRYPOINT ["top", "-b"]

# =========================
# 1) BUILD (Maven)
# =========================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o pom.xml e baixa dependências (cache)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copia o código fonte
COPY src ./src

# Gera o .jar
RUN mvn -q clean package -DskipTests

# =========================
# 2) RUNTIME
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o jar do estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Copia as chaves (se você tiver uma pasta local "keys" na raiz)
COPY keys ./keys

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
