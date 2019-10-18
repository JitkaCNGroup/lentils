# Game004 - solve cypher

*Description*
>this testcase verifies that the team can solve the cypher with correct codeword

*Preconditions*
* Language is set to Czech
* There are at least two cyphers created with codeword and trap word and bonus information
* The teamA is created
* The final place is created with:
    * finish time = current time + 1 hour
    * results time = current time + 2 hours
    * access time = 10 minutes
* The game is already started
* TeamA is logged in and cypher1 is in status Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Verify that there is team name and team score displayed in the header.|The teamA name and score 0 is displayed in the header.|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Enter incorrect codeword to the Řešení field and click on the Ověřit button.|There is a message displayed under the Řešení field that the codeword is not correct.|
|Enter trap codeword to the Řešení field and click on the Ověřit button.|There is a trap page displayed.<br>There is a button Zkusit znovu present.|
|Click on the Zkusit znovu button.|The detail of the cypher1 is displayed.|
|Enter correct codeword to the Řešení field and click on the Ověřit button.|There is a message displayed that cypher is solved.<br>There is a button Go to the next cypher present.|
|Verify that bonus information text is displayed for solved cypher1.|Bonus information text is displayed for the teamA.|
|Click on the Zobrazit další šifru button.|The detail of the cypher2 is displayed.|
|Navigate to the list of cyphers via the menu.|The list of cyphers is displayed.|
|Verify that cypher1 is in status Solved (green checkmark).|The cypher1 is solved.|
|Verify that cypher2 is in status Pending.|There is blue circle displayed for the cypher2.|
|Verify that the score is 10 in the header.|The score is 10 for teamA.|

*Postconditions*
* Logout the teamA