package Libraries;

import PageObjects.GoogleHomePage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.fail;

public class DriverFactory {

    private static WebDriver driver;
    private static ClientApi api;
    static protected GoogleHomePage googleHomePage;
    static final String ZAP_PROXY_ADDRESS = "172.17.0.2";
    static final int ZAP_PROXY_PORT = 8080;
    static final String ZAP_API_KEY = null;
    Properties properties = new Properties();
    //static final String ZAP_API_KEY="55onol68of2jh14qugt9tmpmu";


    protected void Setup(String browser) {
        InitiateDriver(browser);

        InitializePages();
        //return driver;
    }

   

    private void InitiateDriver(String browser) {
        //security();
        String proxyServerUrl=ZAP_PROXY_ADDRESS+":"+ZAP_PROXY_PORT;
        Proxy proxy=new Proxy();
        proxy.setHttpProxy(proxyServerUrl);
        proxy.setSslProxy(proxyServerUrl);
        DesiredCapabilities caps = new DesiredCapabilities();

        switch (browser) {
            case "chrome":
                ChromeOptions co = new ChromeOptions();
                co.addArguments("headless", "window-size=1920,1080");
                co.setAcceptInsecureCerts(true);
                co.setProxy(proxy);               
                driver = new ChromeDriver(co);
                api = new ClientApi(ZAP_PROXY_ADDRESS, ZAP_PROXY_PORT, ZAP_API_KEY);

                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                fail("Unknown browser");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public WebDriver getPage(String url) {
        driver.get(url);
        return driver;
    }

    public void InitializePages() {
        googleHomePage = PageFactory.initElements(driver, GoogleHomePage.class);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageURL() {
        return driver.getCurrentUrl();
    }

    public void tearDown() throws ClientApiException, IOException {
        if (api != null) {
        
            String title="Test Security Report";
            String template="traditional-html";
            String description="This is NZ POST Security test report";
            String reportFileName="final-report.html";
            String targetFolder=System.getProperty("user.dir");
            try{

                ApiResponse response=api.reports.generate(title,template,null,description,null,null,null,null,null,
                        reportFileName,null,targetFolder,null);
                System.out.println("ZAP Report generated at this location  :"+response.toString());
            }
            catch (ClientApiException e)
            {
                e.printStackTrace();
            }

        }
           
            driver.quit();

        }

    }

