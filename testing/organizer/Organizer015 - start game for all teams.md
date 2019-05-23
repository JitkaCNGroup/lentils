# Organizer015 - start game for all teams

*Description*
>this testcase verifies that the organizer can start the game for all teams

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There are 3 teams created (teamA, teamB, teamC)
* no team started the game

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled tymu menu.|The teams table is displayed.|
|Verify that there is a button for Zahajit hru vsem tymum present.|The button is present.|
|Click on the button.|There is an info message displayed that Vsechny tymy uz zahajily hru.|

*Postconditions*
* Logout the organizer user