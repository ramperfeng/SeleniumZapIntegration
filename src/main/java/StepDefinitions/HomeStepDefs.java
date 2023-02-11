package StepDefinitions;

import Libraries.TestContext;
import Libraries.Utilities;
import io.cucumber.java8.En;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
/**
 * created by kallepuk on 11/20/2022
 */
public class HomeStepDefs extends Utilities implements En {

    public HomeStepDefs(TestContext testContext) {
        super(testContext);

        Then("^I should see sign-in in header section right$",() -> assertTrue("Move Work Station tile not displayed", isElementDisplayed(googleHomePage.signIn)));

        Then("^I search for \"([^\"]*)\"$" ,(String arg1)->{
            enterTextInEdit(googleHomePage.searchEdit, arg1);
            clickElement(googleHomePage.searchButton);
        });

        Then("^I should see \"([^\"]*)\" results$", (String arg0) -> {
            System.out.println(getScenarioContext().getContext("abc"));
            waitForElementVisibility(googleHomePage.allResults);
//            verifyAttributeValue(googleHomePage.allResults, "title", "welcome to Google");
            assertThat("String Not Found", googleHomePage.resultLinks.get(0).getText(), is(containsString(arg0)));
            fnScreenshot(getDriver());
        });
        Given("^I am navigate to \"([^\"]*)\"$", (String arg0) -> {
            getPage(arg0);
            waitForElementVisibility(googleHomePage.signIn);
           getScenarioContext().setContext("abc","Welcome to dependency injection");
        });
        Given("^create a pet based on as per details provided by user \"([^\"]*)\" for petCreation$", (String arg0) -> {
        });
        And("^create endpoint api return the success as \"([^\"]*)\" as response$", (String arg0) -> {
        });
        And("^User able to retrieve the pet details using petId$", () -> {
        });
        And("^get endpoint api return the response based on petID$", () -> {
        });
        And("^user able to update pet details for as per requirement \"([^\"]*)\" for update$", (String arg0) -> {
        });
        When("^user request the pet update details endpoint with \"([^\"]*)\"$", (String arg0) -> {
        });
        Then("^update details endpoint api return expected \"([^\"]*)\" as response$", (String arg0) -> {
        });
        Then("^get endpoint api return the updated response based on petID$", () -> {
        });

    }

}
