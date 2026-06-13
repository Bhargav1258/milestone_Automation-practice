pipeline {

```
agent any

tools {
    maven 'Maven3'
    jdk 'JDK21'
}

stages {

    stage('Checkout Source Code') {
        steps {
            echo 'Cloning latest project from GitHub...'
            git branch: 'main',
            url: 'https://github.com/Bhargav1258/milestone_Automation-practice.git'
           }
    }

    stage('Clean Workspace') {
        steps {
            echo 'Cleaning previous build files...'
            bat 'mvn clean'
        }
    }

    stage('Compile Project') {
        steps {
            echo 'Compiling Maven Project...'
            bat 'mvn compile'
        }
    }

    stage('Execute Smoke Suite') {
        steps {
            echo 'Executing Smoke Test Suite...'
            bat 'mvn test -DsuiteXmlFile=smoke.xml'
        }
    }

    stage('Execute Regression Suite') {
        steps {
            echo 'Executing Regression Test Suite...'
            bat 'mvn test -DsuiteXmlFile=regression.xml'
        }
    }
}

post {

    always {

        echo 'Publishing Reports...'

        publishHTML(target: [
            allowMissing: true,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'test-output',
            reportFiles: 'ExtentReport.html',
            reportName: 'Extent Report'
        ])

        archiveArtifacts(
            artifacts: 'screenshots/**/*.png',
            allowEmptyArchive: true
        )

        archiveArtifacts(
            artifacts: 'test-output/**/*.html',
            allowEmptyArchive: true
        )

        archiveArtifacts(
            artifacts: 'C:/Users/Admin/exxcel1/TestData123.xlsx',
            allowEmptyArchive: true
        )
    }

    success {
        echo 'Build Successful - All validations passed.'
    }

    failure {
        echo 'Build Failed - Check Extent Report, Screenshots and Excel Logs.'
    }
}
```

}
