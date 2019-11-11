#!/bin/bash
cd AMT-Project
mvn clean
mvn install -DskipTests
cd target
mv AMT-Project.war ../../AMT-PROJECT-Comercial/images/payara
cd ../../AMT-PROJECT-Comercial/topology-amt	   
docker-compose down
docker-compose up --build

