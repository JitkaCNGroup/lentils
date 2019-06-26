# Admin012 - ~~create and~~ edit contact - happy path

*Description*
>This test case verifies that it is possible to ~~create and~~ edit the contact in Admin


*Preconditions*
* Admin user is logged in
* Contact is opened

|Test Step|Expected Result|
|---------|---------------|
|Enter valid text to the Jmeno field.|Jmeno field is not empty.|
|Enter valid number to the Telefonni cislo field.|Telefonni cislo field is not empty.|
|Enter valid email to the E-mail field.|E-mail field is not empty.|
|Enter valid text to the Web field.|Web field is not empty.|
|Enter valid text to the Facebook udalost field.|Facebook udalost field is not empty.|
|Save the contact.|The contact is saved correctly.<br>No error is displayed.|
|||
|~~Edit all fields of the contact.~~|~~All fields are edited.~~|
|~~Save the contact.~~|~~The contact is saved correctly.~~<br>~~The fields are updated.~~<br>~~No error is displayed.~~|

*Postconditions*
* Logout the admin user
