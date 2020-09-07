pipeline {
    agent any
    tools {
        maven 'Maven3'
        jdk 'Java11'
    }
    options {
        buildDiscarder logRotator(numToKeepStr: '10')
    }
    stages {
        stage('Clean') {
            steps {
                echo 'Clearing workspace'
                sh 'mvn clean'
            }
        }
        stage('Code Review') {
            steps {
                echo 'Reviewing code'
                withSonarQubeEnv(credentialsId: 'de486a49-f681-4856-9c08-c4bb79a40599', installationName: 'SonarQube') { // You can override the credential to be used
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage("Quality Gate"){
            steps {
                timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Compile') {
            steps {
                echo 'Compiling'
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests'
                sh 'mvn test'
                junit testResults: './target/surefire-reports/*.xml', allowEmptyResults: true
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging'
                sh 'mvn package'
            }
        }
    }
}

