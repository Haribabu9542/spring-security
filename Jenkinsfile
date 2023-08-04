pipeline{
  environment {
    scannerHome = tool 'SonarQubeScanner'
    registry = "bagit.bassure.in/haribabu9542"
    registryCredential = 'd02083cb-8700-49c3-abc7-53c57d9b86a1'
    dockerImage = ''
  }
// pipeline {
    agent any

    stages {
        stage('maven install') {
            steps {
               sh 'sudo apt update && sudo apt install -y maven && sudo chmod 777 /var/run/dockre.sock'
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
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
        stage('SonarQube Quality Gate') {
            steps {
                script{
                  timeout(time: 10, unit: 'MINUTES'){
                    def qualitygate = waitForQualityGate()
                    if (qualitygate.status != 'OK') {
                        abortPipeline:true
                        error "Pipeline aborted due to quality gate failure:   ${qualitygate.status}"
                    }else{
                        echo "Quality Gate Passed"
                    }
                  }
                }
            }
        }
        stage('Building image') {
            steps{
                script {
                  dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
             }
          }
          stage('Push Image') {
              steps{
                  script {
                      docker.withRegistry( '', registryCredential )    {
                        dockerImage.push()
                      }
                  }
              }
            }
        
    }
}