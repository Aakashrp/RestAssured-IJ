package stepDefinition;

import Pojos.APIResponse;
import Pojos.Postapidata;
import Pojos.User;
import Utils.APIOperations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.junit.Assert.assertEquals;

import static io.restassured.RestAssured.given;

public class StepDefinition {

    RequestSpecification requestSpecification = APIOperations.getRequestSpec();

    ResponseSpecification responseSpecification = APIOperations.getResponseSpec();
    private Response response;
    private String endpoint;


    @Given("the api endpoint is {string}")
    public void the_api_endpoint_is(String endpoint) {
        RestAssured.baseURI = "https://reqres.in/";
        this.endpoint = endpoint;
    }

    @When("I Place a {string} request")
    public void i_place_a_request(String method) {
        RequestSpecification r = given().spec(requestSpecification).log().all();

        if (method.equalsIgnoreCase("GET")) {
            response = r.when().get(endpoint).then().spec(responseSpecification).extract().response();

        } else if (method.equalsIgnoreCase("PUT")) {
            response = r.when().put(endpoint).then().spec(responseSpecification).extract().response();

        } else if (method.equalsIgnoreCase("DELETE")) {
            response = r.when().delete(endpoint).then().spec(responseSpecification).extract().response();

        } else if (method.equalsIgnoreCase("POST")) {
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
        assertEquals(statuscode, response.getStatusCode());
        System.out.println("Statuscode as expected " + response.getStatusCode());
    }


}
