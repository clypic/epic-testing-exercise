@epic-portal
Feature: Epic Web Portal 

  Scenario: Test for user sign-in
    Given the user opens the URL "http://www.epic.com.mt"
    Then page title must contain "epic | Great network. Great value. Unlimited Mobile Plans, Fibre Internet & Business"
    When user click link "My Account"
    Then page title must contain "epic ID Login"
    And sign-in form must be displayed
    When user sign-in with username "99368021" and password "Voda1234"
    Then page title must contain "Epic - "
    And content title must be "Prepaid"
    And MSISDN must be "99368021"
    And current balance must be visible
    And prepaid-plan must be visible
    When user click link "Log out"
    Then page title must contain "Epic - Log Off Successful"
