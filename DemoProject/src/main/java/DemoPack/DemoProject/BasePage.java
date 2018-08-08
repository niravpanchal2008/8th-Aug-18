package DemoPack.DemoProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import DemoPack.DemoProject.LoginPage.LoginPageUI;
import net.bytebuddy.jar.asm.commons.Method;

public class BasePage {
	public static WebDriver driver;
	public static final String path="./data.properties";
	public static final String excelpath = "C:\\Users\\Quennie\\Documents\\testCase.xlsx";
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ITestResult result;
	
	public static final Logger log = Logger.getLogger(BasePage.class.getName());
	
	public static String getData(String key) throws Exception {
		File f = new File(path);
		FileInputStream fi = new FileInputStream(f);
		Properties p =new Properties();
		p.load(fi);
//		System.out.println(fi);
//		System.out.println("file :"+p.getProperty(key));
		return p.getProperty(key);
	}
	
	public static void logInitiate() {
		String log4jconf = "./log4j.properties";
		PropertyConfigurator.configure(log4jconf);
	}
	
	public String randomNumber(int range) {
		Random r = new Random();
		if(range == 5) {
			return String.valueOf(r.nextInt(90000)+10000);
		}else if(range == 10) {
			return String.valueOf(r.nextInt(900000000)+100000000);
		}else return null;
	}
	
	public void selectOption(WebElement element, int option) {
		Select s = new Select(element);
		s.selectByIndex(option);
	}
	
	public void waitForElement(WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	@SuppressWarnings("unused")
	public void randomListLoad(List<WebElement> element) throws Exception {
		Random r = new Random();
		int listLength = element.size();
		for(int i=0; i<listLength; i++) {
			element.get(r.nextInt(listLength)).click();
			Thread.sleep(10000);
			return;
		}
	}
	
	@AfterClass
	public void endProcess()
	{
		closeBrowser();
	}
	
	
	
	public void closeBrowser() 
	{
		driver.quit();
		log.info("---Browser Closed ---");
		extent.endTest(test);
		extent.flush();
	}
	
	
	
	@BeforeMethod
	
	public void beforeMethod(Method result)
	{
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + "---Test Started---");
	}
	
	
	@AfterMethod
	public void afterMethod(ITestResult iResult)
	{
		getResult(iResult);
		
	}
	
	

	public void getResult(ITestResult iResult) 
	{
		if(iResult.getStatus()==ITestResult.SUCCESS)
		{
			test.log(LogStatus.PASS, iResult.getName() + "---Test is PASS ---");
		}
		else if(iResult.getStatus()==ITestResult.SKIP)
		{
			test.log(LogStatus.SKIP, iResult.getName() + "Test is SKIPPED & skipped reason is: " + iResult.getThrowable());
		}
		else if(iResult.getStatus()==ITestResult.FAILURE)
		{
			test.log(LogStatus.ERROR, iResult.getName() + "Test is FAIL & Failed reason is: " + iResult.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		}
				
	}

	public String captureScreen(String fileName) 
	{
		if(fileName == "")
		{
			fileName = "blank";
		}
		
		File destFile = null;
	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

	    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	    try 
	    {
	        String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/";
	        destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
	        FileUtils.copyFile(scrFile, destFile);
	        // This will help us to link the screen shot in testNG report
	        Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return destFile.toString();
		
	}

	public static void BrowserLaunch(String browser, String url) {
		logInitiate();
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
			driver = new ChromeDriver();		
		}else if(browser.equalsIgnoreCase("FIREFOX")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//drivers//geckodriver.exe");
			driver = new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//drivers//IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		driver.get(url);
		driver.manage().window().maximize();
	}
	

}
