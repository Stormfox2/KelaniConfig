pipeline {
    agent any
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
                script {
                    // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                    pom = readMavenPom file: "pom.xml";
                    // Find built artifact under target folder
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    // Print some info from the artifact found
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    // Extract the path from the File found
                    artifactPath = filesByGlob[0].path;
                    // Assign to a boolean response verifying If the artifact name exists
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        if(artifactPath.name.contains('SNAPSHOT')) {
                            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: NEXUS_REPOSITORY_SNAPSHOT,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    // Artifact generated such as .jar, .ear and .war files.
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: artifactPath,
                                    type: pom.packaging],
                                    // Lets upload the pom.xml file for additional information for Transitive dependencies
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: "pom.xml",
                                    type: "pom"]
                                    ]
                                );
                            } else {
                                echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                                nexusArtifactUploader(
                                    nexusVersion: NEXUS_VERSION,
                                    protocol: NEXUS_PROTOCOL,
                                    nexusUrl: NEXUS_URL,
                                    groupId: pom.groupId,
                                    version: pom.version,
                                    repository: NEXUS_REPOSITORY_RELEASE,
                                    credentialsId: NEXUS_CREDENTIAL_ID,
                                    artifacts: [
                                        // Artifact generated such as .jar, .ear and .war files.
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: artifactPath,
                                        type: pom.packaging],
                                        // Lets upload the pom.xml file for additional information for Transitive dependencies
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: "pom.xml",
                                        type: "pom"]
                                    ]
                                );
                            }
                        }
                    }

                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
    }
}

