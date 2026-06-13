
Your Jenkinsfile should start directly with:

```groovy
pipeline {

    agent any

    tools {
        jdk 'Java21'
        maven 'Maven3'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Bhargav1258/milestone_Automation-practice.git'
            }
        }

        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                bat 'mvn compile'
            }
        }

        stage('Execute Tests') {
            steps {
                bat 'mvn test'
            }
        }
    }
}