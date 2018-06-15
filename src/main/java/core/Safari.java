package core;

import org.openqa.selenium.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Safari {
		
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new SafariDriver();
		
		// cleaning up the logs of the output of the console
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
		
		String url = "http://facebook.com/";
		String email_address = "email";
		String password = "password";
		
		if (!System.getProperty("os.name").toUpperCase().contains("MAC"))
			throw new IllegalArgumentException("Safari is available only on Mac");
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();	
		WebDriverWait wait = new WebDriverWait(driver, 15);		
		
        driver.get(url);
		
		wait.until(ExpectedConditions.titleIs("Facebook - Log In or Sign Up"));
		String title = driver.getTitle();
		
		String copyright = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Facebook © 2018')]"))).getText();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).sendKeys(email_address);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pass"))).clear();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pass"))).sendKeys(password);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginbutton"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id=\"u_0_a\"]/div[1]/div[1]"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\\\"u_0_a\\\"]/div[1]/div[1]/div/a/span/span"))).click();
		Thread.sleep(4000); //Pause in milliseconds (1000 - 1 sec)
		driver.findElement(By.xpath("//*[@id=\"u_0_a\"]/div[1]/div[1]/div/a/span/span")).click();
		
//		String friends = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='213']"))).getText();
		Thread.sleep(4000); //Pause in milliseconds (1000 - 1 sec)
		String friends = 
				driver.findElement(By.xpath("//span[text()='213']")).getText();
		
		WebElement settings = driver.findElement(By.id("userNavigationLabel"));	
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", settings);
		
		WebElement logout = driver.findElement(By.xpath("//span[text()='Log Out']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);
		
		
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userNavigationLabel"))).click();
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Log Out']"))).click(); 
		
		driver.quit();
		
		System.out.println("Browser is: Safari");
		System.out.println("Title of the page: " + title);
		System.out.println("Copyright: " + copyright);
		System.out.println("You have " + friends + " friends");
	}
}
