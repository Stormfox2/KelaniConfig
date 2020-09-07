pipeline {
    tools {
        // This can be nexus3 or nexus2
        NEXUS_VERSION = "nexus3"
        // This can be http or https
        NEXUS_PROTOCOL = "https"
        // Where your Nexus is running
        NEXUS_URL = "repo.ketarion.eu"
        // Repository where we will upload the releases
        NEXUS_REPOSITORY_RELEASE = "KelaniSystemsReleases"
        // Repository where we will upload the snapshots
        NEXUS_REPOSITORY_SNAPSHOT = "KelaniSystemsSnapshots"
        // Jenkins credential id to authenticate to Nexus OSS
        NEXUS_CREDENTIAL_ID = "gitea"

        //Maven
        maven 'Maven3'

        //Java
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
        stage('Compile') {
            steps {
                echo 'Compiling'
                sh 'mvn compile'
            }
        }
         stage('Code Review') {
                    steps {
                        echo 'Reviewing code'
                        withSonarQubeEnv(credentialsId: 'Sonar', installationName: 'SonarQube') { // You can override the credential to be used
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
        stage('Test') {
            steps {
                echo 'Running tests'
                sh 'mvn test'
                junit testResults: './target/surefire-reports/*.xml', allowEmptyResults: true
            }
        }
        stage('Archive') {
            steps {
                echo 'Packaging'
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar', excludes: 'target/origin*', fingerprint: true, followSymlinks: false, onlyIfSuccessful: true
            }
        }

        stage('Publish') {
            steps {

            }
        }
    }
}

