# Organizer006 - view cypher list and detail - game is started

*Description*
>this testcase verifies that the list of the cyphers is displayed after the game is started

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There is at least one Hint created for cypher1
* There are 3 teams created (teamA, teamB, teamC)
* teamA and teamB already started the game (cypher1 is in status Pending for that teams)

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled sifer menu.|The cyphers table is displayed.|
|Verify that all three cyphers are displayed.|The cyphers are displayed with the Poradi and Jmeno sifry.|
|Open detail of the first cypher.|The detail of the first cypher is opened.<br>All three teams are displayed.|
|Verify that there are buttons for changing the cyphers status for 2 teams.|Buttons are visible for 2 teams.|
|Verify that there is a button for taking Hint for 2 teams.|Hint button is visible for 2 teams.|
|Verify that there is no button for changing the cyphers status for 1 team.|There is no button visible for 1 team.|
|Verify that there is no button for taking Hint for 1 team.|There is no Hint button visible for 1 team.|

*Postconditions*
* Logout the organizer user