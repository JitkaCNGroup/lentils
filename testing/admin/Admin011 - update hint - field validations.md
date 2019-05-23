# Admin011 - update hint - field validations

*Description*
>This test case verifies the field validations of the hint form


*Preconditions*
* Admin user is logged in
* There is at least one cypher created
* There is at least one hint created of the cypher
* The cypher list is opened

|Test Step|Expected Result|
|---------|---------------|
|Open the detail of the already created cypher:<br>Click on Akce at the cypher row in the list and select Detail.|The cypher detail is opened.|
|Open edit mode of the already created hint:<br>Click on Akce at the hint row in the list and select Upravit.|The hint information are pre-filled into the hint form correctly.|
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
