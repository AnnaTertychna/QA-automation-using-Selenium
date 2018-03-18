package com.anna.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class Homework4 {
    private static final String URL = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";

    private final String productName = UUID.randomUUID().toString();
    private final int quantity = new Random().nextInt(100) + 1;
    private final double price = Double.parseDouble(new DecimalFormat("#.##")
            .format(new Random().nextFloat() * 100 + 0.1));
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

    @Test(dependsOnMethods = "testA", dataProvider = "getData")
    public void testB(String login, String password){
        driver.get(URL);

        login(driver, login, password);

        openProductsPage(driver);

        WebElement nameFilter = driver.findElement(By.name("filter_column_name"));
        nameFilter.clear();
        nameFilter.sendKeys(productName);
        driver.findElement(By.name("products_filter_submit")).submit();
        waitForElement(driver, By.linkText(productName));

        assertEquals(driver.findElements(By.linkText(productName)).size(), 1);

        String priceText = driver.findElement(By.xpath("//a[text() = '" + productName + "']/../../td[6]/a")).getText();
        assertEquals(price, Double.parseDouble(priceText.split(" ")[0].replace(",", ".")));

        String quantityText = driver.findElement(By.xpath("//a[text() = '" + productName
                + "']/../../td[contains(@class, 'product-sav-quantity')]/a"))
                .getText();
        assertEquals(quantity, Integer.parseInt(quantityText));
    }

    @Test(dataProvider = "getData")
    public void testA(String login, String password){
        driver.get(URL);

        login(driver, login, password);

        openProductsPage(driver);

        driver.findElement(By.id("page-header-desc-configuration-add")).click();

        By byProductName = By.id("form_step1_name_1");
        waitForElement(driver, byProductName);
        driver.findElement(byProductName).sendKeys(productName);

        driver.findElement(By.id("tab_step3")).click();
        By byAmount = By.id("form_step3_qty_0");
        waitForElement(driver, byAmount);
        driver.findElement(byAmount).sendKeys(String.valueOf(quantity));

        driver.findElement(By.id("tab_step2")).click();
        WebElement priceElement = waitForElement(driver, By.id("form_step2_price"));
        priceElement.clear();
        priceElement.sendKeys(String.valueOf(price));

        driver.findElement(By.className("switch-input")).click();
        waitForElement(driver,By.className("growl-message"));
        driver.findElement(By.className("growl-close")).click();

        driver.findElement (By.className("js-btn-save")).click();
        waitForElement(driver,By.className("growl-message"));
        driver.findElement(By.className("growl-close")).click();
    }

    private void openProductsPage(WebDriver driver) {
        By byCatalog = By.id("subtab-AdminCatalog");
        waitForElement(driver, byCatalog);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(byCatalog)).perform();
        By byProducts = By.id("subtab-AdminProducts");
        waitForElement(driver, byProducts);
        WebElement products = driver.findElement(byProducts);
        products.click();
        waitForElement(driver, By.tagName("h2"));
    }

    private void login(WebDriver driver, String login, String password) {
        By byEmail = By.name("email");
        waitForElement(driver, byEmail);

        driver.findElement(byEmail).sendKeys(login);
        driver.findElement(By.name("passwd")).sendKeys(password);

        driver.findElement(By.name("submitLogin")).submit();
    }

    private WebElement waitForElement(WebDriver driver, By by) {
        WebDriverWait waitCatalog = new WebDriverWait(driver, 10);
        waitCatalog.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
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
        return new File(Homework4.class.getResource("/" + getOsDirectory() + "/" + driverExec)
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
