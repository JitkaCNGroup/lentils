# Admin010 - create hint - field validations

*Description*
>This test case verifies the field validations of the hint form


*Preconditions*
* Admin user is logged in
* There is at least one cypher created
* The cypher list is opened

|Test Step|Expected Result|
|---------|---------------|
|Open the detail of the already created cypher:<br>Click on Akce at the cypher row in the list and select Detail.|The cypher detail is opened.|
|Click on the Pridat button.|Form for creation of the hint is opened.|
|Leave all fields empty.|All fields are empty.|
|Save the hint.|The hint is not saved.<br>There is an error displayed.|
|||
|Enter valid text to the Text field.|Text is entered.|
|Enter a negative number to the Hodnota field.|Negative number is entered.|
|Save the hint.|The hint is not saved.<br>There is an error displayed under the Hodnota field.|
|||
|Enter valid text to the Text field.|Text is entered.|
|Enter zero to the Hodnota field.|Zero is entered.|
|Save the hint.|The hint is not saved.<br>There is an error displayed under the Hodnota field.|

*Postconditions*
* Logout the admin user
