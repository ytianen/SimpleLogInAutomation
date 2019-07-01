package fox.helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NavigationHelper {

    private WebDriver driver;
    private Logger log = Logger.getLogger(NavigationHelper.class);
    public WebDriver getWebDriver(){
        return this.driver;
    }

    public NavigationHelper(){
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            System.setProperty("webdriver.gecko.driver","src/test/resources/drivers/geckodriver.exe");
        }
        else{
            System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver");
        }
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        driver = new FirefoxDriver();
        log.info("Starting Firefox Driver");
    }

    public void goTo(String url){
        assert url!=null:"URL can not be null";
        log.info("Navigating to: "+url);
        driver.get(url);
    }
    public void clickElement(By locator){
        log.debug("Click Element"+locator.toString());
        driver.findElement(locator).click();
    }

    public void input(By locator, String input){
        log.info("At Element:"+locator.toString()+". Input Value:" + input);
        driver.findElement(locator).sendKeys(input);
    }

    public boolean isElementPresent(By locator){

        log.debug("Looking for "+locator.toString());
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
            return element!=null;
    }

    public void clickElementText(String text){
        String ElementXPath = "//*[contains(text(),'"+text+"')]";
        if(isElementPresent(By.xpath(ElementXPath)))
        {
            log.info("Element found with Text:"+text);
            driver.findElement(By.xpath(ElementXPath)).click();
        }
        else{
            log.info("Element not found");
        }
    }

    public void retryClickUntilElementAppear(By clickLocator, By expectedLocator){
        int retry = 3;
        while (retry > 0||!isElementPresent(expectedLocator)){
            clickElement(clickLocator);
            retry--;
        }
    }
}
