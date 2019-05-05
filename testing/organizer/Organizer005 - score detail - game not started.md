# Organizer005 - score detail - game not started

*Description*
>this testcase verifies that the cyphers are displayed at score detail even if the game is not started yet

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There are 3 teams created (teamA, teamB, teamC)

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Score menu.|The teams table is displayed.|
|Open score detail of the first team.|The cyphers table is displayed.|
|Verify that all 3 cyphers are displayed.|All cyphers are displayed.|
|Verify that the score is 0 for every cypher.|The score is 0 for every cypher.|

*Postconditions*
* Logout the organizer user