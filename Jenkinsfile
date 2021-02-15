pipeline{
    agent {label 'devops'}
    tools{
        maven 'maven-3.5.2'
        jdk   'jdk1.8'
    }
    stages{
        stage("Initialize"){
            steps{
                sh '''
                    echo "PATH=${PATH}"
                    echo "MAVEN_HOME=${MAVEN_HOME}"
                '''
            }
        }
        stage("Clone source code"){
            steps{
                git branch: 'master', url: 'git@github.com:wangwenjun/simple_application_acceptance.git'
            }
        }

        stage("Acceptance Test for Simple Application"){
            steps{
                sh 'mvn test "-Dtest=*Runner"'
            }
        }

        stage("Publish Function Testing Report"){
            steps{
                sh 'mvn cluecumber-report:reporting'
            }
        }
    }
}