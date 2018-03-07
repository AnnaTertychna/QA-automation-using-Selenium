package com.anna.homework2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class AdminPanelLoginB {
    public static void main(String[] arguments) throws Exception {
        WebDriver driver = getWebDriver();

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("webinar.test@gmail.com");

        WebElement password = driver.findElement(By.name("passwd"));
        password.sendKeys("Xcg7299bnSmMuRLp9ITw");
        Thread.sleep(3000);

        WebElement button = driver.findElement(By.name("submitLogin"));
        button.submit();
        Thread.sleep(3000);

        processMenuItem(driver, By.className("title"));

        processMenuItem(driver, By.id("subtab-AdminParentOrders"));

        processMenuItem(driver, By.id("subtab-AdminCatalog"));

        processMenuItem(driver, By.xpath("//span[contains(text(),'Клиенты')]"));

        processMenuItem(driver, By.id("subtab-AdminParentCustomerThreads"));

        processMenuItem(driver, By.id("subtab-AdminStats"));

        processMenuItem(driver, By.id("subtab-AdminParentModulesSf"));

        processMenuItem(driver, By.xpath("//span[contains(text(),'Design')]"));

        processMenuItem(driver, By.id("subtab-AdminParentShipping"));

        processMenuItem(driver, By.id("subtab-AdminParentPayment"));

        processMenuItem(driver, By.id("subtab-AdminInternational"));

        processMenuItem(driver, By.id("subtab-ShopParameters"));

        processMenuItem(driver, By.id("subtab-AdminAdvancedParameters"));

        driver.quit();
    }

    private static WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver",  new File(AdminPanelLoginB.class
                .getResource("/chromedriver-2.33.exe").getFile()).getPath());

        return new ChromeDriver();
    }

    private static void processMenuItem(WebDriver driver, By by) throws InterruptedException {
        WebElement element = driver.findElement(by);
        element.click();
        Thread.sleep(3000);
        String sectionNameBefore = driver.findElement(By.tagName("h2")).getText();
        System.out.println(sectionNameBefore);

        driver.navigate().refresh();
        String sectionNameAfter = driver.findElement(By.tagName("h2")).getText();
        if (!sectionNameBefore.equals(sectionNameAfter)) {
            System.out.println("Unexpected section name: " + sectionNameAfter + ", expected: " + sectionNameBefore);
        }
    }

}
