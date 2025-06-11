# Class Assignment 3: Automating Builds and Deployments with Jenkins

**Author:** José Duarte (1241914)<br>
**Programme:** SWitCH DEV<br>
**Course:** DevOps</br>

### Table of Contents

- [Introduction](#introduction)
- [Setting up](#setting-up)
- [Building the Gradle Basic Demo Pipeline](#building-the-gradle-basic-demo-pipeline)
- [Building the Spring Boot Basic Pipeline with Docker Image](#building-the-spring-boot-basic-pipeline-with-docker-image)
- [Conclusion](#conclusion)

### Introduction

This assignment focused on implementing CI/CD pipelines using Jenkins for two previously developed applications. 

I started on to the Gradle Basic Demo from CA1 Part 2. Before diving into the full pipeline configuration, I created a 
simple Jenkins job linked to a remote repository. This helped me better understand Jenkins’ integration with version control systems and how 
it can trigger builds based on code changes.

With that working, I proceeded to build a more complete pipeline for the Gradle project. The next challenge involved the application from CA1 
Part 3, a project with both a React and a Spring Boot. This setup required a more advanced configuration to manage the 
different parts of the stack—ensuring both were built, tested, and containerized correctly.

This report details the steps I followed to set up each pipeline and shares what I learned throughout the process.

## Setting up

To get started, I set up Jenkins by running the .war file directly, following the guidelines provided in the official Jenkins documentation. 
I did the following commands to set up Jenkins:

~~~
curl -L -O https://get.jenkins.io/war-stable/2.426.1/jenkins.war
ls -lh jenkins.war
java -jar jenkins.war
~~~

Once Jenkins was up and running, I accessed the web interface via http://localhost:8080 and completed the initial configuration process.

Part of that setup involved connecting Jenkins to GitHub by providing my credentials, which enabled it to access private repositories when 
needed. I also installed several key plugins to extend Jenkins’ capabilities—such as Git, Docker, and Docker Pipeline. While some plugins 
were added manually, others were prompted by error messages that appeared during job setup or pipeline execution.

With everything in place, Jenkins was fully configured and ready to handle builds and deployments for the applications used in this 
assignment.

## Building the Gradle Basic Demo Pipeline

For this task, I worked with the Gradle Basic Demo application. I created a Jenkins pipeline job that fetched and
executed a Jenkinsfile directly from a remote Git repository.

This was also my first time using Git authentication in Jenkins by adding a Git token as a credential. Since I had already set this up earlier, 
Jenkins was able to securely connect to the repository without issues.

Although the initial pipeline script was provided by my instructors, I customized it to better align with my project’s structure and local 
environment.

To set everything up, I opened Jenkins, clicked **New Item**, and chose the **Pipeline** option. I configured the job to use Pipeline script from 
SCM, selected **Git** as the version control system, and entered the URL of my repository. I pointed Jenkins to the correct branch 
(usually main) and set the **Script Path** to match where the Jenkinsfile is located in the repo.

Once saved, I clicked **Build Now** to run the pipeline and followed the execution steps via the console output.

Below is the *Jenkinsfile*  used for this practice:

~~~groovy
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from repository'
                git branch: 'main', url: 'https://github.com/JoseGDuarte/devops-24-25-1241914.git'
            }
        }
        stage('Assemble') {
            steps {
                dir('CA1/Part2/') {
                    echo 'Assembling...'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean assemble'
                }
            }
        }
        stage('Test') {
            steps {
                dir('CA1/Part2/') {
                    echo 'Running Tests...'
                    sh './gradlew test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
        stage('Archive') {
            steps {
                dir('CA1/Part2/') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: true
                }
            }
        }
    }
}
~~~

This pipeline carries out four main stages:
•	Checkout: Pulls the latest code from the repository’s main branch using GitHub token-based authentication.
•	Assemble: Executes the assemble task via Gradle to build and package the application without triggering tests.
•	Test: Runs the unit tests and publishes the results to Jenkins for review.
•	Archive: Stores the generated build artifacts so they can be accessed directly from the Jenkins interface.

The pipeline ran smoothly, with each stage completing successfully and no errors encountered. The screenshot below displays the final outcome of 
the Jenkins pipeline execution:

![Captura de ecrã 2025-06-03, às 15.36.33.png](images/Captura%20de%20ecra%CC%83%202025-06-03%2C%20a%CC%80s%2015.36.33.png)

## Building the Spring Boot Basic Pipeline with Docker Image

As part of this stage of the assignment, I built a Jenkins pipeline to handle the Spring Boot “basic” application from CA1, Part 3. This pipeline 
not only compiles and tests the app but also generates Javadoc documentation, archives build outputs, and publishes a Docker image to Docker Hub.

To enable Docker image deployment, I added my Docker Hub credentials to Jenkins. These were stored under the ID docker-credentials and referenced 
directly within the Jenkinsfile to allow secure authentication during the push step.

The pipeline is organized into the following stages:
•	Checkout: Pulls the application source code from the Git repository.
•	Assemble: Uses Gradle to compile and package the application.
•	Test: Executes tests and publishes the results in Jenkins.
•	Javadoc: Creates the project’s Javadoc and makes it available via Jenkins using the HTML Publisher plugin.
•	Archive: Stores the generated JAR files as build artifacts for later access.
•	Build and push Docker Image: Packages the application into a Docker image (running on Tomcat) and pushes it to Docker Hub using the configured 
credentials.

Here is the Jenkinsfile that defines the stages and logic for this pipeline:

~~~groovy
pipeline {
    agent any

    environment {
        PATH = "/usr/local/bin:$PATH"
        DOCKER_CREDENTIALS_ID = 'jenkins_docker'
        DOCKER_IMAGE = "zeduarte/jenkins-image"
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
        REPO_URL = 'https://github.com/JoseGDuarte/devops-24-25-1241914.git'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning repository...'
                git branch: 'main', url: env.REPO_URL
            }
        }

        stage('Create Dockerfile') {
            steps {
                dir('CA1/Part3/react-and-spring-data-rest-basic') {
                    echo 'Creating Dockerfile...'
                    script {
                        writeFile file: 'Dockerfile', text: '''
                            FROM openjdk:17-jdk-alpine
                            WORKDIR /app
                            COPY build/libs/app.jar app.jar
                            EXPOSE 8080
                            ENTRYPOINT ["java", "-jar", "app.jar"]
                        '''
                    }
                }
            }
        }

        stage('Assemble') {
            steps {
                dir('CA1/Part3/react-and-spring-data-rest-basic') {
                    echo 'Assembling project...'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA1/Part3/react-and-spring-data-rest-basic') {
                    echo 'Running unit tests...'
                    sh './gradlew test'
                    junit '**/build/test-results/test/*.xml'
                }
            }
        }

        stage('Javadoc') {
            steps {
                dir('CA1/Part3/react-and-spring-data-rest-basic') {
                    echo 'Generating Javadoc...'
                    sh './gradlew javadoc'
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'build/docs/javadoc',
                        reportFiles: 'index.html',
                        reportName: 'API Documentation'
                    ])
                }
            }
        }

        stage('Archive') {
            steps {
                dir('CA1/Part3/react-and-spring-data-rest-basic') {
                    echo 'Archiving JAR...'
                    sh 'mv build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar build/libs/app.jar'
                    archiveArtifacts artifacts: 'build/libs/app.jar', allowEmptyArchive: true
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                dir('CA1/Part3/react-and-spring-data-rest-basic') {
                    script {
                        echo 'Building and pushing Docker image...'
                        try {
                            sh 'docker info'

                            def imageTag = "${DOCKER_IMAGE}:${BUILD_ID}"
                            sh "docker build -t ${imageTag} ."

                            docker.withRegistry(DOCKER_REGISTRY, DOCKER_CREDENTIALS_ID) {
                                sh "docker push ${imageTag}"
                            }

                            echo "Docker image pushed: ${imageTag}"
                        } catch (err) {
                            echo " Docker build or push failed: ${err.getMessage()}"
                            echo "Check if Docker is running and credentials are correct."
                            currentBuild.result = 'UNSTABLE'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo ' Cleaning workspace...'
            cleanWs()
        }
        success {
            echo ' Pipeline executed successfully!'
        }
        failure {
            echo ' Pipeline failed!'
        }
    }
}
~~~

The pipeline completed without any issues, and the Docker image was successfully uploaded to Docker Hub.
The screenshots below provide visual confirmation of the process:

![Captura de ecrã 2025-06-11, às 14.25.14.png](images/Captura%20de%20ecra%CC%83%202025-06-11%2C%20a%CC%80s%2014.25.14.png)
![Captura de ecrã 2025-06-11, às 14.30.57.png](images/Captura%20de%20ecra%CC%83%202025-06-11%2C%20a%CC%80s%2014.30.57.png)

## Conclusion

This assignment was a valuable hands-on experience that deepened my understanding of Jenkins and continuous integration practices. I began by 
experimenting with basic pipelines to get familiar with the Jenkins interface and then gradually moved on to designing more robust and automated 
pipelines for two applications I had previously developed.

By the end of the task, I had functioning pipelines capable of compiling, testing, generating documentation, archiving key build outputs, and pushing
Docker images to Docker Hub. I also learned how to configure Jenkins to securely interact with external services like GitHub and Docker by managing 
credentials effectively.

Altogether, this project helped me tie together multiple DevOps concepts and tools, giving me a clearer and more practical understanding of how to 
automate builds and deployments in real-world scenarios.
