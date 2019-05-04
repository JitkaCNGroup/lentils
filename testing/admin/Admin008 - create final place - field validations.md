# Admin008 - create final place - field validations

*Description*
>This test case verifies the field validations of the final place form


*Preconditions*
* Admin user is logged in
* Final place is opened

|Test Step|Expected Result|
|---------|---------------|
|Leave all fields empty.|All fields are empty.|
|Save the final place.|The final place is not saved.<br>There are errors displayed under the Jmeno a popis and Souradnice fields.|
|||
|Enter a string with > 1000 characters to the Jmeno a popis field.|Jmeno a popis field is not empty.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Save the final place.|The final place is not saved.<br>There is an error displayed under the Jmeno a popis field.|

*Postconditions*
* Logout the admin user
