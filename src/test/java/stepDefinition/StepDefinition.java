package stepDefinition;

import Pojos.APIResponse;
import Pojos.Postapidata;
import Pojos.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

import static io.restassured.RestAssured.given;

public class StepDefinition {

    private Response response;
    private String endpoint;

    @Given("the api endpoint is {string}")
    public void the_api_endpoint_is(String endpoint) {
        RestAssured.baseURI = "https://reqres.in/";
        this.endpoint = endpoint;
    }

    @When("I Place a GET request")
    public void i_place_a_get_request() {
        response = given().log().all().when().get(endpoint).then().extract().response();
        System.out.println(response.getBody().asString());
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

    @When("I Place a POST request")
    public void i_place_a_post_request() {
        //String body = "{\n" + "    \"name\": \"morpheus\",\n" + "    \"job\": \"leader\"\n" + "}";

        Postapidata data = new Postapidata("morpheus", "leader");
        response = given().log().all().body(data).header("Content-type", "application/json").when().post(endpoint).then().extract().response();

    }

    @Then("Status code should be {int}")
    public void status_code_should_be(int statuscode) {
        assertEquals(statuscode, response.getStatusCode());
    }


}
