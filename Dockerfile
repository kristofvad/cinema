FROM openjdk:17-jdk-alpine3.14

COPY "./web/target/cinema.jar" "/application/cinema.jar"

CMD ["java", "-jar", "/application/cinema.jar"]