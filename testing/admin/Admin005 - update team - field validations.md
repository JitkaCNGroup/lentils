# Admin005 - update team - field validations

*Description*
>This test case verifies the field validations of the team form

*Preconditions*
* Admin user is logged in
* Team list is opened
* There are teamA and teamB already created

|Test Step|Expected Result|
|---------|---------------|
|Open edit mode of the already created teamA:<br>Click on Akce at the team row in the list and select Upravit.|The team name and number of players are filled into the form.|
|Leave all fields empty.|All fields are empty.|
|Save the team.|Team is not saved.<br>There is an error displayed.|
|||
|Enter the text with > 50 characters to the Jmeno field.|Jmeno field contains more than 50 characters.|
|Enter valid number to the Pocet clenu field.|Pocet clenu contains valid number.|
|Save the team.|Team is not saved.<br>Error under the Jmeno field is displayed.|
|||
|Enter the text teamB to the Jmeno field.|Jmeno field contains teamB.|
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
