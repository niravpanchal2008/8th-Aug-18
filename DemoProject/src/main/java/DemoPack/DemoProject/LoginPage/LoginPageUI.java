package DemoPack.DemoProject.LoginPage;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import DemoPack.DemoProject.BasePage;

public class LoginPageUI extends BasePage {

	public static final Logger log = Logger.getLogger(LoginPageUI.class.getName());
	
	//login page properties
	@FindBy(linkText="Sign in") public WebElement signIn;
	@FindBy(id="email") public WebElement email;
	@FindBy(id="passwd") public WebElement password;
	@FindBy(id="SubmitLogin") public WebElement submitBtn;
	@FindBy(xpath=".//*[@id='center_column']/div[1]/ol/li") public WebElement errorText;
	
	//registration page properties
	@FindBy(id="email_create") public WebElement regEmail;
	@FindBy(id="SubmitCreate") public WebElement regSubmit;
	@FindBy(id="uniform-id_gender2") public WebElement regGender;
	@FindBy(id="customer_firstname") public WebElement regFirstName;
	@FindBy(id="customer_lastname") public WebElement regLastName;
	@FindBy(id="days") public WebElement regBirthDate;
	//@FindBy(id="month") public WebElement regBirthMonth;
	@FindBy(id="months") public WebElement regBirthMonth;
	@FindBy(id="years") public WebElement regBirthYear;
	@FindBy(id="address1") public WebElement regAddress;
	@FindBy(id="city") public WebElement regCity;
	@FindBy(id="id_state") public WebElement regState;
	@FindBy(id="postcode") public WebElement regPostCode;
	@FindBy(id="id_country") public WebElement regCountry;
	@FindBy(id="phone_mobile") public WebElement regPhone;
	@FindBy(id="alias") public WebElement regAlias;
	@FindBy(id="submitAccount") public WebElement regAccountSubmit;
	
	
	

	public LoginPageUI(WebDriver driver) { 
		PageFactory.initElements(driver, this);
	}

	public void loginVerification() {
		signIn.click();
		email.sendKeys("bhanu@gmail.com");
		password.sendKeys("web");
		submitBtn.click();
	}
	
	public String loginErrorVerification() {
		return errorText.getText();
	}
	
	public void customerRegistration() throws Exception {
		signIn.click();
		
		log.info("CLICKED ON SIGN-IN BY USING OBJECT: "+signIn.toString());
		
		regEmail.sendKeys(getData("firstname")+getData("lastname")+randomNumber(5)+getData("domain"));
		
		log.info("ENTERED E-MAIL ADDRESS:"+regEmail.getAttribute("value")+" BY USING OBJECT"+regEmail.toString());
//		System.out.println(regEmail.getAttribute("value"));
		regSubmit.click();
		waitForElement(regGender, 30);
		regGender.click();
		regFirstName.sendKeys(getData("firstname"));
		regLastName.sendKeys(getData("lastname"));
		password.sendKeys(getData("password"));
		selectOption(regBirthDate, 2);
		selectOption(regBirthMonth, 3);
		selectOption(regBirthYear, 10);
		regAddress.sendKeys("hyderabad");
		regCity.sendKeys("hyderabad");
		regState.sendKeys("Indiana");
		regPostCode.sendKeys(randomNumber(5));
		regCountry.sendKeys("United States");
		regPhone.sendKeys(randomNumber(10));
		regAlias.sendKeys("secunderabad");

	}
}
