*** Settings ***
Documentation               This is test to TC Admin002
Library                     Selenium2Library
Resource                    admin_pass.robot
Resource                    Tomas_Bata.robot
Resource                    Admin001.robot


*** Variables ***
${Browser}              chrome
${URL}                  https://lusteniny.cngroup.dk/

*** Keywords ***
Open Lusteniny page
    [Arguments]         ${URL}  ${Browser} 
    open Browser        ${URL}  ${Browser} 
    Maximize Browser Window


Open Cypher list
    Click link          //a[@href="/admin/cypher"]
    
Add Cypher
    [Arguments]         ${Jmeno}    ${Poradi}   ${Odpoved}  ${Falesna_odpoved}  ${Bonus_info}   ${Popis_mista}  ${Adresa}
    [Return]            ${cypher_info}
    Click Element       //a[@href="/admin/cypher/add"]
    input text          //input[@id="name"]                 ${Jmeno}
    input text          //input[@id="stage"]                ${Poradi}
    input text          //input[@id="codeword"]             ${Odpoved}
    input text          //input[@id="trapCodeword"]         ${Falesna_odpoved}
    input text          //textarea[@id="bonusContent"]      ${Bonus_info}
    input text          //textarea[@id="placeDescription"]  ${Popis_mista}
    input text          //input[@id="mapAddress"]           ${Adresa}
    Execute Javascript  coordinates.js
    #Click Element       xpath=.//*[@id='map']
    Click button        //button[@type='submit']

    # Check the cypher row
    ${_Id_}                 Get text        //tr[contains(.,'${Jmeno}')]//td[1]
    ${_Jmeno_}              Get text        //tr[contains(.,'${Jmeno}')]//td[2]
    ${_Poradi_}             Get text        //tr[contains(.,'${Jmeno}')]//td[3]
    ${_Souradnice_}         Get text        //tr[contains(.,'${Jmeno}')]//td[4]
    ${_Popis_mista_}        Get text        //tr[contains(.,'${Jmeno}')]//td[5]
    ${_Odpoved_}            Get text        //tr[contains(.,'${Jmeno}')]//td[6]
    ${_Falesna_odpoved_}    Get text        //tr[contains(.,'${Jmeno}')]//td[7]
    ${_Bonus_info_}         Get text        //tr[contains(.,'${Jmeno}')]//td[8]
    Should not be empty      ${_Souradnice_}
    ${cypher_id}        Get text   //tr[contains(.,'${Jmeno}')]//td[1]
    ${cypher_info}      Create list     ${cypher_id}    ${Jmeno}    ${Poradi}   ${Popis_mista}   ${Odpoved}  ${Falesna_odpoved}  ${Bonus_info}
    ${_cypher_info_}    Create list     ${_Id_}    ${_Jmeno_}      ${_Poradi_}      ${_Popis_mista_}     ${_Odpoved_}     ${_Falesna_odpoved_}     ${_Bonus_info_}        
    Run Keyword if      "${_cypher_info_}"=="${cypher_info}"        log to console      Cypher row contains all correct information.
    sleep       5

Check Detail
    [Arguments]         ${Jmeno}    ${Poradi}   ${Odpoved}  ${Falesna_odpoved}  ${Bonus_info}   ${Popis_mista}  ${Adresa}
    ${cypher_id}        Get text   //tr[contains(.,'${Jmeno}')]//td[1]
    # Click Detail
    Click button    //tr[contains(.,'${Jmeno}')]//button[@type='button']
    Click Element   //a[@href="/admin/hint?cypherId=${cypher_id}"]
    sleep   5
    # Check the information in Detail
    ${_Id_}                 Get text        //tr[contains(.,'${Jmeno}')]//td[1]
    ${_Jmeno_}              Get text        //tr[contains(.,'${Jmeno}')]//td[2]
    ${_Poradi_}             Get text        //tr[contains(.,'${Jmeno}')]//td[3]
    ${_Souradnice_}         Get text        //tr[contains(.,'${Jmeno}')]//td[4]
    ${_Popis_mista_}        Get text        //tr[contains(.,'${Jmeno}')]//td[5]
    ${_Odpoved_}            Get text        //tr[contains(.,'${Jmeno}')]//td[6]
    ${_Falesna_odpoved_}    Get text        //tr[contains(.,'${Jmeno}')]//td[7]
    ${_Bonus_info_}         Get text        //tr[contains(.,'${Jmeno}')]//td[8]
    Should not be empty      ${_Souradnice_}
    ${_cypher_info_}    Create list     ${_Id_}    ${_Jmeno_}      ${_Poradi_}     ${_Popis_mista_}     ${_Odpoved_}     ${_Falesna_odpoved_}     ${_Bonus_info_}        
    Run Keyword if      "${_cypher_info_}"=="${cypher_info}"        log to console      Detail page contains all correct information.
    
    # Go back to Cypher list page
    Click Element           //a[@href="/admin/cypher"]

Edit Cypher
    [Arguments]         ${Jmeno}    ${Poradi}   ${Odpoved}  ${Falesna_odpoved}  ${Bonus_info}   ${Popis_mista}  ${Adresa}   ${new_Adresa}
    ${cypher_id}        Get text   //tr[contains(.,'${Jmeno}')]//td[1]
    # Click Upravit
    Click button    //tr[contains(.,'${Jmeno}')]//button[@type='button']
    Click Element   //a[@href="/admin/cypher/update/${cypher_id}"]
    # Check data in edit mode
    ${cur_jmeno}            Get value        //input[@id="name"]
    ${cur_poradi}           Get value        //input[@id="stage"]
    ${cur_odpoved}          Get value        //input[@id="codeword"]
    ${cur_false}            Get value        //input[@id="trapCodeword"]
    ${cur_bonus_info}       Get value        //textarea[@id="bonusContent"]
    ${cur_popis_mista}      Get value        //textarea[@id="placeDescription"]
    ${cur_adresa}           Get value        //input[@id="mapAddress"]
    Should not be empty     ${cur_adresa}
    Run Keyword if      "${cur_jmeno}" == "${Jmeno}" and "${cur_poradi}" == "${Poradi}" and "${cur_odpoved}"=="${Odpoved}" and "${cur_false}"=="${Falesna_odpoved}" and "${cur_bonus_info}"=="${Bonus_info}" and "${cur_popis_mista}"=="${Popis_mista}" and "${cur_adresa}"=="${Adresa}"       log to console      The cypher info are pre-filled correctly.
    # Edit all fields
    input text          //input[@id="name"]                 ${Jmeno}Edited
    input text          //input[@id="stage"]                ${Poradi}1
    input text          //input[@id="codeword"]             ${Odpoved}Edited
    input text          //input[@id="trapCodeword"]         ${Falesna_odpoved}Edited
    input text          //textarea[@id="bonusContent"]      ${Bonus_info}Edited
    input text          //textarea[@id="placeDescription"]  ${Popis_mista}Edited
    input text          //input[@id="mapAddress"]           ${new_Adresa}
    Click button        //button[@type='submit']
    sleep   5
    
Add hint
    [Arguments]     ${Jmeno}
    [Return]        ${cypher_id}
    # Save cypher_id
    ${cypher_id}      Get text   //tr[contains(.,'${Jmeno}')]//td[1]
    # Click Detail
    Click button    //tr[contains(.,'${Jmeno}')]//button[@type='button']
    Click Element   //a[@href="/admin/hint?cypherId=${cypher_id}"]
    # Add hint 1
    Click Element   //a[@href="/admin/hint/add?cypherId=${cypher_id}"]
    input text      //textarea[@id="text"]      ${hint1[0]}
    input text      //input[@id="value"]        ${hint1[1]}
    Click button    //button[@type="submit"]  
    sleep   5
    # Add hint 2
    Click Element   //a[@href="/admin/hint/add?cypherId=${cypher_id}"]
    input text      //textarea[@id="text"]      ${hint2[0]}
    input text      //input[@id="value"]        ${hint2[1]}
    Click button    //button[@type="submit"]
    # Add hint 3
    Click Element   //a[@href="/admin/hint/add?cypherId=${cypher_id}"]
    input text      //textarea[@id="text"]      ${hint3[0]}
    input text      //input[@id="value"]        ${hint3[1]}
    Click button    //button[@type="submit"]
    # Go back to Cypher list page
    Click Element           //a[@href="/admin/cypher"]
    sleep   5

Delete Cypher
    [Arguments]     ${Jmeno}
    [Return]        ${cypher_id}      ${count}
    # Save cypher_id
    ${cypher_id}      Get text   //tr[contains(.,'${Jmeno}')]//td[1]
    # Click Delete
    Click button    //tr[contains(.,'${Jmeno}')]//button[@type='button']
    Click Element   //a[@href="/admin/cypher/delete/${cypher_id}"]
    # Check if deleted item remains
    ${count}        Get Element count  //tr[contains(.,'${Jmeno}')]
    Run Keyword if      "${count}" == "0"       log to console      Nice, team was deleted. 
                        ...     ELSE            log to console      Something is wrong. Team wasn't deleted.
    sleep   5


Logout
    Click Element    //a[@href='/logout'] 
    Handle Alert

    sleep   5

Browser Shutdown
    Close Browser 


*** Test Cases ***
Open page
    Open Lusteniny page         ${URL}  ${Browser} 
    wait until page contains    Luštěniny

Login
    Login   ${username}     ${password}

Open Cypher list
    Open Cypher list

Add Cypher
    Add Cypher  ${Jmeno}    ${Poradi}   ${Odpoved}  ${Falesna_odpoved}  ${Bonus_info}   ${Popis_mista}  ${Adresa}

Check Detail page
    Check Detail  ${Jmeno}    ${Poradi}   ${Odpoved}  ${Falesna_odpoved}  ${Bonus_info}   ${Popis_mista}  ${Adresa}

Edit Cypher
    Edit Cypher  ${Jmeno}    ${Poradi}   ${Odpoved}  ${Falesna_odpoved}  ${Bonus_info}   ${Popis_mista}  ${Adresa}  ${new_Adresa}

Add hint
    Add hint  ${Jmeno}

Delete cypher
    Delete Cypher  ${Jmeno}

Logout
    Logout

Browser Shutdown
    Browser Shutdown



