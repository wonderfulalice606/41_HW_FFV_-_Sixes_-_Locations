package core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Chrome {
	
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
			driverPath = "./resources/webdrivers/mac/chromedriver";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "./resources/webdrivers/pc/chromedriver.exe";
		else
			throw new IllegalArgumentException("Unknown OS");
		
		System.setProperty("webdriver.chrome.driver", driverPath);
		
		// cleaning up the logs of the output of the console
		System.setProperty("webdriver.chrome.silentOutput", "true");	
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		options.addArguments("--disable-notifications");
		
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			options.addArguments("-start-fullscreen");
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			options.addArguments("--start-maximized");
		else throw new IllegalArgumentException("Unknown OS");
		
		driver = new ChromeDriver(options);
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
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
		
		System.out.println("Browser is: Chrome");
		System.out.println("Title of the page: " + title);
		System.out.println("Copyright: " + copyright);
		System.out.println("You have " + friends + " friends");
	}
}

