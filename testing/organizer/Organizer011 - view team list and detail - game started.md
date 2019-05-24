# Organizer011 - view team list and detail - game started

*Description*
>this testcase verifies that the list of the teams is displayed after the game is started

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There is at least one Hint created for cypher1
* There are 3 teams created (teamA, teamB, teamC)
* teamA and teamB already started the game (cypher1 is in status Pending for that teams)

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled tymu menu.|The teams list is displayed.|
|Verify that all three teams are displayed.|The teams are displayed with the name and Status of the team.|
|Open detail of the first teamA.|The detail of the first teamA is opened.<br>All three cyphers are displayed.|
|Verify that the status of the cypher1 is Pending.|The cypher1 has status Pending.|
|Verify that the status of the cypher2 is Locked.|The cypher2 has status Locked.|
|Verify that the status of the cypher3 is Locked.|The cypher3 has status Locked.|
|Click on the cypher1 row.|The cypher1 progress is displayed with all 3 teams and its statuses.|

*Postconditions*
* Logout the organizer user