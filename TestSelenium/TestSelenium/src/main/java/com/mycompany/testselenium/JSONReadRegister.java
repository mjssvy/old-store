/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testselenium;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileReader;
import java.io.IOException;

public class JSONReadRegister {
    private WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/register");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void register(String phoneNumber, String password, String retypePassword, String fullName, String dateOfBirth, String address) {
        driver.findElement(By.id("phoneNumber")).sendKeys(phoneNumber);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("retype-password")).sendKeys(retypePassword);
        driver.findElement(By.id("full-name")).sendKeys(fullName);
        driver.findElement(By.id("date-of-birth")).sendKeys(dateOfBirth);
        driver.findElement(By.id("address")).sendKeys(address);
        driver.findElement(By.cssSelector("button.register-button")).click();
    }

    private JSONObject getTestCaseData(int testCaseIndex) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("D:\\demo-deploy\\TestSelenium\\TestSelenium\\src\\main\\java\\com\\mycompany\\testselenium\\register.json");
        JSONObject data = (JSONObject) parser.parse(reader);
        JSONArray testCases = (JSONArray) data.get("test_cases_Register");
        return (JSONObject) testCases.get(testCaseIndex);
    }

     //Happy Cases
     //TC_15
    @Test
    public void test15_register_with_valid_credentials() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(0);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    // Negative Cases
    // TC_30
    @Test
    public void test30_register_with_account_already_exists() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(1);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    // TC_016
    @Test
    public void test16_register_with_blank_phonenumber() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(2);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    // TC_023
    @Test
    public void test23_register_with_blank_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(3);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    // TC_024
    @Test
    public void test24_register_with_blank_retype_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(4);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    // TC_025
    @Test
    public void test25_register_with_incorrect_retypepassword() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(5);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
// TC_021
    @Test
    public void test121_register_with_invaid_phonenumber() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(6);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    @Test
    public void test121_register_with_invaid_phonenumber() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(7);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    @Test
    public void test121_register_with_invaid_phonenumber() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(8);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    @Test
    public void test121_register_with_invaid_phonenumber() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(9);
        String phoneNumber = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        String retypePassword = (String) testCase.get("retype-password");
        String fullName = (String) testCase.get("full-name");
        String dateOfBirth = (String) testCase.get("date-of-birth");
        String address = (String) testCase.get("address");
        register(phoneNumber, password, retypePassword, fullName, dateOfBirth, address);
    }
    

    public static void main(String[] args) {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        org.junit.runner.JUnitCore.main("com.mycompany.testselenium.JSONReadRegister");
    }
}
