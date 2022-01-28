@api-todo
Feature: API Todo Tests

  Scenario:  Test for the first user's todo
    Given the URL endpont is valid
    When we request the first Todo details
    Then the response status code should be 200
    And the response body must be valid JSON
    And the response property title must be "delectus aut autem"
    And the response property userId must be 1
    And the response property id must be 1
    And the response property completed value must be "false"
