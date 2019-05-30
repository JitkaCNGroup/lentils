*** Settings ***
Documentation               Edit mista vyhlaseni na aktualni den 19:00
Library                     Selenium2Library
Library                     DateTime
Resource                    admin_pass.robot


*** Variables ***


*** Keywords ***
Edit Misto vyhlaseni
    Execute Javascript  curday.js
    Click button        //button[@type="submit"]


*** Test Cases ***
Open Lusteniny Page
    Open Lusteniny Page         ${URL}  ${Browser} 

Login
    Login   ${username}     ${password}

Open List Misto vyhlaseni
    Open List Misto Vyhlaseni
    
Edit Misto vyhlaseni
    Edit Misto vyhlaseni

Logout
    Logout

Browser Shutdown
    Browser Shutdown