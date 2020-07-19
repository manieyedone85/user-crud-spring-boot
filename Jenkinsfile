pipeline {
        environment {
            // MAKE IMAGE NAME, CONTAINER NAME PREFIX, REPO NAME as same
            REPOSITORY_NAME = "user-crud"
            // THE BELOW SERVER URL IS API DEVELOPMENT INSTANCE
            DEPLOYMENT_SERVER_URL = "140.238.194.24"
            IMAGE_NAME = "user-crud-api-local"
            CONTAINER_NAME = "user-crud-api-local"
            CONTAINER_PORT = "8081"
            SPRING_PROFILES_ACTIVE = "development"
    }
    agent { label 'clkdev/jenkins-agent:1.0.2' }
    stages {
        stage('SCM Checkout'){
            steps{
                git credentialsId: env.CLK_GIT_CRED_ID, url: "${CLK_GIT_BASE_URL}/${env.REPOSITORY_NAME}.git"
            }
        }
        stage('Build'){
            steps{
                sh "mvn clean install"
                sh "docker rmi ${CLK_PRIVATE_DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest"
                sh "docker build -f docker/Dockerfile -t ${env.IMAGE_NAME}:latest ."
                sh "docker tag ${env.IMAGE_NAME}:latest ${CLK_PRIVATE_DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest"
                sh "docker push ${CLK_PRIVATE_DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest"
            }
        }

        stage('Deploy'){
             steps{
                sshagent(['access_to_oci_clk_api_development_instance']) {
                    sh "ssh -o StrictHostKeyChecking=no opc@${env.DEPLOYMENT_SERVER_URL} 'docker stop ${env.CONTAINER_NAME} && docker rm ${env.CONTAINER_NAME} && docker rmi ${CLK_PRIVATE_DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest && docker pull ${CLK_PRIVATE_DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest && docker run --name ${env.CONTAINER_NAME} -d -p ${env.CONTAINER_PORT}:8080 -e \'SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}\' ${CLK_PRIVATE_DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest'"
                }
            }
        }
    }

    post {
        success {
            script {
                wrap([$class: 'BuildUser']) {
                    slackSend(color: '#00FF00', message: "SUCCESS: ${env.JOB_NAME} job started by ${BUILD_USER} has been deployed successfully.\nBuild Info:\nNo: #${env.BUILD_NUMBER}:\nURL: ${env.BUILD_URL}" )
                }
            }
        }

        failure{
            script {
                wrap([$class: 'BuildUser']) {
                    slackSend(color: '#FF0000', message: "FAILURE: ${env.JOB_NAME} job started by ${BUILD_USER} was failed to deploy.\nBuild Info:\nNo: #${env.BUILD_NUMBER}:\nURL: ${env.BUILD_URL}")
                }
            }
        }
    }
}