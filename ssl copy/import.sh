#!/bin/sh

sudo keytool -delete -alias payara -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/security/cacerts

sudo keytool -trustcacerts -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/security/cacerts -storepass changeit -alias payara -import -file ~/Documents/HEIG-VD/Third\ Year/First\ Semester/AMT/AMT-Project/ssl/payara-self-signed-certificate.crt