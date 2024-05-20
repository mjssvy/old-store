package com.mycompany.testselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 *
 * @author ASUS
 */
public class TestSeleniumLogin {

    public static void login(WebDriver driver) {
        // Điều hướng đến trang web
        driver.get("http://54.254.131.235:4200/");
        
        // Điều hướng đến trang đăng nhập từ trang chủ
        driver.findElement(By.cssSelector("a.nav-link[routerLink='/login']")).click();
        // Điền thông tin đăng nhập
        driver.findElement(By.id("phoneNumber")).clear();
        driver.findElement(By.id("phoneNumber")).sendKeys("0909090909");

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("0909090909");
        
        driver.findElement(By.cssSelector("button.login-button[type='button']")).click();
        try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Login successfully");
    }

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        login(driver);
        driver.quit();
    }
}
