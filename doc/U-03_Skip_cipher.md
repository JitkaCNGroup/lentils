# U-03: Skip cypher
**Actor:** Player

**Preconditions:**

- Cypher is pending

**Postconditions:** 

- Next cipher is unlocked
- Correct amount of points is added to the team's score
- Current cipher is marked as skipped

## Main Scenario

 1. **Team:** wants to skip cypher
 2. **System:** asks for confirmation
 3. **Team:** confirm the action
 4. **System:** system marks the cypher as skipped for a team

## Extension
3. Confirmation:
   1. If team cancels the action, use case ends here