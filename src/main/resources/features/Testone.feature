@MainTest
Feature: Test and validate API request

  @Testcase
  Scenario: Place a API request
    Given the api endpoint is "/api/users?page=2"
    When I Place a "GET" request
    Then Status code should be 200
    And Validate the response


  @Testtwo
  Scenario: Place a API request
    Given the api endpoint is "/api/users"
    When I Place a "POST" request
    Then Status code should be 201