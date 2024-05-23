/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testselenium;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author thuyv
 */
public class JSONReadOrder {
    private WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        TestSeleniumLogin.login(driver);
        // Interact with the product
        WebElement productElement = driver.findElement(By.cssSelector("div.product-item"));
        productElement.click();
        // Click on the "Mua ngay" button
        WebElement buyNowButton = driver.findElement(By.cssSelector("button.btn.btn-success"));
        buyNowButton.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    public void order(String fullname, String email, String phone,String address, String note){
        driver.findElement(By.id("fullname")).sendKeys(fullname);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("phone")).sendKeys(phone);
        driver.findElement(By.id("address")).sendKeys(address);
        driver.findElement(By.id("note")).sendKeys(note);
        driver.findElement(By.cssSelector("button.btn.btn-gradient")).click();
    }
    private JSONObject getTestCaseData(int testCaseIndex) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("D:\\demo-deploy\\TestSelenium\\TestSelenium\\src\\main\\java\\com\\mycompany\\testselenium\\order.json");
        JSONObject data = (JSONObject) parser.parse(reader);
        JSONArray testCases = (JSONArray) data.get("test_cases_order");
        return (JSONObject) testCases.get(testCaseIndex);
    }
    //Happy Cases
     //TC_038
    @Test
    public void test38_order_with_valid_credentials() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(0);
        String fullname = (String) testCase.get("fullname");
        String email = (String) testCase.get("email");
        String phone = (String) testCase.get("phone");
        String address = (String) testCase.get("address");
         String note = (String) testCase.get("note");
        order(fullname, email, phone, address,note);
    }
    //Negative Cases
     //TC_044
    @Test
    public void test44_order_with_blank_phone() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(1);
        String fullname = (String) testCase.get("fullname");
        String email = (String) testCase.get("email");
        String phone = (String) testCase.get("phone");
        String address = (String) testCase.get("address");
        String note = (String) testCase.get("note");
        order(fullname, email, phone, address, note);
    }
    //TC_045
    @Test
    public void test45_order_with_invaild_phone() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(2);
        String fullname = (String) testCase.get("fullname");
        String email = (String) testCase.get("email");
        String phone = (String) testCase.get("phone");
        String address = (String) testCase.get("address");
        String note = (String) testCase.get("note");
        order(fullname, email, phone, address, note);
    }
     public static void main(String[] args) {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        org.junit.runner.JUnitCore.main("com.mycompany.testselenium.JSONReadOrder");
    }
}
