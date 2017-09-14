podTemplate(
    inheritFrom: "maven", 
    label: "myJenkins", 
    cloud: "openshift", 
    volumes: [
        persistentVolumeClaim(claimName: "m2repo", mountPath: "/home/jenkins/.m2/")
    ]) {

    node("myJenkins") {

        @Library('github.com/redhat-helloworld-msa/jenkins-library@master') _
        
        stage ('SCM checkout'){
            echo 'Checking out git repository'
            checkout scm
        }
    
        stage ('Maven build'){
            echo 'Building project'
            sh "mvn package"
        }
    
        stage ('DEV - Image build'){
            echo 'Building docker image and deploying to Dev'
            buildApp('helloworld-namaste-dev', "namaste")
            echo "This is the build number: ${env.BUILD_NUMBER}"
        }
    
        stage ('Automated tests'){
            echo 'This stage simulates automated tests'
            sh "mvn -B -Dmaven.test.failure.ignore verify"
        }
    
        stage ('QA - Promote image'){
            echo 'Deploying to QA'
            promoteImage('helloworld-namaste-dev', 'helloworld-msa-qa', 'namaste', 'latest')
        }
    
        stage ('Wait for approval'){
            input 'Approve to production?'
        }
    
        stage ('PRD - Promote image'){
            echo 'Deploying to production'
            promoteImage('helloworld-namaste-qa', 'helloworld-msa', 'namaste', env.BUILD_NUMBER)
        }

        stage ('PRD - Canary Deploy'){
            echo 'Performing a canary deployment'
            canaryDeploy('helloworld-namaste', 'namaste', env.BUILD_NUMBER)
        }

    }
}