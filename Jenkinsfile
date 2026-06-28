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
                    # 1. Standard compose cleanup
                    docker compose down || true
                    
                    # 2. Force remove specific containers
                    docker rm -f elitemart-mysql || true
                    docker rm -f elitemart-app || true
                    
                    # 3. Build new images and start containers
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
