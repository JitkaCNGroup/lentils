# Admin002 - create, edit and delete cypher - happy path

*Description*
>This test case verifies that it is possible to create, edit and delete cypher in Admin


*Preconditions*
* Admin user is logged in
* Cypher list is opened

|Test Step|Expected Result|
|---------|---------------|
|Click on the Pridat button.|Form for creation of the cypher is opened.|
|Enter valid cypher name to the Jmeno field.|Cypher name is entered.|
|Enter valid number to the Poradi field.|Number is entered.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Enter a text to the Odpoved field.|Odpoved field is not empty.|
|Enter a text to the Falesna Odpoved field.|Falesna odpoved field is not empty.|
|Enter a text to the Bonusové informace field.|Bonusové informace field is not empty.|
|Enter a text to the Popis mista field.|Popis mista is not empty.|
|Save the cypher.|New row with the cypher information is created in the cypher list.<br>No error is displayed.|
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
