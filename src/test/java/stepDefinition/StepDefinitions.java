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
import org.w3c.dom.NodeList;
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
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

//    @And("I update the following fields")
//    public void i_update_the_following_fields(List<Map<String, String>> fields) {
//        for (Map<String, String> field : fields) {
//            String fieldname = field.get("field");
//            String newvalue = field.get("value");
//            Node node = requestbody.getElementsByTagName(fieldname).item(0);
//            if (node != null) {
//                node.setTextContent(newvalue);
//                System.out.println("Field Updated Successfully");
//            } else {
//                System.out.println("Field" + field + "not found in XML Document ");
//            }
//
//        }
//    }
//
    //With lambda and Streams

    @And("I update the following fields")
    public void i_update_the_following_fields(List<Map<String, String>> fields) {
        fields.forEach(field -> {
            List<String> mylist = field.values().stream().collect(Collectors.toList());     //Converting a map to list
            List<String> keyset = field.keySet().stream().collect(Collectors.toList());  //Converting a Map to a List of Keys
            System.out.println("KEYSET: " + keyset);
            System.out.println(mylist);
            String fieldname = mylist.get(0);   //Taking value from list
            String newvalue = mylist.get(1);
            Node node = requestbody.getElementsByTagName(fieldname).item(0);
            if (node != null) {
                node.setTextContent(newvalue);
                System.out.println("Field Updated Successfully");
            } else {
                System.out.println("Field" + field + "not found in XML Document ");
            }

        });
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

    @Then("Read value from the xml")
    public void read_value_from_the_xml() throws ParserConfigurationException, IOException, SAXException {

        String responseBody = response.getBody().asString();
        InputStream inputStream = new ByteArrayInputStream(responseBody.getBytes());

        DocumentBuilderFactory dbf
                = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputStream);

        NodeList nodeList
                = doc.getElementsByTagName("m:CapitalCityResult");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            // Check if the node is an element node
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Print the node details (you can customize this as needed)
                System.out.println("Node Name: " + node.getNodeName());
                System.out.println("Node Value: " + node.getTextContent());
            }

        }
        }}
