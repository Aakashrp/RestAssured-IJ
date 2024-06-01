@Main
Feature: Test and validate API Get request

  @New
  Scenario: Place a API request
    Given the api endpoint is "/api/users/2"
    When I Place a "GET" request
    Then Status code should be 200

#
#  @TestLevel01
#  Scenario: Place a POST Request
#    Given the api endpoint is ""
#    When I place a "POST" request
#    Then status code is 200
