package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class StepDefinitions {

    private Response response;
    private String endpoint;

    private Document requestbody;

    @Given("the endpoint is {string}")
    public void the_endpoint_is(String endpoint) {
        RestAssured.baseURI = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
        this.endpoint = endpoint;
        System.out.println(endpoint);
    }

    @Given("the initial request xml body is:")
    public void the_initial_request_xml_body_is(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        requestbody = convertStringToXMLDocument(xmlString);
        System.out.println(xmlString);
    }


//    @And("I update the following fields")   // When u are not creating datatable
//    public void i_update_the_following_fields(String field,String value) {
//        Node node = requestbody.getElementsByTagName(field).item(0);
//        if(node!=null) {
//            node.setTextContent(value);
//        }
//        else
//        {
//            System.out.println("Field" + field + "not found in XML Document ");
//        }
//    }

    @And("I update the following fields")
    public void i_update_the_following_fields(List<Map<String, String>> fields) {
        for (Map<String, String> field : fields) {
            String fieldname = field.get("field");
            String newvalue = field.get("value");
            Node node = requestbody.getElementsByTagName(fieldname).item(0);
            if (node != null) {
                node.setTextContent(newvalue);
                System.out.println("Field Updated Successfully");
            } else {
                System.out.println("Field" + field + "not found in XML Document ");
            }

        }
    }

    private Document convertStringToXMLDocument(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
        return doc;
    }

    public String convertDocumentToString(Document doc) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));     //Source to result
        System.out.println("XML BODY IS" + writer);
        return writer.getBuffer().toString();
    }


    @When("I Place a POST request")
    public void i_place_a_POST_request() throws TransformerException {
        String request = "post";
        System.out.println("This is a " + request);
        String xmlbody = convertDocumentToString(requestbody);
        response = given().header("Content-Type", "text/xml; charset=utf-8").body(xmlbody).when().post(baseURI).then().assertThat().statusCode(200).extract().response();

    }

    @Then("Status code should matched")
    public void status_code_should_matched() {
        Assert.assertEquals(200, response.getStatusCode());
        int code = response.getStatusCode();
        System.out.println(code);
    }

    @And("Read the response in console")
    public void read_the_response_in_console() {
        String newresponse = response.asString();
        System.out.println(newresponse);
    }

}
