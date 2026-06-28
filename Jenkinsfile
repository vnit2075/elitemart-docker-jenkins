pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/vnit2075/elitemart-docker-jenkins.git'
            }
        }

        stage('Build docker image') {
            steps {
                sh 'docker compose up -d --build'
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
