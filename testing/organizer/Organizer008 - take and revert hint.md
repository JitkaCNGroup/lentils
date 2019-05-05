# Organizer008 - take and revert hint

*Description*
>this testcase verifies that it is possible to take and revert hint

*Preconditions*
* The organizer user is logged in
* There are 3 cyphers created (cypher1, cypher2, cypher3)
* The cypher1 has 2 hints created (hint1 with value 5 and hint2 with value 10)
* There are 2 teams created(teamA and teamB)
* teamA has started the game

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled sifer menu.|The cyphers table is displayed.|
|Open first cypher.|The detail of the first cypher is displayed.|
|Verify that the button for take a hint present for teamA.|There is button for teamA:<br>- Hinty|
|Click on the Hinty button of team A.|The Hint list for teamA and cypher1 is displayed.|
|Verify that there are no unlocked hints.|There are no unlocked hints.|
|Verify that there are 2 locked hints.|There are two buttons:<br>- Vzit hint (-10b.)<br>- Vzit hint (-5b.)|
|||
|Click on the Vzit hint (-5b.) button|The confirmation dialog is open with the PIN field.|
|Decline the dialog.|The dialog is closed.<br>There are still 2 locked hints present.|
|||
|Click on the Vzit hint (-5b.) button|The confirmation dialog is open with the PIN field.|
|Enter incorrect PIN for the teamA and confirm the dialog.|There is an error displayed.<br>There are still 2 locked hints present.|
|||
|Click on the Vzit hint (-5b.) button.|The confirmation dialog is open with the PIN field.|
|Enter the correct PIN for the teamA and confirm the dialog.|The dialog is closed.<br>The hint text is displayed at the unlocked hints part.|
|Verify that there is one unlocked hint.|There is one unlocked hint.|
|Verify that there is one Odebrat napovedu button present.|There is one Odebrat napovedu button present at the unlocked hints part.|
|Verify that there is one locked hint.|There is one locked hint.<br>There is one button:<br>- Vzit hint (-10b.)|
|||
|Click on the Odebrat napovedu button.|The confirmation dialog is displayed.|
|Decline the dialog.|The dialog is closed.<br>There is still one locked and one unlocked hint.|
|||
|Click on the Odebrat napovedu button.|The confirmation dialog is displayed.|
|Confirm the dialog.|The dialog is closed.<br>The hint text is removed from the unlocked hints part.|
|Verify that there are no unlocked hints.|There are no unlocked hints.|
|Verify that there are 2 locked hints.|There are two buttons:<br>- Vzit hint (-10b.)<br>- Vzit hint (-5b.)|
|Take both hints.|Both hints are taken.|
|Verify that there are 2 unlocked hints.|There are 2 unlocked hints.<br>There are 2 buttons Odebrat napovedu.|
|Verify that there is no locked hints.|There is no button Vzit hint.|
|||
|Revert both hints.|Both hints are reverted.|
|Verify that there are no unlocked hints.|There are no unlocked hints.<br>There is no button Odebrat napovedu.|
|Verify that there are 2 locked hints.|There are two buttons:<br>- Vzit hint (-10b.)<br>- Vzit hint (-5b.)|

*Postconditions*
* Logout the organizer user