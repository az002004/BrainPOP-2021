import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Login_Chrome_Test {

    WebDriver driver;

    String loginMainPageButtonXpath = "//button[@id='login-button']";
    String mainPageUrl = "https://www.brainpop.com/";
    String popupLoginXpath = "//div[@class='modal-content']";
    String correctUsername = "qatest2021";
    String correctPassword = "brainp0p";
    String correctUsernameXpath = "//*[@id='user_menu']//*[contains(text(), '" + correctUsername + "')]";
    String errorEmptyUsernameXpath = "//span[contains(text(), 'Please type your username.')]";
    String errorEmptyPasswordXpath = "//span[contains(text(), 'Please type your password.')]";
    String errorLockedAccountXpath = "//a[contains(@href, '/password-reminder/')][@class='ub_links']";

    @BeforeSuite
    public void setUpChrome(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterSuite
    public void closeBrowser(){
        driver.quit();
    }

    @Test
    public void testLockedAccount() {
        driver.get(mainPageUrl);

        Common.waitForElement(driver, By.xpath(loginMainPageButtonXpath), 20);
        driver.findElement(By.xpath(loginMainPageButtonXpath)).click();

        Common.waitForElement(driver, By.xpath(popupLoginXpath), 20);
        WebElement usernameTextfield = driver.findElement(By.id("login"));
        WebElement passwordTextfield = driver.findElement(By.id("password"));
        WebElement loginButtonPopup = driver.findElement(By.id("btnLogin"));

        usernameTextfield.sendKeys("admin");
        passwordTextfield.sendKeys("admin");
        loginButtonPopup.click();

        Common.waitForElement(driver, By.xpath(errorLockedAccountXpath), 20);
        WebElement errorLockedAccount = driver.findElement(By.xpath(errorLockedAccountXpath));

        Assert.assertTrue(errorLockedAccount.isDisplayed());
    }

    @Test
    public void testEmptyUsername() {
        driver.get(mainPageUrl);

        Common.waitForElement(driver, By.xpath(loginMainPageButtonXpath), 20);
        driver.findElement(By.xpath(loginMainPageButtonXpath)).click();

        Common.waitForElement(driver, By.xpath(popupLoginXpath), 20);
        WebElement loginButtonPopup = driver.findElement(By.id("btnLogin"));

        loginButtonPopup.click();

        Common.waitForElement(driver, By.xpath(errorEmptyUsernameXpath), 20);
        WebElement errorEmptyUsername = driver.findElement(By.xpath(errorEmptyUsernameXpath));

        Assert.assertTrue(errorEmptyUsername.isDisplayed());
    }

    @Test
    public void testEmptyPassword() {
        driver.get(mainPageUrl);

        Common.waitForElement(driver, By.xpath(loginMainPageButtonXpath), 20);
        driver.findElement(By.xpath(loginMainPageButtonXpath)).click();

        Common.waitForElement(driver, By.xpath(popupLoginXpath), 20);
        WebElement usernameTextfield = driver.findElement(By.id("login"));
        WebElement loginButtonPopup = driver.findElement(By.id("btnLogin"));

        usernameTextfield.sendKeys("YGTfr");
        loginButtonPopup.click();

        Common.waitForElement(driver, By.xpath(errorEmptyPasswordXpath), 20);
        WebElement errorEmptyPassword = driver.findElement(By.xpath(errorEmptyPasswordXpath));

        Assert.assertTrue(errorEmptyPassword.isDisplayed());
    }

    @Test
    public void testValidUsernameValidPassword() {

        driver.get(mainPageUrl);

        Common.waitForElement(driver, By.xpath(loginMainPageButtonXpath), 20);
        driver.findElement(By.xpath(loginMainPageButtonXpath)).click();

        Common.waitForElement(driver, By.xpath(popupLoginXpath), 20);
        WebElement usernameTextfield = driver.findElement(By.id("login"));
        WebElement passwordTextfield = driver.findElement(By.id("password"));
        WebElement loginButtonPopup = driver.findElement(By.id("btnLogin"));

        usernameTextfield.sendKeys(correctUsername);
        passwordTextfield.sendKeys(correctPassword);
        loginButtonPopup.click();

        Common.waitForElement(driver, By.xpath(correctUsernameXpath), 20);
        WebElement expectedUsername = driver.findElement(By.xpath(correctUsernameXpath));

        Assert.assertTrue(expectedUsername.isDisplayed());
    }
}
