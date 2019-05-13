# Chat-got-your-tongue
A WIP Online casino test project

## Requirements

1:DBderby

2:Application Server

3:NPM


## Setup
1: Run dbDerby at the 50000 port "startNetworkServer.bat -p 50000" from db-derby/bin directory

2: Run wildfly by starting "standalone.bat" from the wildfly/bin directory (Or equivelent for other app-servers)

3: Build with maven using "mvn clean wildfly:deploy"

4: Run NPM with "npm run start" from the "frontside" directory.

5a: Create a new user by post request at localhost:8080/casino/webservice/users/create with a body like

{
	"userId": 5,
	"details": {
		"personId": 5,
		"firstName": "Clint",
		"lastName": "Eastwood",
		"userEmail": "example@live.se"
	},
	"username": "Einar",
	"password": "test!password",
	"bananas": 500
}

5b: Create a new user by websocket at ws://localhost:8080/casino/websocket and a body like

{
"request" : "register",
"username" : "Einar",
"password" : "test!password"
}

## Game time!!!

### Use the frontend for the rest!
