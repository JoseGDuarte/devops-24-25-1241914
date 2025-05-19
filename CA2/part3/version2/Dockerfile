FROM gradle:jdk21 AS builder

# creates this directory in the docker image
WORKDIR /app

# copy the part2 chat app from my host machine to the docker image
COPY CA1/part2/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

EXPOSE 59001

ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]