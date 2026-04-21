FROM amazoncorretto:17

WORKDIR /app

COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:1.0.0-x86_64 /lambda-adapter /opt/extensions/lambda-adapter

COPY build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
