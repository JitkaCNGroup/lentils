# U-18: Manage hints for cypher (CRUD)
**Actor:** Admin

**Preconditions:** -

**Postconditions:** -

## List hints

### Main Scenario

1. **Admin:** wants to see all hints for a cypher
2. **System:** shows a lsit of hints with these informations:
    - Points
    - Text

## Add hint

**Postconditions:**
- Newly created hint shows in the hint list for a correct cypher

### Main Scenario

1. **Admin:** wants to add hint to a cypher
2. **System:** lets admin to input following data:
    - Text of the hint
    - Points
3. **Admin:** fills-in data and submits
4. **System:** saves the hint

### Extension
4. Submit:
    1. If some fields are missing, system shows an error

## Edit hint

**Precondition**:
    - Hint exists

### Main Scenario

1. **Admin**: wants to edit existing hint
2. **System**: lets user change these fields:
    - Points
    - Text of the hint
3. **Admin**: changes desired fields and submits
4. **System**: saves the hint data

## Extension
4. Submit:
    1. If some of the fields are missing, system shows an error

## Delete hint

**Postcondition**:
  - Hint is not shown in the list of hints for a cypher
  - Cypher is not presented to players

### Main Scenario

1. **Admin**: wants to delete a given hint
2. **System**: asks for confirmation
3. **Admin**: confirms action
4. **System**: deletes the hint

### Extension
3. Confirm:
  1. If user does not confirm, actions ends here
