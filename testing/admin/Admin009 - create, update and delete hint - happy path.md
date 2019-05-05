# Admin009 - create, update and delete hint - happy path

*Description*
>This test case verifies that it is possible to create, edit and delete hint of the cypher in Admin


*Preconditions*
* Admin user is logged in
* There is at least one cypher created
* The cypher list is opened

|Test Step|Expected Result|
|---------|---------------|
|Open the detail of the already created cypher:<br>Click on Akce at the cypher row in the list and select Detail.|The cypher detail is opened.|
|Click on the Pridat button.|Form for creation of the hint is opened.|
|Enter valid text to the Text field.|Text is entered.|
|Enter valid number to the Hodnota field.|Number is entered.|
|Save the hint.|New row with the hint information is created in the hint list at the cypher detail.<br>No error is displayed.|
|Validate that the new row contains all correct information.|The row contains:<br>- ID<br>- Text<br>- Hodnota|
|||
|Open edit mode of the already created hint:<br>Click on Akce at the hint row in the list and select Upravit.|The hint information are pre-filled into the hint form correctly.|
|Edit all fields.|All fields are edited.|
|Save the hint.|The hint is saved correctly.<br>No error is displayed.<br>The hint row in the list is updated.|
|||
|Click on the Akce at the hint row in the list and select Smazat.|The hint row is removed from the hint list.|

*Postconditions*
* Logout the admin user
