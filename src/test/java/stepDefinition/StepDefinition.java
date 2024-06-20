package stepDefinition;
import Pojos.APIResponse;
import Pojos.Postapidata;
import Pojos.User;

import Utils.APIOperations;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.jfr.StackTrace;
import lombok.extern.flogger.Flogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.junit.Assert.assertEquals;

public class StepDefinition {
    RequestSpecification requestSpecification = APIOperations.getRequestSpec();
    ResponseSpecification responseSpecification = APIOperations.getResponseSpec();

    private static final Logger logger = LogManager.getLogger(StepDefinition.class);


    //LogManager.getLogger(StepDefinition.class);

    private Response response;
    private String endpoint;

    private ExtentTest test;
    private ExtentReports extent;

    public int count;

    @Given("the api endpoint is {string}")
    public void the_api_endpoint_is(String endpoint) {
        RestAssured.baseURI = "https://reqres.in/";
        this.endpoint = endpoint;

    }


    @When("I Place a {string} request")
    public void i_place_a_request(String method) {
        RequestSpecification r = given().spec(requestSpecification).log().all();
        if (method.equalsIgnoreCase("GET")) {
            logger.info("Sending GET request to endpoint: {}", endpoint);
            response = r.when().get(endpoint).then().spec(responseSpecification).extract().response();
            String resp = response.asString();
            System.out.println(resp);
        } else if (method.equalsIgnoreCase("PUT")) {
            logger.info("Sending PUT request to endpoint: {}", endpoint);
            response = r.when().put(endpoint).then().spec(responseSpecification).extract().response();

        } else if (method.equalsIgnoreCase("DELETE")) {
            logger.info("Sending DELETE request to endpoint: {}", endpoint);
            response = r.when().delete(endpoint).then().spec(responseSpecification).extract().response();

        } else if (method.equalsIgnoreCase("POST")) {
            logger.info("Sending POST request to endpoint: {}", endpoint);
            Postapidata data = new Postapidata("morpheus", "leader");
            RequestSpecification re = given().body(data).spec(requestSpecification).log().all();
            response = re.when().post(endpoint).then().spec(responseSpecification).extract().response();
            //response = given().log().all().body(data).header("Content-type", "application/json").when().post(endpoint).then().extract().response();

        }

        // response = given().log().all().when().get(endpoint).then().extract().response();
        // System.out.println(response.getBody().asString());
    }

    @Then("Validate the response")
    public void validate_the_response() throws JsonProcessingException {
//        JsonPath jsonPath = response.jsonPath();
//        int tp = jsonPath.getInt("total_pages");
//        System.out.println(tp);
//        String name = jsonPath.getString("data[0].first_name");
//        System.out.println(name);
//        int id = jsonPath.getInt("data[4].id");
//        System.out.println("ID is : " + id);
//        String url= jsonPath.getString("support.url");
//        System.out.println(url);

        ObjectMapper mapper = new ObjectMapper();

        APIResponse api = mapper.readValue(response.asString(), APIResponse.class);
        System.out.println(api.getPage());
        System.out.println(api.data.get(1).first_name);

        System.out.println(api.data.get(0).first_name);
        for (User user : api.data) {
            System.out.println(user);
        }
    }

//    @When("I Place a POST request")
//    public void i_place_a_post_request() {
//        //String body = "{\n" + "    \"name\": \"morpheus\",\n" + "    \"job\": \"leader\"\n" + "}";
//
//
//    }

    @Then("Status code should be {int}")
    public void status_code_should_be(int statuscode) {
       try {
           assertEquals(statuscode, response.getStatusCode());
           System.out.println("Statuscode as expected " + response.getStatusCode());
           logger.info("Status code as expected : " + statuscode);
       }catch (AssertionError e) {
           logger.error("Status code as expected : " + statuscode);
           throw e;
       }
    }

    @Then("Validate the XML Response")
    public void validate_the_xml_response() throws IOException {
        String resp = response.asString();
        System.out.println(resp);

        response.then().statusCode(200);
        response.then().body("root.city", equalTo("San Jose"));

        response.then().body(hasXPath("/root/firstName", equalTo("John")));

        String xpathFromProperties = APIOperations.getProperties("xpath1");
        System.out.println("XPath from Properties: " + xpathFromProperties);


        if (xpathFromProperties == null || xpathFromProperties.isEmpty()) {
            throw new IllegalArgumentException("XPath expression from properties is null or empty");
        }

        //response.then().body(hasXPath(xpathFromProperties), equalTo("John"));    //this will not work
        response.then().body(hasXPath(APIOperations.getProperties("xpath1") + "[text()='John']")); //working
        response.then().body(hasXPath(APIOperations.getProperties("xpath2") + "[text()='San Jose']"));
        //response.then().body(hasXPath(xpathFromProperties + "[text()='John']"));  //working

        //Validating XML
        XmlPath xp = response.xmlPath();
        String name = xp.getString("root.firstName");
        System.out.println(name);

        String lastName = xp.getString("root.lastName");
        System.out.println(lastName);

        String city = xp.getString("root.city");
        System.out.println(city);

        //We can get count no of users
        List<String> Users = xp.getList("root");
        assertEquals(Users.size(), 1);
        System.out.println("Size of List or Users is : " + Users.size());

    }


//    @And("User need to validate XML Schema from XSD file {string}")
//    public void user_need_to_validate_xml_schema_from_xsd_file(String filepath)  {
//        filepath = "E:/My Projects/All Projects/API Testing/src/test/resources/schema-definition.xsd";
//        File xsdfile = new File(filepath);
//        response.then().assertThat().body(matchesXsd(filepath));
//        response.then().assertThat().body(RestAssuredMatchers.matchesXsd(filepath));
//        response.then().assertThat().body(matchesXsdInClasspath("schema-definition.xsd"));
//    }


    @Then("Validate JSON Schema from {string}")
    public void validate_json_schema_from(String filepath) {
        filepath = "E:/My Projects/All Projects/API Testing/src/test/resources/FirstGet Schema.json";
        File schemaFile = new File(filepath);
        //response.then().assertThat().body(matchesJsonSchemaInClasspath("FirstGet Schema.json"));    //When File is there in resource folder just pass the file name no need of full path it will not take it
        response.then().assertThat().body(matchesJsonSchema(schemaFile));
        logger.debug("Assertion Passed");
    }
}
