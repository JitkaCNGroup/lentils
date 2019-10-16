# Game006 - view contact

*Description*
>this testcase verifies that the contact information can be displayed for team

*Preconditions*
* Language is set to Czech
* The teamA is created
* The contact information is created with:
    * telephone
    * email
    * fb event
* The final place is created with:
    * finish time = current time + 1 hour
    * results time = current time + 2 hours
    * access time = 10 minutes
* TeamA is logged in

*Test Steps*

|Test Step|Expected Result|
|---------|---------------|
|Navigate to the Kontakty via the header menu.|The contact detail is opened.|
|Verify that contact information is displayed.|Contact information is present.|
|Verify that telephone is displayed.|Telephone is present.|
|Verify that email is displayed.|Email is present.|
|Click on the Facebook button.|The fb event is opened.|
|Go back to the contact detail.|The contact detail is opened.|
|Click on the CN Group button|The CN Group web is opened.|
|Go back to the contact detail.|The contact detail is opened.|

*Postconditions*
* Logout the teamA