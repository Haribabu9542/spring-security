pipeline{
  environment {
    scannerHome = tool 'SonarQubeScanner'
    registry = "bagit.bassure.in/haribabu9542"
    registryCredential = 'dockerhub'
    dockerImage = ''
    NEXUS_VERSION = "nexus3"
    NEXUS_PROTOCOL = "http"
    NEXUS_URL = "localhost:8081"
    NEXUS_REPOSITORY = "maven-nexus-repo"
    NEXUS_CREDENTIAL_ID = "nexus"
    // ARTIFACT_VERSION = "${BUILD_NUMBER}"
  }
    agent any
      tools {
        jdk 'openjdk-18'
    }
  
      stages{  
        stage('maven install') {
            steps {
               sh 'sudo apt update && sudo apt install -y maven && sudo chmod 777 /var/run/docker.sock'
            }
        }  
        stage('docker compose build') {
            steps {
                // Step to pull the code from Git repository
                sh 'docker compose -f /var/lib/jenkins/workspace/mvn-project-test/docker-compose.yml up -d' 

            }
        } 
        // stage('clean') {
        //     steps {
        //        sh 'mvn clean'
        //     }
        // }
        stage('clen &  build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Sonarqube') {
            steps {
                script{
                    withSonarQubeEnv(credentialsId: 'sonar-token'){
                        // sh "${scannerHome}/bin/sonar-scanner"
                        // sh 'mvn sonar:sonar'
                        sh "mvn clean verify sonar:sonar -Dsonar.projectKey=myproject -Dsonar.projectName='myproject' -Dsonar.host.url=http://localhost:9000   -Dsonar.token=sqp_410d143ef26ab040337e2a747940fe22af3ac409"
                    }
                }
            }
        }

        // stage('SonarQube Quality Gate') {
        //     steps {
        //         script{
        //           timeout(time: 10, unit: 'MINUTES'){

        //             def qualitygate = waitForQualityGate()
        //             if (qualitygate.status != 'OK') {
        //                 abortPipeline:true
        //                 error "Pipeline aborted due to quality gate failure:   ${qualitygate.status}"
        //             }else{
        //                 echo "Quality Gate Passed." 
        //             }
        //           }
        //         }
        //     }
        // }

        // stage('Building image') {
        //     steps{
        //         script {
        //           dockerImage = docker.build("${registry}/my-app:${BUILD_NUMBER}")

        //         }
        //      }
        //   }
        //   stage('Push Image') {
        //       steps{
        //           script {
        //               docker.withRegistry( "https://bagit.bassure.in/", registryCredential )    {
        //                 dockerImage.push()
        //               }
        //                def imageTag = "${registry}/my-app:${BUILD_NUMBER}"

        //               sh "docker logout https://bagit.bassure.in/"
        //                    // Get the image tag
        //               sh "docker rmi ${imageTag}"

        //           }
                  
        //       }
        //     }


            stage("publish to nexus") {
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
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";

                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            // version: ARTIFACT_VERSION,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                // Artifact generated such as .jar, .ear and .war files.
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging]
                            ]
                        );

                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
        
    }
}