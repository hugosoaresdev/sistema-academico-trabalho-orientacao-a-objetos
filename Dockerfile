# --- Estágio 1: Build ---
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

# Copia as configurações do Maven e baixa as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e os recursos (src/main/resources)
COPY src ./src

# Compila e gera o Fat-JAR limpando os testes para build rápido
RUN mvn clean package -DskipTests

# --- Estágio 2: Runtime (Execução) ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Instala estritamente o necessário para o JavaFX abrir interface gráfica no Linux/X11
RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    libgtk-3-0 \
    libasound2 \
    libxxf86vm1 \
    && rm -rf /var/lib/apt/lists/*

# Copia o JAR gerado pelo Shade Plugin (ele gera o nome baseado no seu artefactId e versão)
COPY --from=build /app/target/academic-system-1.0-SNAPSHOT.jar app.jar

# Executa o sistema
CMD ["java", "-jar", "app.jar"]