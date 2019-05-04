# Organizer004 - cypher detail - game not started

*Description*
>this testcase verifies that the list of the teams is displayed at cyphers detail even if the game is not started yet

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There are 3 teams created (teamA, teamB, teamC)

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled sifer menu.|The cyphers table is displayed.|
|Open first cypher.|The detail of the first cypher is displayed.|
|Verify that all 3 teams are displayed.|All teams are displayed.|
|Verify that every team has all information displayed.|There is ID, Clenu and PIN displayed for every team.|
|Verify that it is not possible to change the cyphers status for any team.|There are no buttons to change the status.|

*Postconditions*
* Logout the organizer user