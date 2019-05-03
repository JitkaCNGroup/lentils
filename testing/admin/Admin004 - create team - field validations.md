# Admin004 - create team - field validations

*Description*
>This test case verifies the field validations of the team form

*Preconditions*
* Admin user is logged in
* Team list is opened

|Test Step|Expected Result|
|---------|---------------|
|Leave all fields empty.|All fields are empty.|
|Save the team.|Team is not saved.<br>There are errors under the both fields displayed.|
|||
|Enter the text with > 50 characters to the Jmeno field.|Jmeno field contains more than 50 characters.|
|Enter valid number to the Pocet clenu field.|Pocet clenu contains valid number.|
|Save the team.|Team is not saved.<br>Error under the Jmeno field is displayed.|
|||
|Enter valid team name to the Jmeno field.|Team name is entered.|
|Enter negative number to the Pocet clenu field.|Negative number is entered.|
|Save the team.|Team is not saved.<br>Error under the Pocet clenu field is displayed.|
|||
|Enter valid team name to the Jmeno field.|Team name is entered.|
|Enter zero to the Pocet clenu field.|Zero is entered.|
|Save the team.|Team is not saved.<br>Error under the Pocet clenu field is displayed.|

*Postconditions*
* Logout the admin user
