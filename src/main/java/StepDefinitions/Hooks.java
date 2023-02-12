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
               // security(properties.getProperty("OS")==null ? "win" : properties.getProperty("OS"));
                Setup(properties.getProperty("execution")==null ? "chrome" : properties.getProperty("browser"));

            }
            myScenario = scenario;

            FIS = new FileInputStream("/src/main/java/config/GlobalConfig.properties");
            System.out.println("global property files printed -------- ");
            ErrorProp = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\config\\GlobalConfig.properties");
            ENDPointProp = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\test.properties");
            config1 = new Properties();
            config1.load(FIS);
            config1.load(ErrorProp);
            config1.load(ENDPointProp);
            failSafePropertyGeneration();


        });
        After((Scenario scenario) -> {
            System.out.println("We are in after scenario methos and before generating Allure report");
         //  generateAllurehtmlReport();
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
    public void generateAllurehtmlReport()
    {
        try
        {
           System.out.println("Print system environment  >>>>>>>>>:"+ System.getenv("ALLURE_HOME"));
           Thread.sleep(Long.parseLong("2000"));
            ProcessBuilder processBuilder= new ProcessBuilder(System.getenv("ALLURE_HOME") +"/bin/allure.bat","serve",System.getProperty("user.dir")+"/target/allure-results");
            //ProcessBuilder processBuilder= new ProcessBuilder("D:/allure-2.19.0/bin/allure.bat","serve","D:/TouchPoint/Cucumber-Templet/target/allure-results");
            processBuilder.redirectErrorStream(true);
            Process process=processBuilder.start();
            writeToLog("Allure Html Report generated Successfully");
            BufferedReader reader =new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            line=reader.readLine();
            while (line !=null)
            {
                System.out.println("taskList :"+ line);
                line=reader.readLine();
                if(line.contains("Server started at"))
                {
                    process.destroy();
                    break;
                }
            }

        }catch (Throwable e)
        {
            writeToLog("Error in generating Allure HTML report ,Error = "+ e.getMessage());

        }
    }
}
