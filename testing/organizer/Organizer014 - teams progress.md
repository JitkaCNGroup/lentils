# Organizer014 - teams progress

*Description*
>this testcase verifies that the progress of the teams is displayed at the team overview

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* There are 3 teams created (teamA, teamB, teamC)
* teamA skipped all cyphers (or resolved all cyphers)
* teamB has cypher1 in status Pending
* teamC has cypher1 in status Solved and cypher2 in status Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled tymu menu.|The teams table is displayed.|
|Verify that the info about the team progress is visible.|There is information about the team progress displayed:<br>- Tymy se nachazeji na stanovistich 1 - 2|
|Verify that there is info about finished teams.|The info is displayed:<br>- Pocet tymu, ktere maji dolusteno: 1|

*Postconditions*
* Logout the organizer user