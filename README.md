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

