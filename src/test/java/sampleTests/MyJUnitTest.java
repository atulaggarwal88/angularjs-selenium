package sampleTests;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import utility.WebDriverManager;

public class MyJUnitTest {
	WebDriverManager webDriverManager;
	WebDriver driver;

	@Before
	public void setUp() throws Exception {
		webDriverManager = new WebDriverManager();
		driver = webDriverManager.getDriver();	
	}

	@After
	public void tearDown() throws Exception {
		webDriverManager.closeBrowser();
	}

	@Test
	public void loginTest() {
		try {
			Robot robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = "http://cafetownsend-angular-rails.herokuapp.com/login";
		webDriverManager.getDriver().get(url);
		webDriverManager.getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		//		driver.findElement(By.xpath("//input[@ng-model='user.name']")).click();
		webDriverManager.sendText_TextBox(driver.findElement(By.xpath("//input[@ng-model='user.name']")),"Luke", "");


		//		driver.findElement(By.xpath("(//input)[position()=1]")).sendKeys("Luke123");

		//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		//		driver.findElement(By.cssSelector("input[name='email']")).click();
		webDriverManager.sendText_TextBox(driver.findElement(By.cssSelector("input[ng-model='user.password']")),"Skywalker", "");
		//		driver.findElement(By.cssSelector("input[ng-model='user.password']")).sendKeys("Skywalker");
		//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector(".main-button[type='submit']")).click();

		//		driver.close();



		//		driver.findElement(By.cssSelector("input[name='first_name']")).sendKeys("atul");
	}

}
