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
               sh 'sudo apt update && sudo apt install -y maven'
            }
        }   
        stage('clean') {
            steps {
               sh 'mvn clean'
            }
        }
        stage('test &  build') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Sonarqube') {
            steps {
                script{
                    withSonarQubeEnv('mytoken'){
                        sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
            }
        }
        stage('SonarQube Quality Gate') {
            steps {
                script{
                  timeout(time: 5, unit: 'MINUTES'){
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