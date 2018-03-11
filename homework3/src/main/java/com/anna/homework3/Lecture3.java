package com.anna.homework3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class Lecture3 {
    public static void main(String[] arguments) throws Exception {
        WebDriver driver = getWebDriver();

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        login(driver);

        waitForElement(driver, By.id("subtab-AdminCatalog"));

        WebElement catalog = driver.findElement(By.id("subtab-AdminCatalog"));
        Actions builder = new Actions(driver);
        builder.moveToElement(catalog).perform();
        By byCategories = By.id("subtab-AdminCategories");
        waitForElement(driver, byCategories);
        WebElement categories = driver.findElement(byCategories);
        categories.click();
        waitForElement(driver, By.id("page-header-desc-category-new_category"));
        driver.findElement(By.id("page-header-desc-category-new_category")).click();

        waitForElement(driver, By.id("name_1"));
        driver.findElement(By.id("name_1")).sendKeys("New categories");
        driver.findElement(By.id("category_form_submit_btn")).click();
        waitForElement(driver, By.className("alert-success"));

        driver.findElement(By.name("categoryFilter_name")).sendKeys("New categories");
        driver.findElement(By.id("submitFilterButtoncategory")).click();
        waitForElement(driver, By.xpath("//td[contains(text(), 'New categories')]"));

        Thread.sleep(5000);
        driver.quit();
    }

    private static void waitForElement(WebDriver driver, By by) {
        WebDriverWait waitCatalog = new WebDriverWait(driver, 10);
        waitCatalog.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private static void login(WebDriver driver) {
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("webinar.test@gmail.com");

        WebElement password = driver.findElement(By.name("passwd"));
        password.sendKeys("Xcg7299bnSmMuRLp9ITw");

        WebElement button = driver.findElement(By.name("submitLogin"));
        button.submit();
    }

    private static WebDriver getWebDriver() {
        String osDirectory = getOsDirectory();

        System.setProperty("webdriver.chrome.driver",  new File(Lecture3.class
                .getResource("/" + osDirectory + "/chromedriver-2.33.exe").getFile()).getPath());

        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(new ChromeDriver());
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
