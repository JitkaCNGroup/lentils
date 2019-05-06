# Game009 - view finalplace - time

*Description*
>this testcase verifies that the finalplace can be displayed when the end game time is < 1 hour

*Preconditions*
* There are two cyphers created (cypher1 and cypher2)
* The teamA is created
* The final place is created with the time = current time + 20 minutes
* The game is already started
* TeamA is logged in and cypher1 is in satus Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the footer.|The page with final place map with location and details is displayed.|
|Go back.<br>Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the footer.|The page with final place map with location and details is displayed.|
|Go back.<br>Click on the Vzdat sifru button.|Confirmation dialog is opened.|
|Confirm the dialog.|There is an information that team skipped the cypher1.<br>There is a button Zobrazit dalsi sifru present.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the footer.|The page with final place map with location and details is displayed.|
|Go back.<br>Click on the cypher2.|The detail of the cypher2 is displayed.|
|Enter correct codeword to the Reseni field and click on the Overit button.|There is a message displayed that cypher2 is solved.<br>There is a button Zobrazit misto vyhlaseni present.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the button Zobrazit misto vyhlaseni.|The page with final place map with location and details is displayed.|

*Postconditions*
* Logout the teamA