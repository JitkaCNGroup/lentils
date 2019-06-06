# Game002 - skip cypher

*Description*
>this testcase verifies that the team can skip the cypher

*Preconditions*
* There are at least two cyphers created (cypher1 and cypher2)
* The teamA is created
* The game is already started
* TeamA is logged in and cypher1 is in satus Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Verify that there is team name and team score displayed in the header.|The teamA name and score 0 is displayed in the header.|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that Vzdat sifru button is present.|The Vzdat sifru button is displayed.|
|Click on the Vzdat sifru button.|Confirmation dialog is opened.|
|Decline the confirmation dialog.|The dialog is closed.<br>The cypher1 detail is displayed.|
|Click on the Vzdat sifru button.|Confirmation dialog is opened.|
|Confirm the dialog.|There is an information that team skipped the cypher1.<br>There is a button Zobrazit dalsi sifru present.|
|Click on the Zobrazit dalsi sifru button.|The detail of the cypher2 is displayed.|
|Navigate to the list of cyphers via the menu.|The list of cyphers is displayed.|
|Verify that cypher1 is in status Skipped (red circle with cross).|The cypher1 is skipped.|
|Verify that cypher2 is in status Pending.|There is blue circle displayed for the cypher2.|

*Postconditions*
* Logout the teamA