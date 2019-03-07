# U-11: Manage ciphers
**Actor:** Admin

**Preconditions:** -

**Postconditions:** -

## Main Scenario

1. **Admin:** wants to create or edit or delete a cipher
2. **System:** asks admin for following cipher information:
   - Cipher title
   - Codeword
   - Stage number
   - Place position (lat/lng)
   - Place description
   - Bonus information (optional)

3. **Admin:** fills-in the data and submits
4. **System:** saves the cipher information into the system

## Extension
2. If action is delete, cipher is removed from the system
4. Submit:
   1. If required fields are missing system shows an error
