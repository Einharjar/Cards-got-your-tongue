# Chat-got-your-tongue
A WIP Online casino test project

## Requirements

1:DBderby (db-derby-10.14.1.0 is what I've personally used)

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

### [Use the frontend for the rest!](http://localhost:80/?#/login)


## Bugs / WIP

1: Doesn't reset after a game finishes.

2: No cookies / Crashes on reload without telling. If the user visits the pages without a signed session no replies will be given and the app appears unresponsive for the user.

3: High Technical Debt, particularly in Websocket and Holdem classes but also through out the front end.

4: Probably vulnerable to sql injections.

5: Does not handle player backing out of game properly.

6: Generally ugly.

7: Lobbies does not auto update.

8: Lack of Redux and general abuse of states in the frontend.
