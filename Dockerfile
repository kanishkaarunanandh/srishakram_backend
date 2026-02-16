# Use Maven + JDK image to build the JAR
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /build

# Copy
COPY . .

# Build the JAR (skip tests if you want faster builds)
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /build/target/srishakram-0.0.1-SNAPSHOT.jar app.jar
COPY ./src/main/resources/application-prod.properties  /app/config/application-prod.properties

COPY ./.env  /app

ENV SPRING_CONFIG_LOCATION=/app/config/application-prod.properties
ENV SPRING_PROFILES_ACTIVE=prod

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
