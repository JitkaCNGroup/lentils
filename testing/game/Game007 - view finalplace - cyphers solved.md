# Game007 - view finalplace - cyphers solved

*Description*
>this testcase verifies that the finalplace can be displayed after all cyphers are solved

*Preconditions*
* There are two cyphers created (cypher1 and cypher2)
* The teamA is created
* The final place is created with the time = current time + 2 hours
* The game is already started
* TeamA is logged in and cypher1 is in satus Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Enter correct codeword of cypher 1 to the Reseni field and click on the Overit button.|There is a message displayed that cypher1 is solved.<br>There is a button Zobrazit dalsi sifru present.|
|Click on the Zobrazit dalsi sifru button.|The detail of the cypher2 is displayed.|
|Enter correct codeword of cypher 2 to the Reseni field and click on the Overit button.|There is a message displayed that cypher2 is solved.<br>There is a button Zobrazit misto vyhlaseni present.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the footer.|The page with final place map with location and details is displayed.|

*Postconditions*
* Logout the teamA