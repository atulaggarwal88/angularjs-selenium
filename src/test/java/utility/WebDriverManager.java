package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import custom_enums.Browser;

public class WebDriverManager {

	WebDriver driver = null;
	Browser browserType = null;
	ConfigFileReader configFileReader = null;
	private static final String CHROME_BINARY_PATH = "E:\\atul\\personal\\JavaJARs\\chrome\\chromedriver.exe";
	private static final String GECKO_BINARY_PATH = "E:\\atul\\personal\\JavaJARs\\chrome\\chromedriver.exe";


	public WebDriverManager() {	
		configFileReader = ConfigFileReader.getInstance();
		browserType = getBrowserTypeEnum(configFileReader.getProperty("browserType"));
	}

	private Browser getBrowserTypeEnum(String browserTypeStr) {
		String browserVerMvnVal = System.getProperty("browserVer");
		if(browserVerMvnVal != null) {
			browserTypeStr = browserVerMvnVal;
			System.out.println("browserVer set from maven command line");
		}
		System.out.println("value of atul from pom: " + System.getProperty("atul"));
		System.out.println("value of atul123 from pom: " + System.getProperty("atul123"));
//		System.out.println("value of atul from pom - config: " + configFileReader.getProperty("atul"));
		
		if(browserTypeStr != null) {
			return Browser.valueOf(browserTypeStr.toUpperCase().trim());
		}
		else {			
			return Browser.CHROME;			
			//log to logger
		}
	}

	public WebDriver getDriver() {
		if(driver == null) {
			switch (browserType){
			case CHROME:				
				driver = getChromeDriver();
				break;
			case FIREFOX:
				driver = new FirefoxDriver();
				break;
			default:
				driver = getChromeDriver();
				break;
			}
		}
		return driver;
	}

	private ChromeDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", CHROME_BINARY_PATH);
		ChromeDriver chromeDriver = new ChromeDriver();
		return chromeDriver;		
	} 

	private FirefoxDriver getFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", GECKO_BINARY_PATH);
		FirefoxDriver chromeDriver = new FirefoxDriver();
		return chromeDriver;		
	} 
	public void sendText_TextBox(WebElement elem, String textValue, String elemDesc) {
		try {
			WebElement foundElement = explicitWaitForElement(elem);
			foundElement.sendKeys(textValue);
		}catch(Exception e) {
			//logger
		}
	}
	public WebElement explicitWaitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 3);
		WebElement foundElem = wait.until(ExpectedConditions.elementToBeClickable(element));
		return foundElem;
	}
	public void closeBrowser() {
		getDriver().close();		
//		getDriver().quit();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
