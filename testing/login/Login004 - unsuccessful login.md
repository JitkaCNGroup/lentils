# Login004 - unsuccessful login

*Description*
>This test case verifies that it is not possible to login with bad credentials


*Preconditions*
* Login page is opened.
* team is created with team name and PIN

|Test Step|Expected Result|
|---------|---------------|
|Leave fields username and password empty.|Fields are empty.|
|Click on Login button.|User is not logged in.<br>Error message is displayed at the login page.|
|||
|Enter correct team name to the username field.|Correct team name is entered.|
|Leave the password field empty.|Password field is empty.|
|Click on Login button.|User is not logged in.<br>Error message is displayed at the login page.|
|||
|Leave the username field empty.|Username field is empty.|
|Enter correct PIN to the password field.|Correct PIN is entered.|
|Click on Login button.|User is not logged in.<br>Error message is displayed at the login page.|
|||
|Enter wrong team name to the username field.|Wrong team name is entered.|
|Enter correct PIN to the password field.|Correct PIN is entered.|
|Click on Login button.|User is not logged in.<br>Error message is displayed at the login page.|
|||
|Enter correct team name to the username field.|Correct team name is entered.|
|Enter wrong PIN to the password field.|Wrong PIN is entered.|
|Click on Login button.|User is not logged in.<br>Error message is displayed at the login page.|
|||
|Enter wrong team name to the username field.|Wrong team name is entered.|
|Enter wrong PIN to the password field.|Wrong PIN is entered.|
|Click on Login button.|User is not logged in.<br>Error message is displayed at the login page.|
*Postconditions*
