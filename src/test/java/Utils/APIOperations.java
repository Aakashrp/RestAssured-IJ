package Utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class APIOperations {


    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder().setBaseUri("https://reqres.in/").addHeader("Content-type", "application/json").setContentType("text/xml; charset=utf-8").build();
    }

    public static ResponseSpecification getResponseSpec() {

        return new ResponseSpecBuilder().build();
    }

    public static String getProperties(String xpath) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("E:\\My Projects\\All Projects\\API Testing\\src\\test\\java\\Utils\\Xpath.properties");
        try {
            prop.load(fis);
        } finally {
            fis.close();
        }
        return prop.getProperty(xpath);
    }
}
