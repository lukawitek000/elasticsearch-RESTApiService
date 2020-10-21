From openjdk:11-jre-slim
EXPOSE 8080
ADD ./target/restapiservice-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=gcp", "app.jar"]