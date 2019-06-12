# Game004 - solve cypher

*Description*
>this testcase verifies that the team can solve the cypher with correct codeword

*Preconditions*
* There are at least two cyphers created
* The teamA is created
* The game is already started
* TeamA is logged in and cypher1 is in status Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Verify that there is team name and team score displayed in the header.|The teamA name and score 0 is displayed in the header.|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Enter incorrect codeword to the Reseni field and click on the Overit button.|There is a message displayed under the Reseni field that the codeword is not correct.|
|~~Enter trap codeword to the Reseni field and click on the Overit button.~~|~~There is a trap page displayed.<br>There is a button Zkusit znovu present.~~|
|~~Click on the button Zkusit znovu.~~|~~The detail of the cypher1 is displayed.~~|
|Enter correct codeword to the Reseni field and click on the Overit button.|There is a message displayed that cypher is solved.<br>There is a button Zobrazit dalsi sifru present.|
|Verify that Zobrazit bonusove informace button is displayed for solved cypher1.|Bonus information button is displayed for the teamA.|
|Click on the button Zobrazit bonusove informace.|The bonus information is displayed.|
|Click on the Zobrazit dalsi sifru button.|The detail of the cypher2 is displayed.|
|Navigate to the list of cyphers via the menu.|The list of cyphers is displayed.|
|Verify that cypher1 is in status Solved (green checkmark).|The cypher1 is solved.|
|Verify that cypher2 is in status Pending.|There is blue circle displayed for the cypher2.|
|Verify that the score is 10 in the header.|The score is 10 for teamA.|

*Postconditions*
* Logout the teamA