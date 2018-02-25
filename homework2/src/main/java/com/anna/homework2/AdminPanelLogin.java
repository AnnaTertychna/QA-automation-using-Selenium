package com.anna.homework2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AdminPanelLogin {
    public static void main(String[] arguments) throws Exception {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver-2.33.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        Thread.sleep(5000);

        driver.close();
        driver.quit();
    }
}
