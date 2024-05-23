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


public class JSONReadLogin {

    private WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void login(String phoneNumber, String password) {
        driver.findElement(By.cssSelector("a.nav-link[routerLink='/login']")).click();
        driver.findElement(By.id("phoneNumber")).clear();
        driver.findElement(By.id("phoneNumber")).sendKeys(phoneNumber);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
         try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("button.login-button[type='button']")).click();
    }

    private JSONObject getTestCaseData(int testCaseIndex) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("D:\\demo-deploy\\TestSelenium\\TestSelenium\\src\\main\\java\\com\\mycompany\\testselenium\\login.json");
        JSONObject data = (JSONObject) parser.parse(reader);
        JSONArray testCases = (JSONArray) data.get("test_cases_login");
        return (JSONObject) testCases.get(testCaseIndex);
    }

    // Happy Cases
    // TC_01
    @Test
    public void test1_login_with_valid_credentials() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(0);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);

    }

     //Negative Cases
     //TC_014
    @Test
    public void test14_login_with_blank_phone_and_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(1);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);
  
    }

     //TC_04
    @Test
    public void test4_login_with_empty_phone_and_valid_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(2);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);

//       
    }

    // TC_05
    @Test
    public void test5_login_with_valid_phone_and_empty_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(3);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);

      
    }

     //TC_03
    @Test
    public void test2_login_with_valid_phone_and_invalid_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(4);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);

        
    }

     //TC_03
    @Test
    public void test3_login_with_uncorrect_phone_and_correct_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(5);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);

       
    }

    // TC_013
    @Test
    public void test13_login_with_invalid_phone_and_invalid_password() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(6);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);
        
    }
    // TC_07
    @Test
    public void test7_login_with_invalid_phone() throws IOException, ParseException {
        JSONObject testCase = getTestCaseData(7);
        String phone_number = (String) testCase.get("phone_number");
        String password = (String) testCase.get("password");
        login(phone_number, password);
        
    }

    public static void main(String[] args) {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        org.junit.runner.JUnitCore.main("com.mycompany.testselenium.JSONReadLogin");
    }
}
