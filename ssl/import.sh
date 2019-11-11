#!/bin/sh

sudo keytool -trustcacerts -keystore /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/security/cacerts -storepass changeit -alias payara -import -file /Users/nohanbudry/school/amt/project/ssl/payara-self-signed-certificate.crt