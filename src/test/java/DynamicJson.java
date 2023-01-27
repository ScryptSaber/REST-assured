import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().header("Content-Type", "application/json")
                .body(Payloads.addBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath json = ReusableMethods.rawToJson(response);
        String id = json.get("ID");
        System.out.println("ID: " + id);
        String msg = json.get("Msg");
        System.out.println(msg + "\n");

        //deleteBook.


    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        //array= collection of elements
        //multidimensional array= collection of arrays.

        return new Object[][]{{"saweqf", "51349"}, {"csdsd", "55534"}, {"cswuud", "55504"}};
    }
}

