package com.anna.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;

public class Homework4 {
    @Test
    public void login(){
        WebDriver driver = getWebDriver();

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        login(driver);

        openProductsPage(driver);

        waitForElement(driver, By.tagName("h2"));

        driver.findElement(By.id("page-header-desc-configuration-add")).click();

        driver.quit();
    }

    private static void openProductsPage(WebDriver driver) {
        By byCatalog = By.id("subtab-AdminCatalog");
        waitForElement(driver, byCatalog);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(byCatalog)).perform();
        By byProducts = By.id("subtab-AdminProducts");
        waitForElement(driver, byProducts);
        WebElement products = driver.findElement(byProducts);
        products.click();
    }

    private static void login(WebDriver driver) {
        By byEmail = By.name("email");
        waitForElement(driver, byEmail);

        WebElement email = driver.findElement(byEmail);
        email.sendKeys("webinar.test@gmail.com");

        WebElement password = driver.findElement(By.name("passwd"));
        password.sendKeys("Xcg7299bnSmMuRLp9ITw");

        WebElement button = driver.findElement(By.name("submitLogin"));
        button.submit();
    }

    private static void waitForElement(WebDriver driver, By by) {
        WebDriverWait waitCatalog = new WebDriverWait(driver, 10);
        waitCatalog.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private static WebDriver getWebDriver() {
        String driver = "chromedriver-2.35.exe";
        String property = "webdriver.chrome.driver";

        System.setProperty(property,  new File(Homework4.class
                .getResource("/" + getOsDirectory() + "/" + driver).getFile()).getPath());

        RemoteWebDriver browserDriver = new ChromeDriver();
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(browserDriver);
        eventFiringWebDriver.register(new MyWebDriverEventListener());
        return eventFiringWebDriver;
    }

    private static String getOsDirectory() {
        String osName = System.getProperty("os.name");
        String osDirectory = "";
        if (osName.contains("Win")) {
            osDirectory = "windows";
        } else {
            throw new RuntimeException(osName + " is not supported");
        }
        return osDirectory;
    }

}
