#!/bin/bash
cd AMT-PROJECT-FOR-TESTING/topology-amt
echo "Warming up your docker =)....................................................."
docker-compose down
docker-compose up --build -d
cd ../../AMT-Project
echo "waiting for everybody before start testing ................................."
Sleep 25
mvn clean
mvn install 

