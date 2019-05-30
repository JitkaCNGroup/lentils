*** Settings ***
Documentation               Open Lusteniny Page, Login, Logout, Close browser, Open list Prehled sifer, Prehled tymu, Skore, Odhlasit
Library                     Selenium2Library
Resource                    organizer_pass.robot

*** Variables ***
${Browser}              chrome
${URL}                  https://lusteniny.cngroup.dk/

*** Keywords ***
Open Lusteniny Page
    [Arguments]         ${URL}  ${Browser} 
    open Browser        ${URL}  ${Browser} 
    Maximize Browser Window

Login 
    [Arguments]         ${username}     ${password}
    Clear Element Text  //input[@id='username']
    input text          //input[@id='username']    ${username}
    Clear Element Text  //input[@type='password']
    input text          //input[@type='password']  ${password}
    Click button        //button[@type='submit']

Open List Prehled sifer
    Click link          //a[@href="/game/progress"]

Open List Prehled tymu
    Click link          //a[@href="/game/progress/teamsList"]

Open List Skore
    Click link          //a[@href="/game/score"]

Logout
    Click Element       //a[@href='/logout'] 
    Handle Alert

Browser Shutdown
    Close Browser 
