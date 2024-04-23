/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testselenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 *
 * @author ASUS
 */
public class Chorme {
@Test
public void LaunchChrome_Method1() {
 
 ChromeOptions options = new ChromeOptions();
 options.addArguments("disable-infobars");
 
//Thêm dòng code này để mở trình duyệt toàn màn hình)
 options.addArguments("--start-maximized"); 

 WebDriver driver = new ChromeDriver();
 driver.get("http://www.google.com");
 }
}

