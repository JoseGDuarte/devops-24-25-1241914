~~# Class Assignment 2: Virtualization with Vagrant

**Author:** José Duarte (1241914)<br>
**Programme:** SWitCH DEV<br>
**Course:** DevOps</br>

### Table of Contents

- [Introduction](#introduction)
- [Part 1](#part-1)

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

Once the app was up and running, I opened a browser and navigated to http://192.168.56.4:8080/, using the static IP assigned to my VM. The page loaded correctly, displaying the 
employee table, which confirmed that the application was successfully deployed and working as intended.

