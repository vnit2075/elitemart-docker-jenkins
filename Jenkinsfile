pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout code from Git
                git branch: 'main', url: 'https://github.com/vnit2075/elitemart-docker-jenkins.git'
            }
        }
        
        stage('Build & Deploy Docker Image') {
            steps {
                sh '''
                    # Stop and remove existing containers if they exist (don't fail if they don't)
                    docker compose down || true
                    
                    # Build new images and start containers in detached mode
                    docker compose up -d --build
                '''
            }
        }
        
        stage('Checking the images') {
            steps {
                sh 'docker images'
            }
        }
        
        stage('Check running containers') {
            steps {
                sh 'docker ps'
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution finished.'
        }
        success {
            echo '✅ Deployment successful! Application is running on port 8080.'
        }
        failure {
            echo '❌ Deployment failed. Check the logs above for details.'
        }
    }
}
