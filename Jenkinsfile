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
                sh 'mvn clean'
            }
        }
        stage('Code Review') {
            steps {
                withSonarQubeEnv(credentialsId: 'gitea', installationName: 'SonarQube') { // You can override the credential to be used
                    sh 'mvn sonar:sonar'
                }
                timeout(time: 1, unit: 'HOURS') {
                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
                junit testResults: './target/surefire-reports/*.xml', allowEmptyResults: true
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}

