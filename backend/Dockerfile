FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw -pl backend -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/backend/target/gerenciadordegendamentosdebarbearia-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]