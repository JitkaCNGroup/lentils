# Admin013 - create contact - field validations

*Description*
>This test case verifies the field validations of the contact form


*Preconditions*
* Admin user is logged in
* Contact is opened

|Test Step|Expected Result|
|---------|---------------|
|Leave all fields empty.|All fields are empty.|
|Save the contact.|The final place is not saved.<br>There is an error displayed.|

*Postconditions*
* Logout the admin user
