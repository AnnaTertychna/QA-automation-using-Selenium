package com.anna.homework5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;

public class Homework5 {
    private WebDriver driver;

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{{"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}};
    }

    @BeforeMethod
    @Parameters({ "browser" })
    public void setUp(String browser) {
        driver = getWebDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(dataProvider = "getData")
    public void testA(String login, String password){
        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        login(driver, login, password);

    }

    private WebElement waitForElement(WebDriver driver, By by) {
        WebDriverWait waitCatalog = new WebDriverWait(driver, 10);
        waitCatalog.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    private void login(WebDriver driver, String login, String password) {
        By byEmail = By.name("email");
        waitForElement(driver, byEmail);

        driver.findElement(byEmail).sendKeys(login);
        driver.findElement(By.name("passwd")).sendKeys(password);

        driver.findElement(By.name("submitLogin")).submit();
    }

    private WebDriver getWebDriver(String browser) {
        RemoteWebDriver browserDriver = null;
        if (browser.toLowerCase().contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", getDriverPath("chromedriver-2.35.exe"));
            browserDriver = new ChromeDriver();
        } else if (browser.toLowerCase().contains("firefox")) {
            System.setProperty("webdriver.gecko.driver", getDriverPath("geckodriver-0.20.0.exe"));
            browserDriver = new FirefoxDriver();
        } else if (browser.toLowerCase().contains("explorer")) {
            System.setProperty("webdriver.ie.driver", getDriverPath("IEDriverServer-3.8.exe"));
            browserDriver = new InternetExplorerDriver();
        }
        if (browserDriver != null) {
            EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(browserDriver);
            eventFiringWebDriver.register(new MyWebDriverEventListener());
            return eventFiringWebDriver;
        }
        throw new RuntimeException("Browser" + browser + " is not supported");
    }

    private String getDriverPath(String driverExec) {
        return new File(Homework5.class.getResource("/" + getOsDirectory() + "/" + driverExec)
                .getFile()).getPath();
    }

    private String getOsDirectory() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("win")) {
            return "windows";
        }
        throw new RuntimeException(osName + " is not supported");
    }
}
