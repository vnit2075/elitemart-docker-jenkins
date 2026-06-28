pipeline {
    agent any

    stages {

        stage('Build & Deploy') {
            steps {
                sh '''
                    docker compose down || true
                    docker compose up -d --build
                '''
            }
        }

        stage('Verify Images') {
            steps {
                sh 'docker images'
            }
        }

        stage('Verify Containers') {
            steps {
                sh 'docker ps -a'
            }
        }

        stage('Application Health') {
            steps {
                sh 'sleep 20'
                sh 'curl -I http://localhost:8080 || true'
            }
        }
    }

    post {

        success {
            echo '========================================='
            echo 'Deployment Successful'
            echo 'Application : http://<EC2-PUBLIC-IP>:8080'
            echo '========================================='
        }

        failure {
            echo '========================================='
            echo 'Deployment Failed'
            echo 'Check Jenkins Console Output'
            echo '========================================='
        }

        always {
            echo 'Pipeline Execution Completed'
        }
    }
}
