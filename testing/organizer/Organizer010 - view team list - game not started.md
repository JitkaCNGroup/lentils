# Organizer010 - view team list - game not started

*Description*
>this testcase verifies that the list of the teams is displayed even if the game is not started yet

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There are 3 teams created (teamA, teamB, teamC)

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled tymu menu.|The teams list is displayed.|
|Verify that all three teams are displayed.|The teams are displayed with the Team name and Status.|
|Click on the teamA.|The teamA detail is displayed.<br>List of the three cyphers is displayed.|

*Postconditions*
* Logout the organizer user