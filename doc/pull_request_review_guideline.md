# Pull request review guideline

This document defines unified checklist for pull request reviewers
and programmers. All the criteria should be met before the pull request
branch can be merged.

## Checklist

- Code MUST be compilable
- Code follows general code style
- Code should be readable and its intent should be understandable by reviewer
- Duplicate code should be avoided
- There should be no dead code (exception are methods for future use)
- No comments (except javadoc) should be present (replace with better method,
  parameters or variables names to make the code self-documenting)
- All TODO comments must be resolved before merging or github issue should be created
- Do reasonable unit tests exists?
- Are the tests comprehensive?
- Is git code history clean?

## Merging
- All requests for change in the pull request should be resolved before merge
- Continuous Integration checks should pass (if deployed)

