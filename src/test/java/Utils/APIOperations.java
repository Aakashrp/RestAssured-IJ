package Utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import javax.swing.text.html.HTML;

public class APIOperations {



    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder().setBaseUri("https://reqres.in/").addHeader("Content-type", "application/json").build();
    }

    public  static ResponseSpecification getResponseSpec() {

        return new ResponseSpecBuilder().build();
    }


}
