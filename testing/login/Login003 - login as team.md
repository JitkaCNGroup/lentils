# Login003 - login as team

*Description*
>This test case verifies that after successful login the team is redirected to the Game part.


*Preconditions*
* Login page is opened.
* The team has already started the game.
* There are some cyphers created by admin.

|Test Step|Expected Result|
|---------|---------------|
|Enter team name to the username field.|Username is entered.|
|Enter valid PIN to the password field.|PIN is entered.|
|Click on Login button.|Team name is displayed in the header.<br>Team score is displayed in the header.<br>The list of cyphers is displayed.|
|Click on Logout button.|Team is logged out - login page is displayed.|

*Postconditions*
