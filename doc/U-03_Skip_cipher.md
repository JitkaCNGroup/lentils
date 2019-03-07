# U-03: Skip cipher
**Actor:** Player

**Preconditions:** -

**Postconditions:**

 - Cipher is marked as done
 - Next cipher is unlocked
 - -10 points are subtracted from the team's score

## Main Scenario

 1. **Team:** decide they want to skip the cipher
 2. **System:** asks for confirmation
 3. **Team:** confirm the action
 4. **System:** marks the cipher as skipped and unlocks the next cipher

## Extension
3. Submit:
   1. If user cancels the action, nothing happens
4. Validation:
   1. If cipher is already completed nothing happens and no further points are subtracted

