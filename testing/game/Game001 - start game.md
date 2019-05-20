# Game001 - start game

*Description*
>this testcase verifies that the team can start the game correctly

*Preconditions*
* There is at least one cypher created (cypher1)
* There is at least one hint for the cypher1 created
* The teamA is created
* The login page is opened

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Login as teamA with correct PIN.|The Zahajit hru button is displayed. Verify that there is team name displayed in the header|
|Click on the Zahajit hru button.|The game is started.|
|Verify that there is team name and team score displayed in the header.|The teamA name and score 0 is displayed in the header.|
|Verify that there is cypher1 name displayed.|The cypher1 name is displayed.|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that text with description is displayed.|Cypher1 description is displayed.|
|Verify that field for codeword with confirmation button are present.|The field Reseni and button Overit are displayed.|
|Verify that map with the location is displayed.|The map with the location is displayed correctly.|
|Verify that Zobrazit na mape button is present.|The button is displayed.|
|Verify that Vybrat napovedu button is displayed.|Vybrat napovedu button is displayed.|
|Verify that Vzdat sifru button is present.|The Vzdat sifru button is displayed.|

*Postconditions*
* Logout the teamA