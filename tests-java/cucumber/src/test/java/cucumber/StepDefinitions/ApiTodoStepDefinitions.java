package cucumber.StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;


public class ApiTodoStepDefinitions {

    private String path;
    private Response response;
    private boolean response_completed;
    public boolean completed;


    @Given("the URL endpont is valid")
    public void theUrlEndpontIsValid() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/todos/1";
    }

    @When("we request the first Todo details")
    public void createUser() {
        response = given().get().then().extract().response();
    }

    @Then("the response status code should be {int}")
    public void responseStatusCodeMustBeInteger(int code) {
        Assertions.assertEquals(code, response.getStatusCode());
    }

    @And("the response body must be valid JSON")
    public void responseBodyMustBeValidJson() {
        response.then().body(matchesJsonSchemaInClasspath("json-schemas/ApiTodoSchema.json"));
    }

    @And("the response property title must be {string}")
    public void responsePropertyTitleMustBeUserString(final String title) {
        String response_title = response.jsonPath().get("title");
        Assertions.assertEquals(title, response_title);
    }

    @And("the response property userId must be {int}")
    public void responsePropertyUserIdMustBeUserInteger(final int userId) {
        int response_userId = response.jsonPath().get("userId");
        Assertions.assertEquals(userId, response_userId);
    }

    @And("the response property id must be {int}")
    public void responsePropertyIdMustBeInteger(final int id) {
        int response_id = response.jsonPath().get("userId");
        Assertions.assertEquals(id, response_id);
    }

    @And("the response property completed value must be {string}")
    public void responsePropertyCompletedMustBeBoolean(String completed) {
        boolean expected = Boolean.parseBoolean(completed);
        this.response_completed = response.jsonPath().get("completed");
        Assertions.assertEquals(expected, this.response_completed);
    }

    @After
    public void responsePropertyCompletedShouldBeStored() {
        this.completed = this.response_completed;
    }
}
