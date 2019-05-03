# Admin001 - Create, edit and delete team - happy path

*Description*
>This test case verifies that it is possible to create, edit and delete team in Admin


*Preconditions*
* Admin user is logged in
* Team list is opened

|Test Step|Expected Result|
|---------|---------------|
|Enter valid team name to the Jmeno field.|Cypher name is entered.|
|Enter valid number to the Poradi field.|Number is entered.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Enter a text to the Odpoved field.|Odpoved field is not empty.|
|Enter a text to the Falesna Odpoved field.|Falesna odpoved field is not empty.|
|Enter a text to the Bonusové informace field.|Bonusové informace field is not empty.|
|Enter a text to the Popis mista field.|Popis mista is not empty.|
|Save the cypher.|The cypher is saved correctly.<br>No error is displayed.<br>New row is displayed in the cypher list.|
|Validate that the new row contains all correct information.|The row contains:<br>- ID<br>- Team name<br>- User name<br>- Number of players<br>- PIN|
|||
|Open edit mode of the already created team:<br>Click on Akce at the team row in the list and select Upravit.|The team name and number of players is filled into the form correctly.|
|Edit the team name in the Jmeno field.|The team name is edited.|
|Edit the number of players in the Pocet clenu field.|The number of players is edited.|
|Save the team.|The team is saved correctly.<br>No error is displayed.<br>The team row in the list is updated.|
|||
|Click on the Akce at the team row in the list and select Smazat.|The team row is removed from the teams list.|

*Postconditions*
* Logout the admin user
