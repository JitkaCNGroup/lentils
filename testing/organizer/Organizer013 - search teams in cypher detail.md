# Organizer013 - search team in cypher detail

*Description*
>this testcase verifies that it is possible to search for teams

*Preconditions*
* The organizer user is logged in
* There is at least one cypher created
* There are 3 teams created (teamA, teamB, teamC, ranger)

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Prehled sifer menu.|The cypher list is displayed.|
|Open detail of the cypher.|The list of teams is displayed.<br>Hledat tym field and Hledat button are present.|
|Leave the Hledat tym empty and click on Hledat button.|There is an error at the Hledat tym field displayed.|
|Enter "team" string to the Hledat tym field and click on Hledat button.|The list is filtered and these teams are displayed:<br>- teamA<br>- teamB<br>- teamC|
|Verify that the button Zobrazit vsechny tymy is present.|The button is present.|
|Click on the Zobrazit vsechny tymy button.|All teams are displayed.<br>The button Zobrazit vsechny tymy is not present anymore.|
|Enter "teamB" string to the Hledat tym field and click on Hledat button.|The list is filtered and this team is displayed:<br>- teamB|
|Enter "xyz" string to the Hledat tym field and click on Hledat button.|There is a message that Zadny tym nenalezen.|

*Postconditions*
* Logout the organizer user