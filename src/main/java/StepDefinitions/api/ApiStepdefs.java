package StepDefinitions.api;

import Libraries.TestContext;
import Libraries.Utilities;
import io.cucumber.java8.En;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ApiStepdefs extends Utilities implements En {

    private static final String USERNAME = "TOOLSQA-Test";
    private static final String PASSWORD = "Test@@123";
    private static final String USER_ID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
    private static String token;
    private static Response response;
    private static String jsonString;
    private static String bookId;
    public ApiStepdefs(TestContext testContext) {
        super(testContext);
        Given("I am an authorized user", () ->{
                getRequest().header("Content-Type", "application/json");
                response = getRequest().body("{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
                        .post("/Account/v1/GenerateToken");

                String jsonString = response.asString();
                token = JsonPath.from(jsonString).get("token");
        });
        Given("A list of books are available", () ->{
                response = getRequest().get("/BookStore/v1/Books");

                jsonString = response.asString();
                List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
                Assert.assertTrue(books.size() > 0);
            System.out.println(getScenarioContext().getContext("abc"));

                bookId = books.get(0).get("isbn");
        });

        When("I add a book to my reading list",() -> {

                getRequest().header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json");

                response = getRequest().body("{ \"userId\": \"" + USER_ID + "\", " +
                                "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}")
                        .post("/BookStore/v1/Books");
        });

        Then("The book is added",() ->{
                Assert.assertEquals(201, response.getStatusCode());
        });

        When("I remove a book from my reading list", () ->{

                getRequest().header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json");

                response = getRequest().body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + USER_ID + "\"}")
                        .delete("/BookStore/v1/Book");


        });

        Then("The book is removed",() ->{
                getRequest().header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json");

                response = getRequest().get("/Account/v1/User/" + USER_ID);
                Assert.assertEquals(200, response.getStatusCode());

                jsonString = response.asString();
                List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("books");
                Assert.assertEquals(0, booksOfUser.size());
        });
        }
    }
