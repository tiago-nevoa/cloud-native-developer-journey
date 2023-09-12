# Behaviour Driven Development Exercise - Java

This branch contains the code / starting point for the Java BDD exercise from the [material repository](https://github.tools.sap/cloud-curriculum/material).

# User Story: Search by title

As a client
I want to search movies by title
so that I can rent the movie my friends told me about

## Acceptance Criteria
**Scenario: Exact match**
Given two movies titled "Forrest Gump" and "Titanic"
When "Forrest Gump" is searched
Then the results list consists of "Forrest Gump"

**Scenario: Partial match**
Given two movies titled "The Godfather" and "City of God"
When "God" is searched
Then the results list consists of "The Godfather" and "City of God"