# Game003 - take hint

*Description*
>this testcase verifies that the team can take a hint for the cypher

*Preconditions*
* Language is set to Czech
* There is at least one cypher created (cypher1)
* There are 3 hints for the cypher1 created (hintA with value 5, hintB with value 10 and hintC with value20)
* The teamA is created
* The final place is created with:
    * finish time = current time + 1 hour
    * results time = current time + 2 hours
    * access time = 10 minutes
* The teamA already started game
* TeamA is logged in and cypher1 is in status Pending
* There are no hints taken for teamA and cypher1

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Verify that there is team name and team score displayed in the header.|The teamA name and score 0 is displayed in the header.|
|Verify that cypher1 is in status Pending.|There is blue circle displayed for the cypher1.|
|Verify that there is no signalization for taken hints of the cypher1.|There is no lamp icon displayed for the cypher1.|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that Vybrat nápovědu button is displayed.|Vybrat nápovědu button is displayed.|
|Click on the Vybrat nápovědu button.|The hint page is displayed.|
|Verify that there are no unlocked hints displayed.|There are no unlocked hints displayed.|
|Verify that there are 3 locked hints displayed.|There are 3 buttons displayed:<br>- Získat nápovědu (-5 bodů)<br>- Získat nápovědu (-10 bodů)<br>- Získat nápovědu (-20 bodů)|
|Click on Získat nápovědu (-5 bodů).|The confirmation dialog is displayed.|
|Decline the confirmation dialog.|The hint page is displayed.<br>No hint is taken.|
|Click on Získat nápovědu (-5 bodů).|The confirmation dialog is displayed.|
|Confirm the dialog.|The dialog is closed.<br>The hintA is taken.<br>The Unlocked hints shows 1 hint taken with the hint text.|
|Go to cypher detail page.<br>Verify that the taken hint with the hint text is displayed under the map.|The hintA text is present.|
|Click on the Vybrat nápovědu button.|The hint page is displayed.|
|Verify that there is unlocked hintA displayed.|There is hintA text displayed.|
|Verify that there are 2 locked hints displayed.|There are 2 buttons displayed:<br>- Získat nápovědu (-10 bodů)<br>- Získat nápovědu (-20 bodů)|
|Navigate to the list of cyphers via the menu.|The list of cyphers is displayed.|
|Verify that there is 1 signalization for taken hints of the cypher1.|There is lamp icon displayed for the cypher1 with count 1.|
|Verify that the score is -5 in the header.|The score is -5 for teamA.|
|Take all remaining hints.|All hints are taken.|
|Navigate to the cypher1 detail.|There are 3 texts of the 3 taken hints - hintA, hintB and hintC.|
|Verify that the score is -35 in the header.|The score is -35 for teamA.|
|Click on the Vybrat nápovědu button.|The hint page is displayed.|
|Verify that there are 3 unlocked hints displayed.|There are hintA text, hintB text and hintC text displayed.|
|Verify that there are no locked hints displayed.|There are no buttons displayed for locked hints.|

*Postconditions*
* Logout the teamA