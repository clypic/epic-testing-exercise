package cucumber.StepDefinitions;

import cucumber.lib.MySQL;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EpicPortalStepDefinitions {

    private final Duration waitDuration15 = Duration.ofSeconds(15);

    private final String xpathAspnetForm        = "//body/form[@id='aspnetForm']";
    private final String xpathCurrentBalance    = xpathAspnetForm +"//*[text()='My Current Balance is']/parent::*/span/strong";
    private final String xpathHeading           = xpathAspnetForm +"//div[@id='content']//h1";
    private final String xpathMsisdn            = xpathAspnetForm +"//*[@class='landing-number']";
    private final String xpathPrepaidPlan       = xpathAspnetForm +"//*[text()='My Current Prepaid Plan is']/parent::*/span/strong";

    private BigDecimal userBalanceNum;
    private String remote_url;
    private String userBalanceRaw;
    private String userDateRetreived;
    private String userMsisdn;
    private String userName;
    private String userPass;
    private String userPrepaidPlan;
    private WebDriver driver;


    public EpicPortalStepDefinitions () {
        ResourceBundle rd = ResourceBundle.getBundle("environment");
        this.remote_url = rd.getString("selenium_remote_firefox_url");
    }


    private BigDecimal readNumericalBalance (final String balanceRaw) {

        return new BigDecimal(balanceRaw.replaceAll("\\s","").substring(1));
    }

    private String getDateRetreived () {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }


    @Before()
    public void openBrowser()
        throws MalformedURLException {

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        // this.driver = new RemoteWebDriver(new URL(this.remote_url), browserOptions);
        this.driver = new RemoteWebDriver(new URL(this.remote_url), firefoxOptions);
    }

    @After()
    public void closeBrowser() {

        driver.quit();
        MySQL mysql = new MySQL("mysql","3306","epic","secret");
        mysql.user_insert  (this.userBalanceNum, 
                            this.userBalanceRaw, 
                            this.userDateRetreived,
                            this.userMsisdn,
                            this.userPrepaidPlan,
                            this.userName);
        mysql.connectionClose();
    }

    @Given("the user opens the URL {string}")
    public void openUrl (final String url) {
        driver.get(url);
    }

    @Then("page title must contain {string}")
    public void pageTitleMustBeString (final String pageTitle) {
        new WebDriverWait(driver, waitDuration15)
            .until(ExpectedConditions.titleContains(pageTitle));
    }

    @When("user click link {string}")
    public void userClickLinkString(final String linkName) {
        driver.findElement(By.xpath("//a[text()='"+ linkName +"']")).click();
    }

    @And("sign-in form must be displayed")
    public void signInFormMustBeDisplayed () {

        WebElement el = new WebDriverWait(driver, waitDuration15)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form//h2")));
    }

    @When("user sign-in with username {string} and password {string}")
    public void userSignInWithUsernameStringAndPasswordString (final String username, final String password) {

        this.userName = username;
        this.userPass = password;

        By locatorUsername = By.xpath("//input[@id='username']");
        By locatorPassword = By.xpath("//input[@id='password']");
        By locatorLogin    = By.xpath("//button[text()='Log in']");

        driver.findElement(locatorUsername).sendKeys(username);
        driver.findElement(locatorPassword).sendKeys(password);
        driver.findElement(locatorLogin).click();
    }

    @And("content title must be {string}")
    public void contentTitleMustBeString (final String expectedContentTitle) {

        String givenContentTitle = driver.findElement(By.xpath(xpathHeading)).getText().trim();
        Assertions.assertEquals(expectedContentTitle, givenContentTitle);
    }

    @And("MSISDN must be {string}")
    public void msisdnMustBeString (final String expectedMsisdn) {

        String givenMsisdn = driver.findElement(By.xpath(xpathMsisdn)).getText().trim();
        Assertions.assertEquals(givenMsisdn, expectedMsisdn);
        this.userMsisdn = givenMsisdn;
    }

    @And("current balance must be visible")
    public void currentBalanceMustBeVisible () {

        WebElement el = new WebDriverWait(driver, waitDuration15)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCurrentBalance)));
        this.userBalanceRaw = driver.findElement(By.xpath(xpathCurrentBalance)).getText().trim();
        this.userBalanceNum = this.readNumericalBalance(this.userBalanceRaw);
        this.userDateRetreived = this.getDateRetreived();
    }

    @And("prepaid-plan must be visible")
    public void prepaidPlanMustBeVisible () {

        WebElement el = new WebDriverWait(driver, waitDuration15)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPrepaidPlan)));
        this.userPrepaidPlan = driver.findElement(By.xpath(xpathPrepaidPlan)).getText().trim();
    }

    @When("user click button {string}")
    public void userClickButtonString (final String buttonText) {
        driver.findElement(By.xpath("//button[text()='"+ buttonText +"']")).click();
    }
}