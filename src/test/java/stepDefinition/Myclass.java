package stepDefinition;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;

public class Myclass {


    @Test
    public void testPlaceApiRequest() throws URISyntaxException, IOException, InterruptedException {
        // Given the API endpoint
        String apiEndpoint = "/api/users?page=2";
        String baseUrl = "https://reqres.in";
        URI uri = new URI(baseUrl + apiEndpoint);
        // When I place a "GET" request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        // Then status code should be 200
        assertEquals(200, response.statusCode());

//        // And validate the response
//        JSONObject jsonResponse = new JSONObject(response.body());
//        System.out.println("Response: " + jsonResponse.toString(4));
//
//        // And validate JSON schema from "filepath"
//        String schemaFilePath = "src/test/resources/json_schema.json"; // update the path to your schema file
//        String schemaContent;
//        try (Stream<String> stream = Files.lines(Paths.get(schemaFilePath))) {
//            schemaContent = stream.collect(Collectors.joining("\n"));
//        }
//
//        JSONObject jsonSchema = new JSONObject(new JSONTokener(schemaContent));
//        Schema schema = SchemaLoader.load(jsonSchema);
//        schema.validate(jsonResponse); // throws a ValidationException if this object is invalid
//    }
}

}
