# Game005 - view map

*Description*
>this testcase verifies that the map with location of the cypher can be displayed

*Preconditions*
* There is cypher1 created
* The teamA is created
* The game is already started
* TeamA is logged in and cypher1 is in satus Pending

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Click on the cypher1.|The detail of the cypher1 is displayed.|
|Verify that Zobrazit na mape button is present.|The button is displayed.|
|Click on the Zobrazit na mape butotn.|New browser tab is opened.|
|Verify that the map with the cypher1 location is displayed correctly.|The map is displayed correctly.|
|Close the browser tab with map.|The tab is closed.|

*Postconditions*
* Logout the teamA