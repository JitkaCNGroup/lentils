# U-19: Initialize game
**Actor:** Admin

**Preconditions:** -

**Postconditions:**

 - Team status for every cypher is reset (first is in status pending)
 - Every team has no taken hint
 - Teams have 0 points

## Main Scenario

 1. **Admin:** decides to start the game
 2. **System:** asks user for password
 3. **Admin:** enters the password
 4. **System:** reset game data

## Extension
3. Submit:
   1. If user cancels the action, nothing happens
   2. If user enters wrong password, system shows an error