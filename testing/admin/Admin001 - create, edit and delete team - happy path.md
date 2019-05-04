# Admin001 - create, edit and delete team - happy path

*Description*
>This test case verifies that it is possible to create, edit and delete team in Admin


*Preconditions*
* Admin user is logged in
* Team list is opened

|Test Step|Expected Result|
|---------|---------------|
|Enter valid team name to the Jmeno field.|Team name is entered.|
|Enter valid number to the Pocet clenu field.|Pocet clenu contains valid number.|
|Save the team.|New row with the team information is created in the team list.<br>No error is displayed.|
|Validate that the new row contains all correct information.|The row contains:<br>- ID<br>- Team name<br>- User name<br>- Number of players<br>- PIN|
|||
|Open edit mode of the already created team:<br>Click on Akce at the team row in the list and select Upravit.|The team name and number of players are filled into the form correctly.|
|Edit the team name in the Jmeno field.|The team name is edited.|
|Edit the number of players in the Pocet clenu field.|The number of players is edited.|
|Save the team.|The team is saved correctly.<br>No error is displayed.<br>The team row in the list is updated.|
|||
|Click on the Akce at the team row in the list and select Smazat.|The team row is removed from the teams list.|

*Postconditions*
* Logout the admin user
