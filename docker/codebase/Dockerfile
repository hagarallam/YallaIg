FROM openjdk:22-slim AS build
RUN apt-get update && apt-get install -y maven
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim
COPY --from=build /target/yallaIg-backend-0.0.1-SNAPSHOT.jar yallaIg-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","yallaIg-backend.jar"]