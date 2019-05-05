# Organizer007 - change cypher status

*Description*
>this testcase verifies that it is possible to change the cypher status of the team which has started the game

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There are 2 teams created(teamA and teamB)
* teamA has started the game

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled sifer menu.|The cyphers table is displayed.|
|Open first cypher.|The detail of the first cypher is displayed.|
|Verify that the buttons for changing the cypher status are present for teamA.|There are buttons for teamA:<br>- Solve<br>- Skip<br>- Lock|
|Click on the Solve button.|The cypher status is changed.<br>No error is displayed.<br>These buttons are visible:<br>- Restart<br>- Lock|
|Click on the Restart button.|The cypher status is changed.<br>No error is displayed.<br>These buttons are visible:<br>- Solve<br>- Skip<br>- Lock|
|Click on the Skip button.|The cypher status is changed.<br>No error is displayed.<br>These buttons are visible:<br>- Restart<br>- Lock|
|Click on the Lock button.|The cypher status is changed.<br>No error is displayed.<br>This button is visible:<br>- Start|

*Postconditions*
* Logout the organizer user