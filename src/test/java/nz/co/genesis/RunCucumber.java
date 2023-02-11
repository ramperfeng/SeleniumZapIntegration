package nz.co.genesis;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "StepDefinitions",
        // plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","json:target/cucumber/cucumber.json","html:target/reports.html"},
        plugin ={"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/cucumber/cucumber.json","html:target/reports.html"},
        //plugin ={"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        publish = true,
        tags = "@Home11"
)
public class RunCucumber {
}