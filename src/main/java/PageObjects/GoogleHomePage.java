package PageObjects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * created by kallepuk on 10/08/2017
 */
public class GoogleHomePage {
   

    @FindBy(how = How.LINK_TEXT, using = "Sign in")
    public WebElement signIn;
    
     @FindBy(how = How.LINK_TEXT, using = "Gmail")
    public WebElement gmail;

      @FindBy(how = How.NAME, using = "q")
    public WebElement searchEdit;
      
    @FindBy(how = How.XPATH, using = "//input[@value='Google Search']")
    public WebElement searchButton;

    @FindBy(how = How.CSS, using = "div.hdtb-mitem.hdtb-msel span")
    public WebElement allResults;

    @FindBy(how = How.XPATH, using = "//div[@class='g']//a")
    public List<WebElement> resultLinks;

    public void Login(String s){
        searchEdit.sendKeys(s);
    }

   }



