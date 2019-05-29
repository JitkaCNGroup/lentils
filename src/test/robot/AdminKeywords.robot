*** Settings ***
Documentation               Open Lusteniny page, Login, Logout, Close browser, Open list Sifry, Tymy, Misto vyhlaseni, Kontakt, Odhlasit
Library                     Selenium2Library
Resource                    admin_pass.robot

*** Variables ***
${Browser}              chrome
${URL}                  https://lusteniny.cngroup.dk/

*** Keywords ***
Open Lusteniny Page
    [Arguments]         ${URL}  ${Browser} 
    open Browser        ${URL}  ${Browser} 
    Maximize Browser Window

Login 
    [Arguments]          ${username}     ${password}
    Clear Element Text   //input[@id='username']
    input text           //input[@id='username']    ${username}
    Clear Element Text   //input[@type='password']
    input text           //input[@type='password']  ${password}
    Click button         //button[@type='submit']

Open List Sifry
    Click link          //a[@href="/admin/cypher"]

Open List Tymy
    Click link          //a[@href="/admin/team"]

Open List Misto Vyhlaseni
    Click link          //a[@href="/admin/finalplace/"]

Open List Kontakt
    Click link          //a[@href="/admin/contact/"]

Logout
    Click Element    //a[@href='/logout'] 
    Handle Alert

Browser Shutdown
    Close Browser 