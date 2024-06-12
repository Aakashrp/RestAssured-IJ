@MainTest
Feature: Test and validate API request

  @Testcase
  Scenario: Place a API request
    Given the api endpoint is "/api/users?page=2"
    When I Place a "GET" request
    Then Status code should be 200
    And Validate the response
    And Validate JSON Schema from "filepath"

  @Testtwo
  Scenario: Place a API request
    Given the api endpoint is "/api/users"
    When I Place a "POST" request
    Then Status code should be 201

  @TestThree
  Scenario: Hitting a api to get xml response
    Given the endpoint is "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso"
    And the initial request xml body is:
    """
     <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <soap:Body>
       <CapitalCity xmlns="http://www.oorsprong.org/websamples.countryinfo">
          <sCountryISOCode>US</sCountryISOCode>
      </CapitalCity>
     </soap:Body>
    </soap:Envelope>
   """
    And I update the following fields
    |field          | value |
    |sCountryISOCode| INDIA |
    When I Place a POST request
    Then Status code should matched
    And Read the response in console