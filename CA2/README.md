~~# Class Assignment 2: Virtualization with Vagrant

**Author:** José Duarte (1241914)<br>
**Programme:** SWitCH DEV<br>
**Course:** DevOps</br>

### Table of Contents

- [Introduction](#introduction)
- [Part 1](#part-1)
- - [Part 2](#part-2)
    -[Part 2.1](#part-21)
    -[Part 2.2](#part-22)
- [Part 3](#part-3)
  -[Part 3.1](#part-31)
  -[Part 3.2](#part-32)
- [Part 4](#part-4)

### Introduction

**Part 1** This report aims to explore the concepts of virtualization through the use of UTM running Ubuntu. Building on previous coursework, the tasks here involve setting up 
and using virtual environments to replicate earlier projects in a controlled and isolated setting. 

---

##  Part 1

### Setting Up

The first objective is to familiarize ourselves with virtualization tools and environments. Since the virtual machine was already created during a previous course (SCOMRED), the initial setup 
process could be skipped. The VM already had a Host-only network configured with a static IP and essential tools installed.

To remotely manage the VM, the OpenSSH server was installed using sudo apt install openssh-server. SSH password authentication was enabled by editing /etc/ssh/sshd_config and setting 
PasswordAuthentication yes. The SSH service was restarted afterward with sudo service ssh restart.

For file transfer capabilities, a FTP server was also configured. This was done by installing vsftpd with sudo apt install vsftpd, modifying the configuration file (/etc/vsftpd.conf) to 
enable writing, and restarting the service using sudo service vsftpd restart.

### Cloning the Repository

To bring project code into the VM, SSH keys were used for secure authentication with GitHub. An SSH key pair was created using the following command and also the public key was displayed and 
then added to GitHub under the SSH keys section of the account settings.

~~~bash 
#generating the key
ssh-keygen -t ed25519 -C "1241914@isep.ipp.pt"

#displaying the key
cat ~/.ssh/id_ed25519.pub
~~~

This allowed the repository to be cloned directly into the VM:

`git clone git@github.com:danielabmartins/devops-24-25-1241902.git`

### Installing Required Tools

With the repository ready, it was necessary to install all software dependencies to replicate the development environment. This included:

~~~bash
#update and upgrade the already installed packages
sudo apt update
sudo apt upgrade

#install Git for version control and source code management
sudo apt install git 

#install JDK and JRE for Java-based projects
sudo apt install openjdk-17-jdk openjdk-17-jre

#install Maven for building and managing Java projects
sudo apt install maven

#install Gradle for building and managing Java projects
wget https://services.gradle.org/distributions/gradle-8.6-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-8.6-bin.zip
~~~

Gradle was added to the system path via *.bashrc*:

~~~bash
echo "export GRADLE_HOME=/opt/gradle/gradle-8.6" >> ~/.bashrc
echo "export PATH=\$GRADLE_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc****
~~~

Versions of all tools were checked to confirm successful installation:

~~~bash
git --version
java --version
mvn --version
gradle --version
~~~

### Running the Spring Boot Tutorial Basic Project

The Spring Boot tutorial app from a previous assignment was executed inside the VM. After navigating to the appropriate directory, the wrapper was installed using ``mvn -N wrapper:wrapper`` and once
this was ready, I executed the command `./mvnw spring-boot:run`. Similarly to my first assignment,I needed to access the application. This time, however, I wanted to ensure  that it was accessible 
externally.
For this, I used the URL `http://192.168.56.4:8080/` with my VM's IP address that I found using the ``ifconfig`` command.

![Captura de ecrã 2025-04-02, às 16.11.26.png](images/Captura%20de%20ecra%CC%83%202025-04-02%2C%20a%CC%80s%2016.11.26.png)

### Running the Gradle_Basic_Demo Project (1)

For this part, I am going to focus on building and running the gradle_basic_demo from CA1.2. To fulfill  this project, I needed to run the application in two environments: 
the virtual and the host machine.

I first moved to the gradle_basic_demo directory in my Virtual Machine where I executed the command ``gradle wrapper``
so that I could install the wrapper. Then, I was able to run the following command:

``./gradlew build``

Since I was using an Ubuntu Server, I did not have a desktop environment to run GUI apps like this
project's chat client. In order to make this work, I opened a terminal on my host machine and
moved to the gradle_basic_demo directory. Once I ran the appropriate command I created the Client
that could then communicate with the Server running on my Virtual Machine:

``./gradlew runClient --args="192.168.56.4 59001" ``

![Captura de ecrã 2025-04-08, às 16.53.05.png](images/Captura%20de%20ecra%CC%83%202025-04-08%2C%20a%CC%80s%2016.53.05.png)
![Captura de ecrã 2025-04-08, às 16.55.15.png](images/Captura%20de%20ecra%CC%83%202025-04-08%2C%20a%CC%80s%2016.55.15.png)

As seen in the images above, I successfully ran the command and opened the chat windows which ensured
that the application was running smoothly.


### Executing the Gradle_Basic_Demo Project (2)
I was now meant to work on developing and running another component of the
gradle_basic_demo project within the virtual machine. The expected outcome should
be the table with employee information provided in the Spring Boot Tutorial Basic Project.

To begin, I headed to the folder 'react-and-spring-boot' and ran the following commands
to build and run the application properly.

~~~bash
./gradlew build
./gradlew bootRun
~~~

Once the app was up and running, I opened a browser and navigated to http://192.168.56.4:8080/, using the static IP assigned to 
my VM. The page loaded correctly, displaying the  employee table, which confirmed that the application was successfully deployed 
and working as intended.

![Captura de ecrã 2025-04-08, às 16.59.46.png](images/Captura%20de%20ecra%CC%83%202025-04-08%2C%20a%CC%80s%2016.59.46.png)

---
# Part 2
##  Part 2.1

This assignment focuses on setting up a virtualised environment using Vagrant to deploy a Spring Boot application connected to 
an H2 database. The goal was to configure and run this environment inside virtual machines, enabling seamless deployment and testing.
The aim of this part of the assignment was to establish a virtual infrastructure capable of running a Spring Boot application backed by an H2 database. 
To do this, a provided Vagrantfile was adapted and expanded to suit the specific needs of the application.
This document outlines the configuration steps, integration process, and successful execution of the application within the virtualised setup.

### Environment Setup

To begin, I installed Vagrant by downloading it from the official website. Once installed, I verified the installation by executing the command
``vagrant --version`` as to ensure that everything was set.
Additionally, I updated my .gitignore file to exclude the .vagrant/ directory to prevent unnecessary files from being committed to version control.

To proceed with the setup, I completed the following steps:

- **Downloading the Base Project**

I cloned the base project from Bitbucket. This project  included the **Vagrantfile** as well as all the initial configurations to set up the Virtual Machines.

- **Copying the Vagrantfile** 

I copied the Vagrantfile into my project directory using:

~~~bash
cp cp ~/vagrant-multi-spring-tut-demo/macOS/Vagrantfile ~/Documents/GitHub/devops-24-25-1241914/CA2/part2
~~~

- **Starting the Virtualised Environment** 

Instead of the default VirtualBox provider, I used QEMU as the virtualisation provider due to compatibility issues on my system. This required me to configure the 
Vagrantfile accordingly and install the necessary QEMU dependencies.

~~~bash
vagrant up --provider=qemu
~~~

This launched two VMs:
•	db for the H2 database
•	web for the Spring Boot application

- **Accessing the application** 

Once the environment was up, I accessed the application via:

~~~web 
http://localhost:8080/basic-0.0.1-SNAPSHOT/
http://localhost:8080/basic-0.0.1-SNAPSHOT/h2-console
~~~

### Configuring the Vagrantfile  

The Vagrantfile serves as the core configuration file that instructs Vagrant on how to create and provision the virtual machines. 
It defines details such as the base operating system, resource allocation and the steps required to install/launch the application.
To meet the requirements of this project, I made several key adjustments so that the Spring Boot application could run seamlessly alongside 
the H2 database within a virtualised setup.

- **Updating the Repository URL** 

I updated the repository URL in the provisioning script to clone my own GitHub project, which contains the Spring Boot + Gradle application:

~~~bash 
git clone git@github.com:JoseGDuarte/devops-24-25-1241914.git
~~~

- **Navigating to the Correct Path**  

After cloning, I modified the path to target the Spring Boot application directory:

~~~bash 
cd devops-24-25-1241914/CA1/part3/react-and-spring-data-rest-basic
~~~

- **Adding the bootRun command** 

I added the Gradle bootRun command to the provisioning steps so that the Spring Boot application would launch automatically once the VM was up:

~~~bash 
./gradlew bootRun
~~~

- **Updating the Java version** 

To ensure compatibility with the application, I updated the VM to use OpenJDK 17

~~~bash 
openjdk-17-jdk-headless
~~~

- **Enabling the SSH Agent Forwarding** 

Since my repository is private, I enabled SSH agent forwarding. This lets the virtual machine use my SSH credentials from the host system, avoiding the 
need to store SSH keys inside the VM:

~~~bash 
config.ssh.forward_agent = true
~~~

- **Trusting GitHub’s SSH key**

To prevent SSH from prompting the VM to confirm GitHub’s fingerprint, I added a command that preemptively trusts GitHub as a known host:

~~~bash 
if [ ! -n "$(grep "^github.com " ~/.ssh/known_hosts)" ]; then
   ssh-keyscan github.com >> ~/.ssh/known_hosts 2>/dev/null
fi
~~~

Before running vagrant up, I made sure the SSH agent was active and that my private key was added, using the following commands:

~~~bash 
#starting the agent
eval "$(ssh-agent -s)"

#adding the SSH key
ssh-add ~/.ssh/id_ed25519 
~~~

These SSH steps were already set up during CA2 – Part 1, where I created the key and added it to my GitHub account.

With these changes in place, the Vagrantfile was now fully configured to meet the needs of this project.
The full file can be found [here](~/Documents/devops-24-25-1241914/CA2/part2/Vagrantfile). 

### Connecting Spring Boot to H2 Database 

To integrate the Spring Boot application with the external H2 database running on its own VM, I made the following configuration updates:

- **application.properties**

~~~ 
server.servlet.context-path=/basic-0.0.1-SNAPSHOT
spring.data.rest.base-path=/api
spring.datasource.url=jdbc:h2:tcp://192.168.56.11:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.sql.init.mode=never
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
~~~

- **src/App.js**

To match the updated Spring Boot context path, I also changed the frontend API call:

~~~
client({method: 'GET', path: '/basic-0.0.1-SNAPSHOT/api/employees'}).done(response => {
~~~

### Running the application

Since I was using QEMU as the provider (instead of the default VirtualBox), I added the --provider=qemu flag: ```vagrant up --provider=qemu```.

The vagrant up command bootstraps and provisions two virtual machines:
•	db: responsible for hosting the H2 database
•	web: where the Spring Boot application runs

During provisioning, all necessary software was installed, including Git, Java, Gradle, and the Spring Boot application itself.

Once the environment was ready, I tested the deployment by visiting:

~~~
http://localhost:8080/basic-0.0.1-SNAPSHOT/
http://localhost:8080/basic-0.0.1-SNAPSHOT/h2-console
~~~

After logging into the H2 web console, I confirmed that:
•	The Spring Boot app was connected to the database
•	Data operations (create, read, update, delete) worked as expected
•	I could inspect the database schema and records from the browser

This end-to-end verification demonstrated that the virtualised environment was correctly set up and functioning as intended.

---

##  Part 2.2

In this section, an alternative virtualisation approach is explored using UTM, a QEMU-based hypervisor, instead of VirtualBox. 
Both tools provide virtualisation capabilities suitable for development environments, but they differ significantly in compatibility, 
performance, and integration with DevOps tools like Vagrant.

| Feature                          | **UTM**                                                                        | **VirtualBox**                                                                  |
|----------------------------------|--------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| **Cross-platform Support**       | Native support for macOS.                                                      | Available on Windows, macOS, and Linux.                                         |
| **Ease of Use**                  | Lightweight interface; configuration is more manual.                           | User-friendly and straightforward interface.                                    |
| **Hardware Compatibility**       | Excellent for Apple Silicon (M1/M2), thanks to native ARM support.             | Broader compatibility across platforms, but may lag slightly in newer features. |
| **Licensing**                    | Free and open-source (QEMU/UTM).                                               | Free and open-source (Oracle VM VirtualBox).                                    |
| **Performance**                  | Optimised for Apple Silicon; fast with native ARM VMs, slower under emulation. | Slightly slower performance, but adequate for most development tasks.           |
| **Snapshot Functionality**       | Basic snapshot features; manual setup of devices.                              | Functional but less refined compared to VMware.                                 |
| **Vagrant Plugin Support**       | Not supported natively by Vagrant. VM provisioning must be manual.             | Has free and native support in Vagrant.                                         |


When using UTM, the virtual machine setup process involves more manual configuration compared to VirtualBox with Vagrant:
•	The VM must be created and configured manually within the UTM interface.
•	The operating system and all dependencies (e.g. Java, Git, Maven) need to be installed manually via SSH or terminal.
•	Networking (such as port forwarding or bridged mode) must be configured in UTM’s settings to enable external access to services like a Spring Boot application.

Despite the lack of native Vagrant support, UTM successfully supports custom development environments — especially on Apple Silicon machines,
where other hypervisors may struggle to run efficiently.

- **Using VMware**

VirtualBox integrates smoothly with Vagrant, a tool for automating virtual machine setup. With a Vagrantfile, developers can:
•	Automatically create and provision virtual machines.
•	Define VM settings such as CPU, memory, networking, and shared folders.
•	Provision software stacks using shell scripts or configuration management tools.

An example configuration using VirtualBox and Vagrant might look like:

~~~bash
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/bionic64"
  config.vm.provider "vmware_desktop" do |v|
    v.vmx["memsize"] = "1024"
    v.vmx["numvcpus"] = "2"
  end
end
~~~
Alternatively, you can run the VMs by explicitly choosing the provider with the command:

~~~bash
vagrant up --provider=vmware_desktop
~~~

This automatically sets up the virtual machine, installs required packages, and starts any configured services — making it ideal for 
reproducible development environments.

--- 
### Conclusion

This section explored the setup of a virtualised environment for CA2 – Part 2 using two different approaches: UTM (manual and QEMU-based) 
and VirtualBox with Vagrant (automated and x86-based). While VirtualBox offers tight integration with Vagrant for automated provisioning, 
UTM provides excellent support for Apple Silicon and flexibility for ARM-based environments.

The comparison highlights how different tools can be used to achieve the same goal — a functioning Spring Boot application running inside a 
VM connected to an H2 database. Working with both platforms provided valuable insights into virtualisation workflows, platform compatibility,
and the trade-offs between automation and manual configuration.

Ultimately, this flexibility is one of Vagrant’s strengths: even if native support for a provider is unavailable, the core infrastructure 
can still be adapted manually. This reinforces important DevOps principles, including adaptability, reproducibility, and platform awareness.

---
# Part 3

This section of the assignment focuses on using Docker to package and run a chat server application. The chat server was created in a previous task, and now the objective is to use Docker to ensure the application behaves the same way across any machine or operating system.

There are two ways this part is tackled:
•	Option 1: Compile the server application within the Dockerfile.
•	Option 2: Compile the application locally on the machine, then include the compiled file in the Docker image.

##  Part 3.1

To begin with the first method, I installed Docker and signed in with my DockerHub account.

In this method, everything is built within the Docker environment itself. The Dockerfile begins by using a Gradle image that includes JDK 11 to compile the source code. After building, it switches to a smaller Java runtime image to run the application. The final JAR file is copied into this runtime image, and the server is configured to listen on port 12345.

Here’s the Dockerfile used:

~~~dockerfile
FROM gradle:jdk17 AS builder

WORKDIR /CA2/part3/version1

RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

WORKDIR /CA2/part3/version1/gradle_basic_demo

RUN chmod +x gradlew && ./gradlew build --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /CA2/part3/version1/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

EXPOSE 59001

ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
~~~

Once the Dockerfile was ready, I ran the following command to build the Docker image:
``docker build -t 1241914/chat-server:v1 .``

By using the **-t** flag, I am able to create a custom name and version so that I can identify it easier.
Next, I ran the command ``docker images`` to ensure that the image was built correctly.

![Captura de ecrã 2025-05-13, às 15.41.41.png](images/Captura%20de%20ecra%CC%83%202025-05-13%2C%20a%CC%80s%2015.41.41.png)


The next step was to run the Docker container and for that I used the command ``docker run -p 59001:59001 1241914/chat-server:v1``

![Captura de ecrã 2025-05-13, às 15.28.06.png](images/Captura%20de%20ecra%CC%83%202025-05-13%2C%20a%CC%80s%2015.28.06.png)

Now that the chat server was running, I needed to launch the chat client to verify that the application was functioning correctly.

![Captura de ecrã 2025-05-13, às 15.42.10.png](images/Captura%20de%20ecra%CC%83%202025-05-13%2C%20a%CC%80s%2015.42.10.png)

To do this, I opened two new terminals and ran the following commands to start the Client side of the application:
~~~bash
./gradlew build
./gradlew runClient
~~~

Both clients were able to communicate through the server running inside Docker. 

![Captura de ecrã 2025-05-13, às 15.29.00.png](images/Captura%20de%20ecra%CC%83%202025-05-13%2C%20a%CC%80s%2015.29.00.png)

After confirming that everything worked, I pushed the second version to DockerHub:

~~~bash
#tagging the image
docker tag 1241914/chat-server:v1 zeduarte/chat-server:v1
#pushing the image to DockerHub
docker push zeduarte/chat-server:v1
~~~

##  Part 3.2

In the second approach, I first built the application outside of Docker using my own system, then included the generated .jar file in the image.

I then made a new dockerfile for version2

~~~dockerfile
FROM gradle:jdk21 AS builder

# creates this directory in the docker image
WORKDIR /app

# copy the part2 chat app from my host machine to the docker image
COPY CA1/part2/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

EXPOSE 59001

ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
~~~

I built the image with ``docker build -t 1241914/chat-server:v2 .``

Then launched the container with ``docker run -p 59001:59001 1241914/chat-server:v2``

Just like in the first method, I opened two new terminals and ran the chat client ``./gradlew runClient``

![Captura de ecrã 2025-05-13, às 16.53.04.png](images/Captura%20de%20ecra%CC%83%202025-05-13%2C%20a%CC%80s%2016.53.04.png)

Both clients were able to communicate through the server running inside Docker. After confirming that everything worked, I pushed the second version to DockerHub:

~~~bash
#tagging the image
docker tag 1241914/chat-server:v1 zeduarte/chat-server:v2
#pushing the image to DockerHub
docker push zeduarte/chat-server:v2
~~~

![Captura de ecrã 2025-05-13, às 17.03.32.png](images/Captura%20de%20ecra%CC%83%202025-05-13%2C%20a%CC%80s%2017.03.32.png)
![Captura de ecrã 2025-05-13, às 17.03.48.png](images/Captura%20de%20ecra%CC%83%202025-05-13%2C%20a%CC%80s%2017.03.48.png)


In this part of the assignment, I successfully used Docker to containerize the chat server using two distinct methods:
1.	Building the project entirely inside Docker.
2.	Compiling it first on my machine and only using Docker to run the result.

Both methods proved that Docker helps maintain consistency across environments, simplifying the deployment and testing process.

---
# Part 4

In this section, I explain the process I followed to containerise a Spring Boot application using Docker. The goal was to replace the 
Vagrant-based setup from Part 2 of the CA with a Docker-based solution where both the application and its H2 database would run inside 
separate containers. To manage these containers together, I used Docker Compose, which simplifies the orchestration of multi-service 
environments. The report details how I crafted the Dockerfiles, configured the docker-compose.yml, mounted a volume for database persistence,
and uploaded the resulting images to Docker Hub. This task significantly improved my grasp of Docker for application deployment and environment management.

### db.Dockerfile

To start, I prepared a Dockerfile for the H2 database server and named it db.dockerfile. The file includes all the necessary steps to set up the container, 
and below is the breakdown of what each instruction does:

~~~dockerfile
FROM openjdk:11-jre-slim

RUN apt-get update \
    && apt-get install -y wget \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/src/app

RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

EXPOSE 8082
EXPOSE 9092

CMD ["java", "-cp", "./h2-1.4.200.jar", "org.h2.tools.Server", \
     "-tcp", "-tcpAllowOthers", "-ifNotExists", \
     "-web", "-webAllowOthers"]
~~~

### web.Dockerfile

Next, I wrote a Dockerfile for the main web application, which integrates Spring Boot with a React frontend. This file, named web.dockerfile, is responsible for compiling 
and packaging the project into a deployable WAR file. Here’s a summary of the file content:

~~~dockerfile
FROM openjdk:17-jdk-slim

RUN apt-get update \
    && apt-get install -y git \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/src/app

RUN git clone https://github.com/JoseGDuarte/devops-24-25-1241914.git .

WORKDIR /usr/src/app/CA1/part3/react-and-spring-data-rest-basic

RUN chmod +x gradlew \
    && ./gradlew clean bootJar

EXPOSE 8080

CMD ["java", "-jar", "build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar"]
~~~

### docker-compose

To coordinate the two services, I set up a docker-compose.yml file. This file defines how the database and application containers are built and connected, and how ports and 
environment variables are configured.

~~~dockerfile
version: '3.8'

services:
  db:
    build:
      context: .
      dockerfile: db/Dockerfile
    container_name: h2_database
    ports:
      - "8082:8082"  # H2 Web Console
      - "9092:9092"  # H2 TCP Server
    networks:
      - app-network

  web:
    build:
      context: .
      dockerfile: web/Dockerfile
    container_name: spring_web_app
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:h2:tcp://db:9092/~/test
      SPRING_DATASOURCE_USERNAME: sa
      SPRING_DATASOURCE_PASSWORD:
    networks:
      - app-network

networks:
  app-network:
    driver: bridge
~~~

After setting everything up, I launched the services using:

```docker-compose up --build ```

![Captura de ecrã 2025-05-19, às 11.32.23.png](images/Captura%20de%20ecra%CC%83%202025-05-19%2C%20a%CC%80s%2011.32.23.png)
![Captura de ecrã 2025-05-21, às 11.10.15.png](images/Captura%20de%20ecra%CC%83%202025-05-21%2C%20a%CC%80s%2011.10.15.png)

Once the containers were running, I could successfully access:
•	The application at: http://localhost:8080
•	The H2 console at: http://localhost:8082

### Wrapping it up

The next stage of this project involved tagging and pushing the images to my Docker Hub repository. The final step of this project 
involved tagging and pushing the images to my Docker Hub repository. For consistency with Part 3, I followed the same approach 
by building each image individually using the docker build command and assigning custom tags during the build process. 
Specifically, I used the following commands:

~~~bash
docker build -t 1241914/part4-db:db ./db
docker build -t 1241914/part4-web:web ./web
~~~

These commands ensured that each image was tagged appropriately right from the start. I verified that the images were created and tagged 
correctly using the docker images command.

Once confirmed, I pushed both images to my Docker Hub repository using:

~~~bash
docker push zeduarte/part4-db:db
docker push zeduarte/part4-web:web
~~~

This completed the containerization and publication process, making the images available for deployment or sharing via Docker Hub.

### Working with Volumes

To keep the H2 database file available outside the container, I mapped a volume in the docker-compose.yml. Once the service was up, 
I accessed the database container’s shell using ```docker-compose exec db bash```.

Within the container, I verified the presence of the database file at /root/test.mv.db and copied it to the shared volume path 
with ``cp /root/test.mv.db /usr/src/data-backup/`` and exited the container with ``exit``. Back on the host machine, I checked 
the ./db-data folder and confirmed that the file was copied successfully. This validated that volume mapping was functioning as expected.

--- 
### Conclusion

This segment of the project was an excellent practical dive into Docker. I managed to containerise the entire application stack, coordinate 
services using Docker Compose, and ensure persistent data storage with volumes. It provided a clear picture of how Docker simplifies 
application deployment, especially for multi-container environments.