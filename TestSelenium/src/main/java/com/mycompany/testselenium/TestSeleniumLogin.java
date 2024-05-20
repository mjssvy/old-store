/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
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

    private static WebDriver driver;

    public static void initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options); // Sử dụng biến cấp độ lớp thay vì tạo một biến mới
        driver.manage().window().maximize();

        // Điều hướng đến trang web
        driver.get("http://52.221.229.25:4200/");
        try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // Điều hướng đến trang đăng nhập từ trang chủ
        driver.findElement(By.cssSelector("a.nav-link[routerLink='/login']")).click();
        //Điền thông tin đăng nhập
        driver.findElement(By.id("phoneNumber")).clear();
        driver.findElement(By.id("phoneNumber")).sendKeys("123456789");

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("123456789");
        driver.findElement(By.cssSelector("button.login-button[type='button']")).click();
        System.out.println("Login successfully");

    }

    public static void main(String[] args) {
        initializeWebDriver();
    }
}
