# events-project
Continuos Integration -> https://app.circleci.com/pipelines/github/edpichler/events-project

It uses springboot, kotlin, kubernetes (swarm) with zero downtime automatic deployments, ngnix and LetsEncrypt for the certificate. 

# Running locally
 - ``./gradlew bootRun``
 - go to http://localhost:8080/
