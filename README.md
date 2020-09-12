# events-project
Continuos Integration -> https://app.circleci.com/pipelines/github/edpichler/events-project

It uses spring boot, kotlin, kubernetes (swarm) with zero downtime automatic deployments, ngnix and LetsEncrypt for the certificate. 
See live in https://hedvig.pichler.network


# Running locally
 - ``./gradlew bootRun``
 - go to http://localhost:8080/
