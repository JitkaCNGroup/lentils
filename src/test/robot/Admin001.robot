*** Settings ***
Documentation               This is test to TC Admin001
Library                     Selenium2Library
Resource                    admin_pass.robot
Resource                    AdminKeywords.robot


*** Variables ***
${jmeno}                kne01
${pocet_clenu}          3
${nove_jmeno}           kne02
${novy_pocet_clenu}     5


*** Keywords ***
Add Team
    [Arguments]         ${jmeno}    ${pocet_clenu}
    input text          //input[@id='name']                 ${jmeno}
    input text          //input[@id='numberOfMembers']      ${pocet_clenu}
    Click button        //input[@type='submit']
    # Check
    #${error}        Get text                //p[@class="error"]
    #${count}        Get Element count       //p[@class="error"]
    #Run Keyword if      "${count}" == "1"       log to console      ${error}
    Page should not contain     Zadané jméno už existuje.
    ${_id_}         Get text        //tr[contains(.,'${jmeno}')]//td[1]
    ${_nazev_}      Get text        //tr[contains(.,'${jmeno}')]//td[2]
    ${_username_}   Get text        //tr[contains(.,'${jmeno}')]//td[3]
    ${_pocet_}      Get text        //tr[contains(.,'${jmeno}')]//td[4]
    ${_pass_}       Get text        //tr[contains(.,'${jmeno}')]//td[5]
    ${_Akce_}       Get text        //tr[contains(.,'${jmeno}')]//td[6]
    ${team_info}         Create list         ${_id_}    ${_nazev_}      ${_username_}      ${_pocet_}     ${_pass_}     ${_Akce_}                   
    log to console       ${team_info}

Edit Number Of Team Members
    [Arguments]         ${jmeno}        ${pocet_clenu}      ${novy_pocet_clenu}
    [Return]            ${team_id}      ${cur_jmeno}        ${cur_pocet}
    # Save team_id
    ${team_id}          Get text   //tr[contains(.,'${jmeno}')]//td[1]
    # Click Upravit
    Click button        //tr[contains(.,'${jmeno}')]//button[@type='button']
    Click Element       //a[@href="/admin/team/update/${team_id}"]
    # Check data in edit mode
    ${cur_jmeno}        Get value        //input[@id="name"]
    ${cur_pocet}        Get value        //input[@id="numberOfMembers"]
    Run Keyword if      "${cur_jmeno}" == "${jmeno}"        log to console      Name is filled correctly.
    Run Keyword if      "${cur_pocet}" == "${pocet_clenu}"        log to console      Number of players is filled correctly.
    # Edit something
    Clear Element text  //input[@id='numberOfMembers']
    input text          //input[@id='numberOfMembers']      ${novy_pocet_clenu}
    # Save changes
    Click button        //input[@type='submit']
    Page should not contain     Počet členů musí být vetší než 0.
    # Check changes     
    ${_id_}         Get text        //tr[contains(.,'${jmeno}')]//td[1]
    ${_nazev_}      Get text        //tr[contains(.,'${jmeno}')]//td[2]
    ${_username_}   Get text        //tr[contains(.,'${jmeno}')]//td[3]
    ${_pocet_}      Get text        //tr[contains(.,'${jmeno}')]//td[4]
    ${_pass_}       Get text        //tr[contains(.,'${jmeno}')]//td[5]
    ${_Akce_}       Get text        //tr[contains(.,'${jmeno}')]//td[6]
    ${team_info}         Create list         ${_id_}    ${_nazev_}      ${_username_}      ${_pocet_}     ${_pass_}     ${_Akce_}                   
    log to console       ${team_info}

Edit Team Name
    [Arguments]         ${jmeno}    ${nove_jmeno}
    [Return]            ${team_id}      ${count}
    # Save team_id
    ${team_id}          Get text   //tr[contains(.,'${jmeno}')]//td[1]
    # Click Upravit
    Click button        //tr[contains(.,'${jmeno}')]//button[@type='button']
    Click Element       //a[@href="/admin/team/update/${team_id}"]
    # Edit something
    Clear Element text  //input[@id='name']
    input text          //input[@id='name']     ${nove_jmeno}
    # Save changes
    Click button        //input[@type='submit']
    Page should not contain     Zadané jméno už existuje.
    # Check changes
    ${count}        Get Element count  //tr[contains(.,'${nove_jmeno}')]
    Run Keyword if      "${count}" == "1"       log to console      Nice, name ${jmeno} was changed to ${nove_jmeno}. 
                        ...     ELSE            log to console      Something is wrong. Name wasn't changed succesfully.

Delete Team
    [Arguments]     ${nove_jmeno}
    [Return]        ${team_id}      ${count}
    # Save team_id
    ${team_id}      Get text   //tr[contains(.,'${nove_jmeno}')]//td[1]
    # Click Delete
    Click button    //tr[contains(.,'${nove_jmeno}')]//button[@type='button']
    Click Element   //a[@href="/admin/team/delete/${team_id}"]
    # Check if deleted item remains
    ${count}        Get Element count  //tr[contains(.,'${nove_jmeno}')]
    Run Keyword if      "${count}" == "0"       log to console      Nice, team was deleted. 
                        ...     ELSE            log to console      Something is wrong. Team wasn't deleted.



*** Test Cases ***
Open Lusteniny Page
    Open Lusteniny Page         ${URL}  ${Browser} 

Login
    Login   ${username}     ${password}

Open List Tymy
    Open List Tymy 

Add team
    Add Team    ${jmeno}    ${pocet_clenu}
    wait until page contains      ${jmeno}

Edit Number Of Team Members
    Edit Number Of Team Members  ${jmeno}       ${pocet_clenu}      ${novy_pocet_clenu}

Edit Team Name
    Edit Team Name  ${jmeno}  ${nove_jmeno}

Delete Team
    Delete Team  ${nove_jmeno}

Logout
    Logout

Browser Shutdown
    Browser Shutdown




