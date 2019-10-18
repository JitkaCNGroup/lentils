# Game009 - view finalplace - time

*Description*
>this testcase verifies that the finalplace can be displayed when the time is within the access time of the finalplace

*Preconditions*
* Language is set to Czech
* There are two cyphers created (cypher1 and cypher2)
* The teamA is created
* The final place is created with:
    * finish time = current time + 0.5 hour
    * results time = current time + 1 hours
    * access time = 30 minutes
* The game is already started
* TeamA is logged in and cypher1 is in status Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the footer.|The page with final place map with location and details is displayed.|
|Go back.<br>Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that the Vyhlášení výsledků button is present.|The Vyhlášení výsledků button is displayed.|
|Click on the Vyhlášení výsledků.|The page with final place map with location and details is displayed.|
|Go back to cypher1 detail.<br>Click on the Vzdát šifru button.|Confirmation dialog is opened.|
|Confirm the dialog.|There is an information that team skipped the cypher1.<br>There is a button Zobrazit další šifru present.<br>There is a button Vyhlášení výsledků present.|
|Go back to the cypher list.|There is blue circle displayed for the cypher2.|
|Verify that the footer with finalplace button and time is displayed.|The footer is displayed and contains finalplace button and time.|
|Click on the cypher2.|The detail of the cypher2 is displayed.|
|Verify that the Vyhlášení výsledků button is present.|The Vyhlášení výsledků button is displayed.|
|Click on the Vyhlášení výsledků button.|The page with final place map with location and details is displayed.|

*Postconditions*
* Logout the teamA