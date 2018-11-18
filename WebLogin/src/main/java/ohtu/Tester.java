package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {

	public static void main(String[] args) {
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:4567");

		driver.findElement(By.linkText("login")).click();
		driver.findElement(By.name("username")).sendKeys("pekka");
		driver.findElement(By.name("password")).sendKeys("incorrect");
		driver.findElement(By.name("login")).submit();
		driver.findElement(By.name("username")).sendKeys("incorrect");
		driver.findElement(By.name("password")).sendKeys("akkep");
		driver.findElement(By.name("login")).submit();

		driver.findElement(By.linkText("back to home")).click();

		driver.findElement(By.linkText("register new user")).click();
		driver.findElement(By.name("username")).sendKeys("newpekka" + System.currentTimeMillis());
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.name("passwordConfirmation")).sendKeys("password");
		driver.findElement(By.name("signup")).submit();

		driver.findElement(By.linkText("continue to application mainpage")).click();
		driver.findElement(By.linkText("logout")).click();

		driver.quit();
	}

	private static void sleep(int n) {
		try {
			Thread.sleep(n * 1000);
		} catch (Exception e) {
		}
	}
}
