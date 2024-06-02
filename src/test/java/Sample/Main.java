package Sample;

import Utils.APIOperations;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;

public class Main {


    public static void main(String[] args) {

        RestAssured.baseURI = "https://mocktarget.apigee.net";

        RequestSpecification req = APIOperations.getRequestSpec();
        ResponseSpecification res = APIOperations.getResponseSpec();

        RequestSpecification r = given().spec(req).log().all();

        Response rs = r.when().get("https://mocktarget.apigee.net/xml").then().spec(res).extract().response();

        String resp=rs.asString();
        System.out.println(resp);

        rs.then().statusCode(200);
        rs.then().body("root.city",equalTo("San Jose"));

        rs.then().body(hasXPath("/root/firstName", equalTo("John")));

        //Validating XML
        XmlPath xp = rs.xmlPath();
        String name = xp.getString("root.firstName");
        System.out.println(name);

        String lastName = xp.getString("root.lastName");
        System.out.println(lastName);

        String city = xp.getString("root.city");
        System.out.println(city);

        //We can get count no of users
        List<String> Users=xp.getList("root");
        Assert.assertEquals(Users.size(),1);
        System.out.println("Size of List or Users is : " + Users.size());

        //Example there is list of users in response XML u can get all name values in list and use for each to print all OR u can check if that name is present or not



    }
}
