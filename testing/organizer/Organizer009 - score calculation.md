# Organizer009 - score calculation

*Description*
>this testcase verifies that the score for team is calculated correctly

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1 with value 10, cypher2 with value 10, cypher3 with value 10)
* The cypher1 has 2 hints created (hint1 with value 5 and hint2 with value 10)
* There are 2 teams created(teamA and teamB)
* teamA has started the game
* teamA has NO hint taken for cypher1

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled sifer.|The list of cyphers is displayed.|
|Open cypher1.|The detail of the cypher1 is displayed.|
|Click on the Hinty button of team A.|The Hint list for teamA and cypher1 is displayed.|
|Click on the Vzit hint (-5b.) button, enter valid PIN and confirm the dialog.|The hint1 is taken for teamA.|
|Navigate to the Score.|The list of the team is displayed.|
|Validate that teamA has score -5.|TeamA has score -5.|
|Open the score detail of teamA.|The score detail is opened.|
|Validate the score is correct.|The score for cypher1 and teamA is:<br>Body za sifru = 0<br>Celkem pouzitych napoved = 1<br>Zaporne body za napovedy=5<br>Celkove skove = -5|
|||
|Navigate to the Prehled sifer.|The list of cyphers is displayed.|
|Open cypher1.|The detail of the cypher1 is displayed.|
|Click on the Solve button of the teamA.|The cypher1 for teamA is solved.|
|Navigate to the Score.|The list of the team is displayed.|
|Validate that teamA has score 5.|TeamA has score 5.|
|Open the score detail of teamA.|The score detail is opened.|
|Validate the score of cypher1 is correct.|The score for cypher1 and teamA is:<br>Body za sifru = 10<br>Celkem pouzitych napoved = 1<br>Zaporne body za napovedy=5<br>Celkove skove = 5|
|||
|Navigate to the Prehled sifer.|The list of cyphers is displayed.|
|Open cypher2.|The detail of the cypher2 is displayed.|
|Click on the Skip button of the teamA.|The cypher2 for teamA is skipped.|
|Navigate to the Score.|The list of the team is displayed.|
|Validate that teamA has score 5.|TeamA has score 5.|
|Open the score detail of teamA.|The score detail is opened.|
|Validate the score of cypher1 is correct.|The score for cypher1 and teamA is:<br>Body za sifru = 10<br>Celkem pouzitych napoved = 1<br>Zaporne body za napovedy= 5<br>Celkove skove = 5|
|Validate the score of cypher2 is correct.|The score for cypher2 and teamA is:<br>Body za sifru = 0<br>Celkem pouzitych napoved = 0<br>Zaporne body za napovedy= 0<br>Celkove skove = 0|
|||
|Navigate to the Prehled sifer.|The list of cyphers is displayed.|
|Open cypher3.|The detail of the cypher3 is displayed.|
|Click on the Solve button of the teamA.|The cypher3 for teamA is solved.|
|Validate that teamA has score 15.|TeamA has score 15.|
|Validate the score of cypher1 is correct.|The score for cypher1 and teamA is:<br>Body za sifru = 10<br>
Celkem pouzitych napoved = 1<br>Zaporne body za napovedy= 5<br>Celkove skove = 5|
|Validate the score of cypher2 is correct.|The score for cypher2 and teamA is:<br>Body za sifru = 0<br>
Celkem pouzitych napoved = 0<br>Zaporne body za napovedy= 0<br>Celkove skove = 0|
|Validate the score of cypher3 is correct.|The score for cypher3 and teamA is:<br>Body za sifru = 10<br>
Celkem pouzitych napoved = 0<br>Zaporne body za napovedy= 0<br>Celkove skove = 10|
|||
|Navigate to the Prehled sifer.|The list of cyphers is displayed.|
|Open cypher1.|The detail of the cypher1 is displayed.|
|Click on the Restart and then Skip button of the teamA.|The cypher1 for teamA is skipped.|
|Validate that teamA has score 15.|TeamA has score 15.|
|Validate the score of cypher1 is correct.|The score for cypher1 and teamA is:<br>Body za sifru = 0<br>
Celkem pouzitych napoved = 1<br>Zaporne body za napovedy= 5<br>Celkove skove = -5|
|Validate the score of cypher2 is correct.|The score for cypher2 and teamA is:<br>Body za sifru = 0<br>
Celkem pouzitych napoved = 0<br>Zaporne body za napovedy= 0<br>Celkove skove = 0|
|Validate the score of cypher3 is correct.|The score for cypher3 and teamA is:<br>Body za sifru = 10<br>
Celkem pouzitych napoved = 0<br>Zaporne body za napovedy= 0<br>Celkove skove = 10|

*Postconditions*
* Logout the organizer user