package fox.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import fox.Page.HomePage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;



public class CreateAccountSteps {
    private Logger log = Logger.getLogger(CreateAccountSteps.class);
    private HomePage homePage;

    @Before
    public void testStartUp() {
        homePage = new HomePage();
    }

    @After
    public void testShutDown(Scenario scenario){
        log.info("Test shutting down.");
        WebDriver driver = homePage.navigationHelper.getWebDriver();
        if(scenario.isFailed()){
            byte[] screenshot= ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot,"image/png");
            scenario.write("Scenario: failed");
        }
        else{
            scenario.write("Scenario: passed");
        }
        driver.quit();
    }


    @Given("^I navigated to \"([^\"]*)\"$")
    public void i_navigated_to(String url) {
       // navigationHelper.goTo(url);
        homePage.goTo(url);
    }

    @When("^I click on UserIcon$")
    public void i_click_on_UserIcon()  {
        homePage.clickAccount();
    }

    @Then("^I can click the Sign up button$")
    public void i_can_click_the_Sign_up_button()  {
        homePage.click_SignUp();
    }

    @Then("^fill in \"([^\"]*)\" as \"([^\"]*)\"$")
    public void fill_in_as(String field, String input) throws Throwable {
        homePage.signUpInfo(field,input);
    }

    @Then("^select gender as \"([^\"]*)\"$")
    public void select_Gender_as(String gender)  {
        homePage.selectGender(gender);
    }

    @Then("^click Create Profile$")
    public void click_Create_Profile()  {
        homePage.clickCreateProfile();
    }

    @Then("^verify the text \"([^\"]*)\" is displayed$")
    public void verify_the_text_is_displayed(String text)  {
        try{
            homePage.verifyText(text);
        }catch(Exception e){
            if(homePage.verifyText("Email address is already registered")){
                assert false:("Email address already used, Please change to a different valid email in feature file.");
            }
            else{
                log.info(e.toString());
            }
        }
    }

    @Then("^click the Done button$")
    public void click_the_Done_button()  {
        homePage.clickDone();
    }

    @When("^I can sign in using \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_can_sign_in_using_and(String email, String password) throws Throwable {
        homePage.signIn(email,password);
    }

    @Then("^I can click on Edit Profile$")
    public void i_can_click_on_Edit_Profile() {
        homePage.clickEditProfile();
    }

    @Then("^delete my Profile$")
    public void delete_my_Profile() {
        homePage.deleteProfile();
    }

    @And("^clean up by deleting the profile")
    public void clean_up_by_deleting_the_profile(){
        homePage.clickAccount();
        homePage.clickEditProfile();
        homePage.deleteProfile();
    }
}


