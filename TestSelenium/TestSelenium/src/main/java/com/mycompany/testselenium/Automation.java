/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testselenium;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
/**
 *
 * @author ASUS
 */
public class Automation {
    public static void main(String[] args) {
        //WebDriverManager.firefoxdriver().setup();
        //WebDriver driver = new FirefoxDriver();
        
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
    }
   
}
