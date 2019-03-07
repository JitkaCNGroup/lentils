# Merge request review guideline

This document defines unified checklist for merge request reviewers
and programmers. All the criterias should be met before the merge request
branch can be merged.

## Checklist

- Code MUST be compilable
- Code follows general code style
- Checkstyle must not be turned off and if so, comment must be provided why
- Code should be readable
- Duplicate code should be avoided#
- There should be no dead code
- No comments (except javadoc) should be present (replace with better method,
  parameters or variables names to make the code self-documenting)
- All TODO comments must be resolved before merging
- Do reasonable unit tests exists?
- Are the tests comprehensive?
- Is git code history clean?

## Merging
- All requests for change should be resolved before merge
- CI should pass (if deployed)

