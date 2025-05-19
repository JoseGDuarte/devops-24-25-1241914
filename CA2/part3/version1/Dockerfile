FROM gradle:jdk17 AS builder

WORKDIR /CA2/part3/version1

# Clone the gradle basic demo BitBucket repository
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

WORKDIR /CA2/part3/version1/gradle_basic_demo

RUN chmod +x gradlew && ./gradlew build --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /CA2/part3/version1/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

EXPOSE 59001

ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]