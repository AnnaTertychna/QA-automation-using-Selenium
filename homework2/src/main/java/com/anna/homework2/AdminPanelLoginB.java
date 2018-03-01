package com.anna.homework2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class AdminPanelLoginB {
    public static void main(String[] arguments) throws Exception {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver-2.33.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("webinar.test@gmail.com");

        WebElement password = driver.findElement(By.name("passwd"));
        password.sendKeys("Xcg7299bnSmMuRLp9ITw");
        Thread.sleep(3000);

        WebElement button = driver.findElement(By.name("submitLogin"));
        button.submit();
        Thread.sleep(3000);

        WebElement dashboard = driver.findElement (By.className("title"));
        dashboard.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement orders = driver.findElement(By.id("subtab-AdminParentOrders"));
        orders.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement catalog = driver.findElement(By.id("subtab-AdminCatalog"));
        catalog.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement client = driver.findElement(By.xpath("//span[contains(text(),'Клиенты')]"));
        client.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement custemerServis = driver.findElement(By.id("subtab-AdminParentCustomerThreads"));
        custemerServis.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement statistics = driver.findElement(By.id("subtab-AdminStats"));
        statistics.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement modules = driver.findElement(By.id("subtab-AdminParentModulesSf"));
        modules.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement design = driver.findElement(By.xpath("//span[contains(text(),'Design')]"));
        design.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement schipping = driver.findElement(By.id("subtab-AdminParentShipping"));
        schipping.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement payment = driver.findElement(By.id("subtab-AdminParentPayment"));
        payment.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement international = driver.findElement(By.id("subtab-AdminInternational"));
        international.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement schopParametrs = driver.findElement(By.id("subtab-ShopParameters"));
        schopParametrs.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        WebElement configuration = driver.findElement(By.id("subtab-AdminAdvancedParameters"));
        configuration.click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.tagName("h2")).getText());

        driver.quit();
    }

}
