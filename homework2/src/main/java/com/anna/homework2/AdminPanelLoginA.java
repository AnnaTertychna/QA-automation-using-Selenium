package com.anna.homework2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AdminPanelLoginA {
    public static void main(String[] arguments) throws Exception {
        WebDriver driver = getWebDriver();

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("webinar.test@gmail.com");

        WebElement password = driver.findElement(By.name("passwd"));
        password.sendKeys("Xcg7299bnSmMuRLp9ITw");

        WebElement button = driver.findElement(By.name("submitLogin"));
        button.submit();

        Thread.sleep(3000);

        WebElement employeeInfo = driver.findElement(By.id("employee_infos"));
        employeeInfo.click();

        Thread.sleep(3000);

        WebElement logOut = driver.findElement(By.id("header_logout"));
        logOut.click();

        Thread.sleep(3000);

        driver.quit();
    }

    private static WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver-2.33.exe");

        return new ChromeDriver();
    }
}
