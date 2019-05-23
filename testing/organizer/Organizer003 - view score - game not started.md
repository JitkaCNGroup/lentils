# Organizer003 - view score - game not started

*Description*
>this testcase verifies that the teams score is displayed even if the game is not started yet

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There are 3 teams created (teamA, teamB, teamC)

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Score menu.|The teams list is displayed.|
|Verify that all three teams are displayed.|The teams are displayed with the columns:<br>- Poradi<br>- Nazev<br>- Body|
|Verify that the body is 0 for every team.|The body is 0 for every team.|

*Postconditions*
* Logout the organizer user