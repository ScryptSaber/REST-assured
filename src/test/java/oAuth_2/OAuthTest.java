package oAuth_2;


import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static void main(String[] args) {
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWtgzh4LwU28mZM8moFITQjoCwe4q1NtVI7GHdoREm6GcqSrR9e5gk7fhYOKKheS_9eDpA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);


        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParam("code", "4%2F0AWtgzh4LwU28mZM8moFITQjoCwe4q1NtVI7GHdoREm6GcqSrR9e5gk7fhYOKKheS_9eDpA")
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");

        String response = given().queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println("Response: " + response);


    }
}
