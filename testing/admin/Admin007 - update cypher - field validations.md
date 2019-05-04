# Admin007 - update cypher - field validations

*Description*
>This test case verifies the field validations of the cypher form


*Preconditions*
* Admin user is logged in
* Cypher list is opened
* There is at least one cypher created

|Test Step|Expected Result|
|---------|---------------|
|Open edit mode of the already created cypher:<br>Click on Akce at the cypher row in the list and select Upravit.|The cypher information are filled into the cypher form.|
|Delete all fields.|All fields are empty.|
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
