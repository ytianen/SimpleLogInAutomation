package fox.Page;


import org.openqa.selenium.By;

import static fox.helpers.NavigationHelper.*;


public class HomePage {
    //public NavigationHelper navigationHelper = new NavigationHelper();
    //Home Page Navigation locator
    private By UserIconAccount_Button_Xpath =By.xpath("//a[@href='/account']");
    private By SignUp_Button_Xpath = By.xpath("//button[text()='Sign Up']");
    //Sign Up Locators
    private By FirstName_inputText_Name = By.name("signupFirstName");
    private By LastName_inputText_Name = By.name("signupLastName");
    private By Email_inputText_Name= By.name("signupEmail");
    private By Password_inputText_Name= By.name("signupPassword");
    private By Birthdate_inputText_Xpath = By.xpath("//*[contains(@placeholder,'Birthdate')]");
    private By Gender_Dropdown_Xpath= By.xpath("//a[text()='Gender']");
    private By CreateProfile_Button_Xpath= By.xpath("//div[@class='Account_signupButtonDesktop_1PCXs']/button");
    private By Done_Button_Xpath= By.xpath("//button[contains(text(),'Done')]");

    //Sign In Locators
    private By SignIn_Button_Xpath= By.xpath("//button[text()='Sign In']");
    private By SignInEmail_inputText_Name = By.name("signinEmail");
    private By SignInPassword_inputText_Name = By.name("signinPassword");
    private By SignInConfirm_Button_Xpath=By.xpath("//div[@class='Account_signinButtonDesktop_2SO1g']/button");

    //Account Management
    private By EditProfile_Button_Xpath = By.xpath("//a[text()='Edit Profile']");
    private By DeleteProfile_Button_Xpath = By.xpath("//a[text()='Delete Profile']");
    private By ConfirmDeleteProfile_Button_Xpath = By.xpath("//button[text()='Delete Profile']");

    public void goTo(String url){
        goTo(url);
        isElementPresent(UserIconAccount_Button_Xpath);
    }

    public void clickAccount(){
        clickElement(UserIconAccount_Button_Xpath);
    }

    public void click_SignUp(){
        clickElement(SignUp_Button_Xpath);
    }

    public void signUpInfo(String field, String inputText){
        assert field!=null:"Field is null";
        assert inputText!=null:"inputText can not be null for" + field;

        //reformat field to lowercase
        field=field.toLowerCase().trim();

        if(field.equals("lastname"))
        {
            inputText(LastName_inputText_Name,inputText);
        }
        else if(field.equals("firstname"))
        {
            inputText(FirstName_inputText_Name,inputText);
        }
        else if(field.equals("email"))
        {
            inputText(Email_inputText_Name,inputText);
        }
        else if(field.equals("password")){
            inputText(Password_inputText_Name,inputText);
        }
        else if(field.equals("birthdate")){
            inputText(Birthdate_inputText_Xpath,inputText);
        }
        else{
            System.out.print("===ERROR===:No valid field is found.");
        }
    }

    public void selectGender(String Gender){
        assert Gender.equals("Male")||Gender.equals("Female")||Gender.equals("Prefer not to say"):"Invalid inputText for Gender";
        clickElement(Gender_Dropdown_Xpath);
        clickElementText(Gender);
    }

    public void clickCreateProfile(){
        clickElement(CreateProfile_Button_Xpath);
    }

    public boolean verifyText(String text){
       return isElementPresent(By.xpath("//*[contains(text(),'"+text+"')]"));
    }

    public void clickDone(){
        clickElement(Done_Button_Xpath);
    }

    public void signIn(String email,String password){
        clickElement(SignIn_Button_Xpath);
        inputText(SignInEmail_inputText_Name, email);
        inputText(SignInPassword_inputText_Name,password);
        clickElement(SignInConfirm_Button_Xpath);
        assert isElementPresent(UserIconAccount_Button_Xpath);
    }

    public void clickEditProfile(){
        retryClickUntilElementAppear(UserIconAccount_Button_Xpath,EditProfile_Button_Xpath);
        clickElement(EditProfile_Button_Xpath);
    }

    public void deleteProfile(){
        clickElement(DeleteProfile_Button_Xpath);
        clickElement(ConfirmDeleteProfile_Button_Xpath);
        assert isElementPresent(SignUp_Button_Xpath);
    }
}
