package oAuth_2;


import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static void main(String[] args) {

//        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//        String url = driver.getCurrentUrl();
//        String partialCode = url.split("code=")[1];
//        String code = partialCode.split("&scope")[0];
//        System.out.println(code);

        String directCode = "4%2F0AWtgzh6re4Hx4jSYEQ-LH_KW7alFVazOG5ssZ69b7Gzl5fPyJ5O6kRZPnUdLyiq-7z4wgQ";

        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParam("code", directCode)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");

        GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

        System.out.println("\n\n" + gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getExpertise());
        System.out.println(gc.getUrl());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        ArrayList actualList = new ArrayList<>();


        //Assignment of rahulshetty
        for (WebAutomation list : gc.getCourses().getWebAutomation()) {
            System.out.println(list.getCourseTitle());
            actualList.add(list.getCourseTitle());
        }

        List expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(actualList.equals(expectedList));

        // System.out.println("Response: " + response);

        
    }
}
