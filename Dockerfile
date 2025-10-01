# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Run
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
"-b"]