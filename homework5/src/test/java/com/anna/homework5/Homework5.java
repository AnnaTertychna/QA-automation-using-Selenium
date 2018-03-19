package com.anna.homework5;

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
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;
import static org.testng.AssertJUnit.assertEquals;

public class Homework5 {
    private WebDriver driver;

    @BeforeMethod
    @Parameters({ "browser" })
    public void setUp(String browser) {
        driver = getWebDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testB() throws InterruptedException {
        driver.get("http://prestashop-automation.qatestlab.com.ua/");

        openProductsPage();

        WebElement product = findRandomProduct();

        String productPriceExpected = product.findElement(
                By.xpath(".//div[@class = 'product-price-and-shipping']/span[@class = 'price']")).getText();
        String productNameExpected = product.findElement(By.xpath(".//a")).getText();

        addToCart(product);

        assertCart(productPriceExpected, productNameExpected);


    }

    private void assertCart(String productPriceExpected, String productNameExpected) {
        String productNameActual = driver.findElement(By.xpath("//div[@class = 'product-line-info']/a")).getText();
        String productPriceActual = driver.findElement(By.className("product-price")).getText();
        int quantity = parseInt(driver.findElement(By.className("js-cart-line-product-quantity")).getAttribute("value"));

        assertEquals(1, quantity);
        assertEquals(productNameExpected, productNameActual);
        assertEquals(productPriceExpected, productPriceActual);
    }

    private void addToCart(WebElement product) {
        new Actions(driver).moveToElement(product).perform();
        WebElement quickView = waitForElement(driver, product.findElement(By.xpath("..//a[@class = 'quick-view']")));
        new Actions(driver).moveToElement(quickView).perform();
        new Actions(driver).moveToElement(quickView).perform();
        //new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(quickView));
        quickView.click();
        waitForElement(driver, By.className("add-to-cart")).click();
        waitForElement(driver, By.xpath("//div[@class = 'cart-content']/a")).click();
    }

    private WebElement findRandomProduct() {
        List<WebElement> products = driver.findElements(By.xpath("//div[@class = 'product-description']"));
        return products.get(new Random().nextInt(products.size()));
    }

    private void openProductsPage() {
        driver.findElement(By.className("all-product-link")).click();
        waitForElement(driver, By.tagName("h1"));
    }

    private WebElement waitForElement(WebDriver driver, By by) {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private WebElement waitForElement(WebDriver driver, WebElement webElement) {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(webElement));
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
