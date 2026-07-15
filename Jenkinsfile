pipeline {
   agent {label "master"}
   environment{
        PATH = "/urs/local/bin:urs/bin:${PATH}"
   }

   stages{
     stage("DEPLOY"){
       steps{
         sh """
           docker-compose -f docker-compose.yml dow --remove-orphans
           docker-compose -f docker-compose.yml up -d --build --force-recreate
         """
       }

     }
   }

}