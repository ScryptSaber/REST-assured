import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {

        JsonPath json = new JsonPath(Payloads.CoursePrice());

        //Print number of courses returned by API
        int count = json.getInt("courses.size()");
        System.out.println("Count of courses: " + count);

        //Print purchase amount
        int totalAmount = json.getInt("dashboard.purchaseAmount");
        System.out.println("Total Amount: " + totalAmount);

        //Print title of the first course
        String titleFirstCourse = json.get("courses[0].title");
        System.out.println("Title of the first course: " + titleFirstCourse);

        //Print all course titles and their respective prices
        System.out.println("\n*__All Courses and prices__*");
        for (int i = 0; i < count; i++) {
            String courseTitles = json.get("courses[" + i + "].title");
            int prices = json.get("courses[" + i + "].price");

            System.out.println(courseTitles);
            System.out.println(prices);
        }

        //Print no of copies sold by RPA Course
        String title="RPA";
        System.out.print("\nNumber of copies sold by RPA Course: ");
        for (int i = 0; i < count; i++) {
            String courseTitles = json.get("courses[" + i + "].title");
            if (courseTitles.equalsIgnoreCase(title)) {
                int copies = json.get("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }


    }
}
