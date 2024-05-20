package com.mycompany.testselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Selenium test for navigating to order page after interacting with a product
 * and clicking the "Mua ngay" button.
 * 
 * Author: thuyv
 */
public class TestSeleniumOrder {
    private static WebDriver driver;

    public static void initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        // Initialize WebDriver and login
        TestSeleniumLogin.login(driver);
        // Interact with the product
        WebElement productElement = driver.findElement(By.cssSelector("div.product-item"));
        productElement.click();
        System.out.println("Clicked on the product successfully.");
        // Click on the "Mua ngay" button
        WebElement buyNowButton = driver.findElement(By.cssSelector("button.btn.btn-success"));
        buyNowButton.click();
        System.out.println("Clicked on 'Mua ngay' button successfully.");
         // Fill in the order form
        WebElement fullnameInput = driver.findElement(By.id("fullname"));
        fullnameInput.sendKeys("Cao ABC");
        System.out.println("Entered full name.");

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("ABC@gmai.com");
        System.out.println("Entered email.");

        WebElement phoneNumberInput = driver.findElement(By.id("phone"));
        phoneNumberInput.sendKeys("0909090909");
        System.out.println("Entered phone number.");

        WebElement addressInput = driver.findElement(By.id("address"));
        addressInput.sendKeys("Quan 3");
        System.out.println("Entered address.");
         // Click the "Đặt hàng" button to submit the order
        WebElement orderButton = driver.findElement(By.cssSelector("button.btn.btn-gradient"));
        orderButton.click();
        System.out.println("Clicked on 'Đặt hàng' button successfully.");
        System.out.println("Navigated to order page successfully.");
       

        // Close the browser
//        driver.quit();
    }

    public static void main(String[] args) {
        initializeWebDriver();
    }
}
