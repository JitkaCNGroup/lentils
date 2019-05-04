# Admin006 - create cypher - field validations

*Description*
>This test case verifies the field validations of the cypher form


*Preconditions*
* Admin user is logged in
* Cypher list is opened

|Test Step|Expected Result|
|---------|---------------|
|Click on the Pridat button.|Form for creation of the cypher is opened.|
|Leave all field empty.|All fields are empty.|
|Save the cypher.|Cypher is not saved.<br>There are errors displayed under the Poradi and Souradnice fields.|
|||
|Enter a negative number to the Poradi field.|The Poradi field is not empty.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Save the cypher.|Cypher is not saved.<br>There is error displayed under the Poradi field.|
|||
|Enter zero to the Poradi field.|The Poradi field is not empty.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Save the cypher.|Cypher is not saved.<br>There is error displayed under the Poradi field.|
|||
|Enter a valid number to the Poradi field.|THe Poradi field is not empty.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Enter a string with > 1000 characters to the Bonusove informace field.|Bonusove informace field is not empty.|
|Save the cypher.|Cypher is not saved.<br>There is error displayed under the Bonusove informace field.|
|||
|Enter a valid number to the Poradi field.|THe Poradi field is not empty.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Enter a string with > 1000 characters to the Popis mista field.|Popis mista field is not empty.|
|Save the cypher.|Cypher is not saved.<br>There is error displayed under the Popis mista field.|

*Postconditions*
* Logout the admin user
