package fox.helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;
import java.util.ArrayList;

import static java.time.Duration.ofSeconds;


public class NavigationHelper {

    private static WebDriver driver;
    private static Logger log = Logger.getLogger(NavigationHelper.class);
    private static Wait <WebDriver> wait = null;
    private static Wait <WebDriver> elementExistsWait = null;
    // Default wait time
    private static final int WAIT_TIME = 60;

    // Default poll interval
    private static final int POLL_INTERVAL = 1;

    // Wait time to check if an element exists
    private static final int ELEMENT_EXISTS_WAIT_TIME = 5;

    public static WebDriver getWebDriver(){
        return driver;
    }

    public static void init(){

        //Setting driver path by OS
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

        //Setting wait
        wait = new FluentWait <>(driver)
                .withTimeout(ofSeconds(WAIT_TIME))
                .pollingEvery(ofSeconds(POLL_INTERVAL))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);

        elementExistsWait = new FluentWait <>(driver)
                .withTimeout(ofSeconds(ELEMENT_EXISTS_WAIT_TIME))
                .pollingEvery(ofSeconds(POLL_INTERVAL))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
    }

    public static void goTo(String url){
        assert url!=null:"URL can not be null";
        log.info("Navigating to: "+url);
        driver.get(url);
    }

    public static void clickElement(By locator) {
        waitForElemToBeClickable(locator);
        try {
            log.debug("Click Element: " + locator.toString());
            driver.findElement(locator).click();
        } catch (NullPointerException e) {
            log.error("CAN NOT CLICK NULL ELEMENT!!!!" + e.getMessage());
        }
    }

    public static void clickElementUsingJavaScript(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
        } catch (NullPointerException e) {
            log.error("CAN NOT CLICK NULL ELEMENT!!!!" + e.getMessage());
        }
    }

    public static void selectElementValue(By locator, String value) {
        log.debug("Select Element:" + locator.toString());
        Select dropdown = new Select(driver.findElement(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        dropdown.selectByValue(value);
    }

    public static void inputText(By locator, String input) {
        log.debug("At Element:" + locator.toString() + ". Input Value:" + input);
        waitForVisibilityOfElemLocated(locator);
        driver.findElement(locator).sendKeys(input);
    }

    public static boolean isElementPresent(By locator) {
        log.debug("Looking for " + locator.toString());
        WebElement element = elementExistsWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element != null;
    }

    public static boolean isElementClickable(By locator) {
        WebElement element = elementExistsWait.until(ExpectedConditions.elementToBeClickable(locator));
        return element != null;
    }

    public static boolean isElementVisible(By locator) {
        try {
            WebElement element = elementExistsWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.debug(locator.toString() + " is Visible");
            if (element != null) {
                return true;
            }
        } catch (TimeoutException e) {
            log.debug(e.getMessage());
            // log.debug("Cant locate: " + locator.toString());
        }
        return false;
    }

    public static void scrollIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void clickElementText(String text) {
        String ElementXPath = "//*[contains(text(),'" + text + "')]";
        if (isElementPresent(By.xpath(ElementXPath))) {
            log.debug("Element found with Text:" + text);
            driver.findElement(By.xpath(ElementXPath)).click();
        } else {
            log.debug("Element not found");
        }
    }

    public static void retryClickUntilElementAppear(By clickLocator, By expectedLocator) {
        int retry = 3;

        if (isElementVisible(expectedLocator)) {
            log.info("Element is visible!!");
        } else {
            //clickElementUsingJavaScript(clickLocator);
            //log.info("clicked " + clickLocator);
            while (retry > 0) {
                clickElement(clickLocator);
                log.info("click " + clickLocator + " attempt remain: " + retry);
                try {
                    waitForVisibilityOfElemLocated(expectedLocator);
                    retry = 0;
                } catch (TimeoutException e) {
                    retry--;
                }
            }
        }
    }

    public static void retryClickUntilElementDisappear(By clickLocator, By expectedLocator) {
        int retry = 3;

        if (!isElementVisible(expectedLocator)) {
            log.info("Element is invisible!!");
        } else {
            //clickElementUsingJavaScript(clickLocator);
            //log.info("clicked " + clickLocator);
            while (retry > 0) {
                clickElement(clickLocator);
                log.info("click " + clickLocator + " attempt remain: " + retry);
                try {
                    if(!isElementVisible(expectedLocator)) {
                        retry = 0;
                    }
                } catch (TimeoutException e) {
                    waitForPageLoad(ELEMENT_EXISTS_WAIT_TIME);
                    retry--;
                }
            }
        }
    }


    public static WebElement waitForVisibilityOfElemLocated(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    public static WebElement waitForElemToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageLoad(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
    }

    public static void waitUntilElementInvisible(By locator) {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitUntilElementInvisible(WebElement element) {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitUntilElementInvisible(By locator, int sec) {
        Wait<WebDriver> TempWait = new FluentWait<>(driver)
                .withTimeout(ofSeconds(sec))
                .pollingEvery(ofSeconds(sec / 10));
        TempWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static String getElementText(By locator) {
        waitForVisibilityOfElemLocated(locator);
        return driver.findElement(locator).getText();
    }

    public static boolean validate(boolean condition, String Flag) {
        log.info("Validation for: " + Flag);
        try {
            assert condition;
            return true;
        } catch (AssertionError e) {
            log.warn(Flag + ":" + e.getMessage());
        }
        return false;
    }

    public static void clearInputField(By locator) {
        driver.findElement(locator).clear();
    }

    public static void handleAlertAccept() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertMessage = driver.switchTo().alert().getText();
            log.info(alertMessage);
            alert.accept();
        } catch (NoAlertPresentException a) {
            log.info("no alert active");
        }
    }

    private static String MainTab = null;

    public static String SwitchToNewTab(By locator) {
        MainTab = driver.getWindowHandle();
        clickElement(locator);
        waitForPageLoad(3);
        ArrayList<String> newTab = new ArrayList <>(driver.getWindowHandles());
        newTab.remove(MainTab);
        driver.switchTo().window(newTab.get(0));
        return driver.getCurrentUrl();
    }

    public static void SwitchToMainTab() {
        assert MainTab != null : "Main Tab is not saved.";
        try {
            driver.switchTo().window(MainTab);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public static void SwitchFrame(String iframeName) {
        driver.switchTo().defaultContent();
        driver.switchTo().frame(iframeName);
        log.info("switched to iframe:" + iframeName);
    }

    public static void SwitchTodefaultContent() {
        log.info("switched to defaultContent");
        driver.switchTo().defaultContent();

    }
}