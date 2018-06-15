package core;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Firefox {

	static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		
		// cleaning up the logs of the output of the console
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
		
		String driverPath = " ";
		String url = "http://facebook.com/";
		String email_address = "email";
		String password = "password";
		
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			driverPath = "./resources/webdrivers/mac/geckodriver.sh";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "./resources/webdrivers/pc/geckodriver.exe";
		else
			throw new IllegalArgumentException("Unknown OS");
		
		System.setProperty("webdriver.gecko.driver", driverPath);
		
		FirefoxProfile testprofile = new FirefoxProfile();
		testprofile.setPreference("dom.webnotifications.enabled", false);		
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(FirefoxDriver.PROFILE, testprofile);
		FirefoxOptions opt = new FirefoxOptions();
		opt.merge(dc);
		
		WebDriver driver =  new FirefoxDriver(opt);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 15);		
			
		driver.get(url);
		
		wait.until(ExpectedConditions.titleIs("Facebook - Log In or Sign Up"));
		String title = driver.getTitle();
		
		String copyright = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Facebook © 2018')]"))).getText();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).sendKeys(email_address);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pass"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pass"))).sendKeys(password);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("u_0_2"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id=\"u_0_a\"]/div[1]/div[1]"))).click();
	
//      Waiting 15 seconds for an element to be present on the page, checking
//      For its presence once every 5 seconds		
//		Wait<WebDriver> wait new FluentWait<WebDriver>(driver)
//		.withTimeout(15, TimeUnit.SECONDS)
//		.pollingEvery(5, TimeUnit.SECONDS)
//		.ignoring(NoSuchElementException.class);
		
		String friends = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='213']"))).getText();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userNavigationLabel"))).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Log Out']"))).click(); 
		
		driver.quit();
		
		System.out.println("Browser is: Firefox");
		System.out.println("Title of the page: " + title);
		System.out.println("Copyright: " + copyright);
		System.out.println("You have " + friends + " friends");
	}
}
