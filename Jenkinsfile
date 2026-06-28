pipeline {
    agent any

    stages {
        stage('Build docker image') {
            steps {
		sh 'docker compose down || true && docker compose up -d --build'
            }
        }

        stage('Checking the images') {
            steps {
                sh 'docker images'
            }
        }

        stage('Check running containers') {
            steps {
                sh 'docker ps -a'
            }
        }
    }

    post {
        success {
            echo 'Deployment successfully done!.. Application running on port 8080'
        }
        failure {
            echo 'Deployment failed...!!! Check the logs.....'
        }
        always {
            echo '.........Pipeline execution completed..........'
        }
    }
}
