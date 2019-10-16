# Game005 - view map

*Description*
>this testcase verifies that the map with location of the cypher can be displayed

*Preconditions*
* Language is set to Czech
* There is cypher1 created
* The teamA is created
* The final place is created with:
    * finish time = current time + 1 hour
    * results time = current time + 2 hours
    * access time = 10 minutes
* The game is already started
* TeamA is logged in and cypher1 is in status Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that Zobrazit na mapě button is present.|The button is displayed.|
|Click on the Zobrazit na mapě button.|New browser tab is opened.|
|Verify that the map with the cypher1 location is displayed correctly.|The map is displayed correctly.|
|Close the browser tab with map.|The tab is closed.|

*Postconditions*
* Logout the teamA