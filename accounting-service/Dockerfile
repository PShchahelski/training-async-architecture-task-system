FROM gradle:jdk17 AS build
WORKDIR /app
COPY . /app/.
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/*.jar
EXPOSE 7080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
