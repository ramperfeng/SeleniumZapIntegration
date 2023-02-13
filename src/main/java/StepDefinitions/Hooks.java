package StepDefinitions;

import Libraries.TestContext;
import Libraries.Utilities;
import io.cucumber.java8.En;
import io.cucumber.java8.Scenario;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApiException;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Hooks extends Utilities implements En {


    public static FileInputStream FIS=null;
    public static FileInputStream ErrorProp=null;
    public static FileInputStream ENDPointProp=null;
    public static Properties config1=null;
    public static Scenario myScenario;
    Properties properties = new Properties();
    public Hooks(TestContext testContext) {
        super(testContext);



        Before((Scenario scenario) -> {
            InputStream input = new FileInputStream("src/main/resources/test.properties");

            properties.load(input);
            System.out.println("list of pp" + properties);
            if (properties.getProperty("execution").equalsIgnoreCase("WEB")) {
               
                Setup(properties.getProperty("execution")==null ? "chrome" : properties.getProperty("browser"));

            }
            myScenario = scenario;
            System.out.println("**************global property files printed -------- ");

            FIS = new FileInputStream(properties.getProperty("SeleniumZapIntegration\\src\\main\\java\\config\\GlobalConfig.properties"));
            System.out.println("global property files printed -------- ");
            //ErrorProp = new FileInputStream("SeleniumZapIntegration\\src\\test\\resources\\Application-props\\error.properties");
            //ENDPointProp = new FileInputStream("SeleniumZapIntegration\\src\\test\\resources\\Application-props\\apiEndPoints.properties");
            config1 = new Properties();
            config1.load(FIS);
            config1.load(ErrorProp);
            config1.load(ENDPointProp);
            failSafePropertyGeneration();


        });
        After((Scenario scenario) -> {
            System.out.println("We are in after scenario methos and before generating Allure report");
        
            if (properties.getProperty("execution").equalsIgnoreCase("WEB")) {
                if (scenario.isFailed()) {
                    writeToLog("Current Page URL is " + getDriver().getCurrentUrl());
                    fnScreenshot(getDriver());

                    tearDown();
                }
            }
            if (properties.getProperty("execution").equalsIgnoreCase("WEB")) {
                tearDown();
            }
        });


    }
    public static void failSafePropertyGeneration ()
    {

        try {
            for (Object prop : config1.keySet()) {
                if (System.getenv(prop.toString()) != null) {
                    System.setProperty(prop.toString().trim().toUpperCase(), System.getenv(prop.toString()));
                } else {
                    System.setProperty(prop.toString().trim().toUpperCase(), config1.getProperty(prop.toString()));
                }
            }
        } catch (Exception e) {
            writeToLog("Error Occured Inside failSafePropertyGenaeration block in preRun,Error Description =" + e.getMessage());
        }
    }
    
}
