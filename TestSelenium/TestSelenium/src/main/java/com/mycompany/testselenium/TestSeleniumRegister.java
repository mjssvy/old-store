package com.mycompany.testselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestSeleniumRegister {
    private static WebDriver driver;

    public static void initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Navigate to the registration page
        driver.get("http://52.221.229.25:4200/register/");

        // Fill in the registration form
        driver.findElement(By.id("phoneNumber")).sendKeys("0808080808");
        driver.findElement(By.id("password")).sendKeys("0808080808");
        driver.findElement(By.id("retype-password")).sendKeys("0808080808");
        driver.findElement(By.id("full-name")).sendKeys("Nguyen ABC");
        driver.findElement(By.id("date-of-birth")).sendKeys("08-08-2000");
        driver.findElement(By.id("address")).sendKeys("hcm");
      
        // Submit the registration form
         driver.findElement(By.cssSelector("button.register-button")).click();
        System.out.println("Registration form filled successfully.");

        // Close the browser
//        driver.quit();
    }

    public static void main(String[] args) {
        initializeWebDriver();
    }
}
