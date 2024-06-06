@Main
Feature: Test and validate API Get request

  @New
  Scenario: Place a GET API request
    Given the api endpoint is "/api/users/2"
    When I Place a "GET" request
    Then Status code should be 200

  @NewONE
  Scenario: Place a GET API request for validating XML Response
    Given the api endpoint is "https://mocktarget.apigee.net/xml"
    When I Place a "GET" request
    Then Status code should be 200
    And Validate the XML Response
  #  And User need to validate XML Schema from XSD file "filepath"