# Chat-got-your-tongue
Online casino test project

1:Run dbDerby at the 50000 port "startNetworkServer.bat -p 50000" from db-derby/bin directory
2:Run wildfly with "standalone.bat" from the wildfly/bin directory
To build with maven run a "clean wildfly:deploy"

There are several test classes which have their own main method you could test such as the game class for the Holdem test and
testHarness for the database test.

There's also two urls hosted via servlets;
http://localhost:8080/casino/login
http://localhost:8080/casino/holdem




{
"request" : "register",
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