import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) throws IOException {

        //validate if Add Place API working as expected

        //given - All input details
        //when - Submit the API - resource,http method
        //then - Validate the response
        //content of the file to String -> content of file can convert into Byte -> Byte data to String

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payloads.AddPlace())
                // .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\ASUS\\Desktop\\addPlace.json"))))
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response); //for parsing Json
        String placeId = js.getString("place_id");

        System.out.println(placeId);

        //Update Place
        String newAddress = "Karaman,Turkey";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);

        //TestNG Assertion
        Assert.assertEquals(actualAddress, newAddress);


    }

}
