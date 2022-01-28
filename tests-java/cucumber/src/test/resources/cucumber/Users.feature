@users
Feature: MySQL Schema Tests

  Scenario: Test for users table
    Given the connection details hostname "mysql" on port "3306" with username "epic" and password "secret"
    When we get all users from table users
    Then the list must contain user "99368021"