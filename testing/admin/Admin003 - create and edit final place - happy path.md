# Admin003 - ~~create and~~ edit final place - happy path

*Description*
>This test case verifies that it is possible ~~to create and~~ edit the final place in Admin


*Preconditions*
* Admin user is logged in
* Final place is opened

|Test Step|Expected Result|
|---------|---------------|
|Enter valid text to the Jmeno a popis field.|Jmeno a popis field is not empty.|
|Click into the map to select the coordinates.|The Souradnice field is not empty.|
|Enter a time to the Cas field.|Cas field is not empty.|
|Save the final place.|The final place is saved correctly.<br>No error is displayed.|
|||
|~~Edit all fields of the final place.~~|~~All fields are edited.~~|
|~~Save the final place.~~|~~The final place is saved correctly.<br>The fields are updated.<br>No error is displayed~~.|

*Postconditions*
* Logout the admin user
