# Game008 - view finalplace - cyphers skipped

*Description*
>this testcase verifies that the finalplace can be displayed after all cyphers are skipped

*Preconditions*
* Language is set to Czech
* There are two cyphers created (cypher1 and cypher2)
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
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Click on the Vzdát šifru button.|Confirmation dialog is opened.|
|Confirm the dialog.|There is an information that team skipped the cypher1.<br>There is a button Zobrazit další šifru present.|
|Click on the Zobrazit další šifru button.|The detail of the cypher2 is displayed.|
|Click on the Vzdát šifru button.|Confirmation dialog is opened.|
|Confirm the dialog.|There is an information that team skipped the cypher2.<br>There is a button Vyhlášení výsledků present.|
|Click on the Vyhlášení výsledků button.|The page with final place map with location and details is displayed.|
|Go back to the cypher list.|There are red circles with x inside displayed for all cyphers.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the footer.|The page with final place map with location and details is displayed.|

*Postconditions*
* Logout the teamA