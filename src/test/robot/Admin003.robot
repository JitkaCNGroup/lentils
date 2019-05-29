*** Settings ***
Documentation               Edit mista vyhlaseni na aktualni den 19:00
Library                     Selenium2Library
Library                     DateTime
Resource                    admin_pass.robot
Resource                    Tomas_Bata.robot

*** Variables ***
${Browser}              chrome
${URL}                  https://lusteniny.cngroup.dk/

*** Keywords ***
Open Lusteniny page
    [Arguments]         ${URL}  ${Browser} 
    open Browser        ${URL}  ${Browser} 
    Maximize Browser Window

Login 
    [Arguments]          ${username}     ${password}
    Clear Element Text   //input[@id='username']
    input text           //input[@id='username']    ${username}
    input text           //input[@type='password']  ${password}
    Click button         //button[@type='submit']

Open list Misto vyhlaseni
    Click link          //a[@href="/admin/finalplace/"]

Edit Misto vyhlaseni
    Execute Javascript  curday.js
    Click button        //button[@type="submit"]

Logout
    Click Element    //a[@href="/logout"] 
    Handle alert

Browser Shutdown
    Close Browser  
    

*** Test Cases ***
Open page
    Open Lusteniny page         ${URL}  ${Browser} 
    wait until page contains    Luštěniny

Login
    Login   ${username}     ${password}

Open list Misto vyhlaseni
    Open list Misto vyhlaseni

Edit Misto vyhlaseni
    Edit Misto vyhlaseni

Logout
    Logout

Browser Shutdown
    Browser Shutdown