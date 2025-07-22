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
                sh './mvnw clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t eventapp .'
            }
        }

        stage('Docker Run') {
            steps {
                sh 'docker run -d -p 8080:8080 --name eventapp-container eventapp'
            }
        }
    }
}