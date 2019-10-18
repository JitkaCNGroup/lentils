# Game001 - start game

*Description*
>this testcase verifies that the team can start the game correctly

*Preconditions*
* Language is set to Czech
* There is at least one cypher created (cypher1)
* There is at least one hint for the cypher1 created
* The teamA is created
* The final place is created with:
    * finish time = current time + 1 hour
    * results time = current time + 2 hours
    * access time = 10 minutes
* The login page is opened

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Login as teamA with correct PIN.|The Zahájit hru button is displayed. Verify that there is team name displayed in the header|
|Click on the Zahájit hru button.|The game is started.|
|Verify that there is team name and team score displayed in the header.|The teamA name and score 0 is displayed in the header.|
|Verify that there is cypher1 name displayed.|The cypher1 name is displayed.|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that text with description is displayed.|Cypher1 description is displayed.|
|Verify that field for codeword with confirmation button are present.|The field Řešení and button Ověřit are displayed.|
|Verify that map with the location is displayed.|The map with the location is displayed correctly.|
|Verify that Zobrazit na mapě button is present.|The button is displayed.|
|Verify that Vybrat nápovědu button is displayed.|Vybrat nápovědu button is displayed.|
|Verify that Vzdát šifru button is present.|Vzdát šifru button is displayed.|

*Postconditions*
* Logout the teamA