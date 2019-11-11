# Teaching-HEIGVD-AMT-2019-Project-One

#### Authors: Budry Nohan, Moreno Andres

## Objectives

The main objective of this project is to apply the patterns and techniques presented during the lectures, and to create a simple multi-tiered application in Java EE.

#Instructions:

### Testing:

In orders to start the test, you only need to execute the script **StartTesting.sh** by introducing the following command inside the repertory :

```
./StartTesting.sh
```



this will build the docker compose and execute all the tests.



In order to access the `commercial` infrastructure, the **StartComertial.sh** scrip needs to be executed. (in this scenario, non of the test will be executed and the war will be deployed inside the docker). The command to execute:

```
./StartComercial.sh
```



Once the application has been deployed, the login/registration page will be at:


```
http://localhost:8080/AMT-Project/login
```



|   TYPE   |  USERNAME    | PASSWORD     |
| ---- | ---- | ---- |
|    ADMIN  |    jack  |    123  |
|    REGULAR  |   steve   |    123  |



**Different routes:**

| Route         | Functionality |
| ------------- | ------------ |
| /login        | Login        |
| /registration | Registration |
| /rights 			| Managing the Exploitation Rights |
| /fields			  | Managing fields |
| /logout			  | Logout account |
| /profile      | User main page |

| PHPMyAdmin | USERNAME | PASSWORD   |
| ---------- | -------- | ---------- |
|            | root     | adminadmin |












More info regarding the project, inside the document  folder.

