@Main
Feature: Test and validate API Get request

  @New
  Scenario: Place a API request
    Given the api endpoint is "/api/users/2"
    When I Place a GET request
    Then Status code should be 200
