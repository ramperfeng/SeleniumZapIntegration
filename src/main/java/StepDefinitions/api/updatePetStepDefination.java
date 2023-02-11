package StepDefinitions.api;

import Libraries.TestContext;
import Libraries.Utilities;
import StepDefinitions.Hooks;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Random;
import org.junit.Assert;
import utilities.ExcelReader;
import utilities.RestService;
import utilities.Helper;

public class updatePetStepDefination extends Utilities implements En  {

    String API_NAME = "petCreation";
    String API_NAME1 = "samplePet";
    String API_NAME2 = "updatePet";
    private ThreadLocal<String> PetID = new ThreadLocal<>();
    private ThreadLocal<String> updatedPetName = new ThreadLocal<>();
    private ThreadLocal<String> petCreationPayload = new ThreadLocal<>();
    private ThreadLocal<String> petUpdatePayload = new ThreadLocal<>();
    private ThreadLocal<RestService> weatherService = new ThreadLocal<RestService>() {
        public RestService initialValue() {
            return new RestService();
        }
    };
    private ThreadLocal<String> resourceReqPayload = new ThreadLocal<>();
    public Response result;
    public Helper restHelper = new Helper();


    public static String  end = System.getProperty("URI");

    public updatePetStepDefination(TestContext testContext) {
        super(testContext);
        Given ("create a pet based on as per details provided by user {string} for petCreation", (String payload) -> {
            String  inputPayload=ExcelReader.readXlsJONFile(payload,API_NAME,"DATA");
            inputPayload=ExcelReader.jsonFormat(inputPayload);
            Random rand = new Random();
            String id = String.format("%04d", rand.nextInt(10000));
            PetID.set(id);
            petCreationPayload.set(inputPayload.replace("creatid",PetID.get()));
            System.out.println("Request payload ---------"+petCreationPayload.get());
            Hooks.writeToLog("Resource Request Payload : "+petCreationPayload.get());
            if((payload.contains("FieldMissing"))==true)
            {
                ExcelReader.mandetroryorEmptyFieldValidate.set("N");
            }
            HashMap<String, String> weatherHeaders = weatherService.get().setDefaultHeaders();
            String endpoint = System.getProperty("PETCREATE");
            System.out.println("Actual Endpoint requested :>>>>>."+endpoint);
            result = restHelper.PostMessageByMessageBody(endpoint, petCreationPayload.get(),weatherHeaders);
            });
        Given ("create endpoint api return the success as {string} as response", (String responsePayload) -> {
            weatherService.get().verifySuccessResponseStatusCode(result);
            System.out.println("Actual Response from the Server :"+result.getBody().asString());
            System.out.println("Actual Pet from the Request>>>>>>>:"+PetID.get());
            Assert.assertTrue(result.getBody().asString().contains(PetID.get()));
            writeToLog("Actual Response Status code " + result.getStatusCode());
            writeToLog("Actual Response From Server  " + result.getBody().asString());
            //ExcelReader.readMultipleRecordsXlsJSONFile(responsePayload, API_NAME, "RESPONSE", result.getBody().asString());
        });
        Given ("User able to retrieve the pet details using petId", () -> {
            System.out.println("pet ID details "+PetID.get());
            HashMap<String, String> weatherHeaders = weatherService.get().setDefaultHeaders();
            String getEndpoint=weatherService.get().getPetIDDetails(PetID.get());
            result = restHelper.GetMesaage(getEndpoint, weatherHeaders);
            writeToLog("Actual response for get endpoint :>>>>>."+ result.getBody().asString());
        });

        Given ("get endpoint api return the response based on petID", () -> {

            weatherService.get().verifySuccessResponseStatusCode(result);
            Assert.assertTrue(result.getBody().asString().contains(PetID.get()));

        });
        Given ("user able to update pet details for as per requirement {string} for update", (String payload ) -> {

            String inputPayload = ExcelReader.readXlsJONFile(payload, API_NAME2, "DATA");
            inputPayload = ExcelReader.jsonFormat(inputPayload);
            petUpdatePayload.set(inputPayload.replace("creatid", PetID.get()));
            System.out.println("Request payload for update endpoint  ---------" + petUpdatePayload.get());
            writeToLog("Resource Request Payload : " + petUpdatePayload.get());
            if ((payload.contains("FieldMissing")) == true) {
                ExcelReader.mandetroryorEmptyFieldValidate.set("N");
            }
        });
        When ("user request the pet update details endpoint with {string}", (String payload ) -> {
            HashMap<String, String> weatherHeaders = weatherService.get().setDefaultHeaders();
            String updateEndpoint=System.getProperty("UPDATE_ENDPOINT");
            System.out.println("Actual Endpoint requested :>>>>>."+updateEndpoint);
            result = restHelper.PUTMessageByMessageBody(updateEndpoint, petUpdatePayload.get(),weatherHeaders);

        });
        Then ("update details endpoint api return expected {string} as response", (String responsePayload ) -> {
            weatherService.get().verifySuccessResponseStatusCode(result);
            Assert.assertTrue(result.getBody().asString().contains(PetID.get()));

        });
        Then ("get endpoint api return the updated response based on petID", () -> {
            weatherService.get().verifySuccessResponseStatusCode(result);
            HashMap<String, String> weatherHeaders = weatherService.get().setDefaultHeaders();
            String getEndpoint=weatherService.get().getPetIDDetails(PetID.get());
            result = restHelper.GetMesaage(getEndpoint, weatherHeaders);
            try{
                Assert.assertTrue(result.getBody().asString().contains(PetID.get()));
                System.out.println("petDetails returned by server :" + PetID.get());
            }catch(AssertionError e){
                System.out.println("petDetails not returned by server :" + PetID.get());
                throw e;
            }
            //Assert.assertTrue(result.getBody().asString().contains(PetID.get()));
            try{
                Assert.assertTrue(result.getBody().asString().contains("UpdatedPetDetails"));
                System.out.println("updated petDetails returned by server :" + "UpdatedPetDetails");
            }catch(AssertionError e){
                System.out.println("updated petDetails not returned by server :" + "UpdatedPetDetails");
                throw e;
            }
            // Assert.assertTrue(result.getBody().asString().contains("UpdatedPetDetails1"));
        });


           }

    }

