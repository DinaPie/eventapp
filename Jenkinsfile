pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/DinaPie/eventapp.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
                sh './mvnw install -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t eventapp .'
            }
        }

        stage('Docker Run') {
            steps {
                sh 'docker run -d -p 8080:8080 eventapp'
            }
        }
    }

    post {
        always {
            sh 'docker stop eventapp-container || true'
            sh 'docker rm eventapp-container || true'
        }
    }
}
