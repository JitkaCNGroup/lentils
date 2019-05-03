Admin002 - Create, edit and delete cypher - happy path

*Description*
>This test case verifies that it is possible to create, edit and delete cypher in Admin


*Preconditions*
* Admin user is logged in
* Cypher list is opened

|Test Step|Expected Result|
|---------|---------------|
|Click on the Pridat button.|Form for creation of the cypher is opened.|
|Enter valid cypher name to the Jmeno field|Team name is entered.|
|Enter valid number to the Pocet clenu field.|Number of players is entered.|
|Save the team.|The team is saved correctly.<br>No error is displayed.<br>New row is displayed in the teams list.|
|Validate that the new row contains all correct information.|The row contains:<br>- ID<br>- jmeno<br>- Poradi<br>- Souradnice<br>- Popis mista<br>- Opdoved<br>- Falesna odpoved<br>- Bonus|
|||
|Click on the Akce and select Detail.|The cypher detail is displayed.|
|Validate that the cypher row contains all correct information.|The row contains:<br>- ID<br>- jmeno<br>- Poradi<br>- Souradnice<br>- Popis mista<br>- Opdoved<br>- Falesna odpoved<br>- Bonus|
|||
|Open edit mode of the already created cypher:<br>Click on Akce at the cypher row in the list and select Upravit.|The cypher information are pre-filled into the cypher form correctly.|
|Edit all fields.|All fields edited.|
|Save the cypher.|The cypher is saved correctly.<br>No error is displayed.<br>The cypher row in the list is updated.|
|||
|Click on the Akce at the cypher row in the list and select Smazat.|The cypher row is removed from the cypher list.|

*Postconditions*
* Logout the admin user
