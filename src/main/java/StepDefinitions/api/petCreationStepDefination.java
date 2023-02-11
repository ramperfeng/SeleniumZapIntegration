package StepDefinitions.api;
import Libraries.TestContext;
import Libraries.Utilities;
import StepDefinitions.Hooks;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Random;
import utilities.ExcelReader;
import utilities.RestService;
import utilities.Helper;


public class petCreationStepDefination extends Utilities implements En {
    String API_NAME = "petCreation";
    private ThreadLocal<String> PetID = new ThreadLocal<>();
    private ThreadLocal<String> petCreationPayload = new ThreadLocal<>();
    private ThreadLocal<RestService> weatherService = new ThreadLocal<RestService>() {
        public RestService initialValue() {
            return new RestService();
        }
    };
    public Response result;
    public Helper restHelper = new Helper();

    public petCreationStepDefination(TestContext testContext) {
        super(testContext);
        Given("User provided valid pet details {string} for petCreation", (String payload) -> {
            String inputPayload = ExcelReader.readXlsJONFile(payload, API_NAME, "DATA");
             inputPayload=ExcelReader.jsonFormat(inputPayload);
            Random rand = new Random();
            String id = String.format("%04d", rand.nextInt(10000));
            PetID.set(id);
            petCreationPayload.set(inputPayload.replace("creatid", PetID.get()));
            if ((payload.contains("FieldMissing")) == true) {
                ExcelReader.mandetroryorEmptyFieldValidate.set("N");
            }

        });


        When("User request the petCreation endpoint with {string}", (String payload) -> {
            System.out.println(" We are in When Method ");
            HashMap<String, String> weatherHeaders = weatherService.get().setDefaultHeaders();
            String endpoint = System.getProperty("PETCREATE");
            Hooks.writeToLog("Actual Endpoint requested :>>>>>." + endpoint);
            result=restHelper.PostMessageByMessageBody(endpoint, petCreationPayload.get(), weatherHeaders);
            Hooks.writeToLog("Server Response :"+result.getBody().asString());
                  });

        Then("petCreation end point expected {string} as success response", (String responsePayload) -> {
            weatherService.get().verifySuccessResponseStatusCode(result);
            System.out.println("Actual Response From Server ######  " + result.getBody().asString());
            System.out.println("Actual Response From Server $$$$$$  " + result.getBody().toString());
            writeToLog("Actual Response code Server  " + result.statusCode());
           // ExcelReader.PayloadComparisonJsonKeys(result.getBody().toString(),responsePayload);
            ExcelReader.readMultipleRecordsXlsJSONFile(responsePayload, API_NAME, "RESPONSE", result.getBody().asString());

        });
    }
}
