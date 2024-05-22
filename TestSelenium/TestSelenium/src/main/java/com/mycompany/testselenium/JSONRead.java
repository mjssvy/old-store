/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testselenium;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author thuyv
 */
public class JSONRead {
   public static void main(String[] args) throws IOException, ParseException {

      //object of JSONParser
       JSONParser j = new JSONParser();

      // load json file to be read
       FileReader f = new FileReader("D:\\demo-deploy\\TestSelenium\\TestSelenium\\src\\main\\java\\com\\mycompany\\testselenium\\login.json");

      // parse json content
      Object o = j.parse(f);

      // convert parsing object to JSON object
       JSONObject detail = (JSONObject)o;

      // get values from JSON file
      String phone_number = (String)detail.get("phone_number");
      String password = (String)detail.get("password");
      // get values from JSON array
      
      // Initiate the Webdriver
      WebDriver driver = new ChromeDriver();

      // adding implicit wait of 12 secs
      driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);

      // Opening the webpage
      driver.get("http://localhost:4200/login");

      //Identify elements
        driver.findElement(By.id("phoneNumber")).clear();
        driver.findElement(By.id("phoneNumber")).sendKeys(phone_number);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button.login-button[type='button']")).click();
      // get values entered
      // quitting browser
//      driver.quit();
   }
}
