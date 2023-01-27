import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {
    @Test
    public void sumOfCourses() {
        JsonPath json = new JsonPath(Payloads.CoursePrice());

        int count = json.getInt("courses.size()");
        int sum = 0;
        for (int i = 0; i < count; i++) {
            int price = json.get("courses[" + i + "].price");
            int copies = json.get("courses[" + i + "].copies");
            int amount = price * copies;
            sum += amount;

            System.out.println("Amount of " + i + ".index" + ": " + amount);
        }
        System.out.println("Total sum: " + sum);

        int purchaceAmount = json.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaceAmount);

    }
}
