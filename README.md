# Chat-got-your-tongue
A WIP Online casino test project

##Requirements
1:DBderby
2:Application Server
3:NPM

##Setup
1: Run dbDerby at the 50000 port "startNetworkServer.bat -p 50000" from db-derby/bin directory
2: Run wildfly by starting "standalone.bat" from the wildfly/bin directory (Or equivelent for other app-servers)
3: Build with maven using "mvn clean wildfly:deploy"
4: Run NPM with "npm run start" from the frontend directory.
5: Create a new user by either post request at {PLACEHOLDER} or by websocket at ws://localhost:8080/casino/websocket


WIP
WIP
WIP
WIP
WIP
WIP
WIP
WIP
WIP
WIP
WIP


{
"request" : "register",
"user": {
"username" : "John",
"password": "qwerty",
"bananas" : 0.0,
"userId" : 1688.0,
"details" :   {
"personId" : 1688.0,
"firstName" : "John",
"lastName" : "Smith"
}
} 
}

localhost:8080/casino/webservice/users/username/john
{
"userId":0,
"details":{
"personId":420,
"firstName":"john",
"lastName":"larsson",
"userEmail":""
},
"username":"john",
"password":"qwerty",
"bananas":500
}




{
"request" : "login",
"username" : "John",
"password": "qwerty" 
}

{
"request" : "login",
"username" : "alex",
"password" : "qwerty"
}


{
"request" : "string",
"string" : "newlobby"
}

{
"request" : "string",
"string" : "getlobbies"
}

{
"request" : "join",
"join" : 0.0
}

{
"request" : "string",
"string" : "thislobby"
}

{
"request" : "string",
"string" : "start"
}
