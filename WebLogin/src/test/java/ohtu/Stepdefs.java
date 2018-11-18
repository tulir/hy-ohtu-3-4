package ohtu;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {
	private WebDriver driver = new HtmlUnitDriver();
	private String baseUrl = "http://localhost:4567";

	@Given("^login is selected$")
	public void login_selected() throws Throwable {
		driver.get(baseUrl);
		driver.findElement(By.linkText("login")).click();
	}

	@When("^username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
	public void username_and_password_are_given(String username, String password) throws Throwable {
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).submit();
	}

	@Then("^system will respond \"([^\"]*)\"$")
	public void system_will_respond(String pageContent) throws Throwable {
		assertTrue(driver.getPageSource().contains(pageContent));
	}

	@When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
	public void username_correct_and_password_are_given(String username, String password) throws Throwable {
		logInWith(username, password);
	}

	@When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
	public void username_and_incorrect_password_are_given(String username, String password) throws Throwable {
		logInWith(username, password);
	}

	@When("^incorrect username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
	public void incorrectUsernameAndPasswordAreGiven(String username, String password) throws Throwable {
		logInWith(username, password);
	}

	@Then("^user is logged in$")
	public void user_is_logged_in() throws Throwable {
		pageHasContent("Ohtu Application main page");
	}

	@Then("^user is not logged in and error message is given$")
	public void user_is_not_logged_in_and_error_message_is_given() throws Throwable {
		pageHasContent("invalid username or password");
		pageHasContent("Give your credentials to login");
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	/* helper methods */

	private void pageHasContent(String content) {
		assertTrue(driver.getPageSource().contains(content));
	}

	private void logInWith(String username, String password) {
		assertTrue(driver.getPageSource().contains("Give your credentials to login"));
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).submit();
	}

	private void signUpWith(String username, String password, String passwordConfirmation) {
		assertTrue(driver.getPageSource().contains("Create username and give password"));
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("passwordConfirmation")).sendKeys(passwordConfirmation);
		driver.findElement(By.name("signup")).submit();
	}

	@Given("^command new user is selected$")
	public void commandNewUserIsSelected() {
		driver.get(baseUrl);
		driver.findElement(By.linkText("register new user")).click();
	}

	@When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
	public void aValidUsernameAndPasswordAndMatchingPasswordConfirmationAreEntered(String username, String password) throws Throwable {
		signUpWith(username, password, password);
	}

	@Then("^a new user is created$")
	public void aNewUserIsCreated() {
		pageHasContent("Welcome to Ohtu Application!");
	}

	@When("^an invalid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
	public void anInvalidUsernameAndPasswordAndMatchingPasswordConfirmationAreEntered(String username, String password) throws Throwable {
		signUpWith(username, password, password);
	}

	@Then("^user is not created and error \"([^\"]*)\" is reported$")
	public void userIsNotCreatedAndErrorIsReported(String err) throws Throwable {
		pageHasContent(err);
	}

	@When("^a valid username \"([^\"]*)\" and invalid password \"([^\"]*)\" and matching password confirmation are entered$")
	public void aValidUsernameAndInvalidPasswordAndMatchingPasswordConfirmationAreEntered(String username, String password) throws Throwable {
		signUpWith(username, password, password);
	}

	@When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and password confirmation \"([^\"]*)\" are entered$")
	public void aValidUsernameAndPasswordAndPasswordConfirmationAreEntered(String username, String password, String passwordConfirmation) throws Throwable {
		signUpWith(username, password, passwordConfirmation);
	}

	@Given("^user with username \"([^\"]*)\" with password \"([^\"]*)\" is successfully created$")
	public void userWithUsernameWithPasswordIsSuccessfullyCreated(String username, String password) throws Throwable {
		commandNewUserIsSelected();
		signUpWith(username, password, password);
		driver.findElement(By.linkText("continue to application mainpage")).click();
		driver.findElement(By.linkText("logout")).click();
	}

	@Given("^user with username \"([^\"]*)\" and password \"([^\"]*)\" is tried to be created$")
	public void userWithUsernameAndPasswordIsTriedToBeCreated(String username, String password) throws Throwable {

	}
}
