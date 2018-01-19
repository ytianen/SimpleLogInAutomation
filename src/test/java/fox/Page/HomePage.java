package fox.Page;

import fox.helpers.NavigationHelper;
import org.openqa.selenium.By;


public class HomePage {
    public NavigationHelper navigationHelper = new NavigationHelper();
    //Home Page Navigation locator
    private By UserIconAccount_Button_Xpath =By.xpath("//a[@href='/account']");
    private By SignUp_Button_Xpath = By.xpath("//button[text()='Sign Up']");
    //Sign Up Locators
    private By FirstName_Input_Name = By.name("signupFirstName");
    private By LastName_Input_Name = By.name("signupLastName");
    private By Email_Input_Name= By.name("signupEmail");
    private By Password_Input_Name= By.name("signupPassword");
    private By Birthdate_Input_Xpath = By.xpath("//*[contains(@placeholder,'Birthdate')]");
    private By Gender_Dropdown_Xpath= By.xpath("//a[text()='Gender']");
    private By CreateProfile_Button_Xpath= By.xpath("//div[@class='Account_signupButtonDesktop_1PCXs']/button");
    private By Done_Button_Xpath= By.xpath("//button[contains(text(),'Done')]");

    //Sign In Locators
    private By SignIn_Button_Xpath= By.xpath("//button[text()='Sign In']");
    private By SignInEmail_Input_Name = By.name("signinEmail");
    private By SignInPassword_Input_Name = By.name("signinPassword");
    private By SignInConfirm_Button_Xpath=By.xpath("//div[@class='Account_signinButtonDesktop_2SO1g']/button");

    //Account Management
    private By EditProfile_Button_Xpath = By.xpath("//a[text()='Edit Profile']");
    private By DeleteProfile_Button_Xpath = By.xpath("//a[text()='Delete Profile']");
    private By ConfirmDeleteProfile_Button_Xpath = By.xpath("//button[text()='Delete Profile']");

    public void goTo(String url){
        navigationHelper.goTo(url);
        navigationHelper.isElementPresent(UserIconAccount_Button_Xpath);
    }

    public void clickAccount(){
        navigationHelper.clickElement(UserIconAccount_Button_Xpath);
    }

    public void click_SignUp(){
        navigationHelper.clickElement(SignUp_Button_Xpath);
    }

    public void signUpInfo(String field, String input){
        assert field!=null:"Field is null";
        assert input!=null:"Input can not be null for" + field;

        //reformat field to lowercase
        field=field.toLowerCase().trim();

        if(field.equals("lastname"))
        {
            navigationHelper.input(LastName_Input_Name,input);
        }
        else if(field.equals("firstname"))
        {
            navigationHelper.input(FirstName_Input_Name,input);
        }
        else if(field.equals("email"))
        {
            navigationHelper.input(Email_Input_Name,input);
        }
        else if(field.equals("password")){
            navigationHelper.input(Password_Input_Name,input);
        }
        else if(field.equals("birthdate")){
            navigationHelper.input(Birthdate_Input_Xpath,input);
        }
        else{
            System.out.print("===ERROR===:No valid field is found.");
        }
    }

    public void selectGender(String Gender){
        assert Gender.equals("Male")||Gender.equals("Female")||Gender.equals("Prefer not to say"):"Invalid Input for Gender";
        navigationHelper.clickElement(Gender_Dropdown_Xpath);
        navigationHelper.clickElementText(Gender);
    }

    public void clickCreateProfile(){
        navigationHelper.clickElement(CreateProfile_Button_Xpath);
    }

    public boolean verifyText(String text){
       return navigationHelper.isElementPresent(By.xpath("//*[contains(text(),'"+text+"')]"));
    }

    public void clickDone(){
        navigationHelper.clickElement(Done_Button_Xpath);
    }

    public void signIn(String email,String password){
        navigationHelper.clickElement(SignIn_Button_Xpath);
        navigationHelper.input(SignInEmail_Input_Name, email);
        navigationHelper.input(SignInPassword_Input_Name,password);
        navigationHelper.clickElement(SignInConfirm_Button_Xpath);
        assert navigationHelper.isElementPresent(UserIconAccount_Button_Xpath);
    }

    public void clickEditProfile(){
        navigationHelper.retryClickUntilElementAppear(UserIconAccount_Button_Xpath,EditProfile_Button_Xpath);
        navigationHelper.clickElement(EditProfile_Button_Xpath);
    }

    public void deleteProfile(){
        navigationHelper.clickElement(DeleteProfile_Button_Xpath);
        navigationHelper.clickElement(ConfirmDeleteProfile_Button_Xpath);
        assert navigationHelper.isElementPresent(SignUp_Button_Xpath);
    }
}
