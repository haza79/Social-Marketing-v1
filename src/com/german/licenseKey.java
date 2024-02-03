package com.german;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class licenseKey {
    WebDriver driver ;
    private String keyUrl = "http://haza.infinityfreeapp.com/index.php";
    String projectRoot = System.getProperty("user.dir");
    public void setupDriver(){
        System.out.println("setup driver ...");
        System.setProperty("webdriver.chrome.driver", projectRoot+File.separator+"libs\\selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver();
        System.out.println("setup driver success");
    }

    public ArrayList<ArrayList> showAllKey(){

        ArrayList<ArrayList> data = new ArrayList<>();



        driver.get(keyUrl);

        List<WebElement> optionCount = driver.findElements(By.xpath("/html/body/p"));
        System.out.println("element size = "+optionCount.size());

        for (int i = 1; i<= optionCount.size(); i++){
            ArrayList<String> rowData = new ArrayList<>();
            String path = "/html/body/p["+i+"]";
            String rawData = driver.findElement(By.xpath(path)).getText();
            String[] splitData = rawData.split(",");
            rowData.add(splitData[0]);
            rowData.add(splitData[1]);
            rowData.add(splitData[2]);

            data.add(rowData);

        }

        System.out.println(data);


        return data;
    }


    public static void main(String args[]) throws IOException {

        licenseKey licenseKey = new licenseKey();
        licenseKey.setupDriver();
        ArrayList<ArrayList> data = licenseKey.showAllKey();


        for (int i = 0; i<data.size(); i++){

            System.out.println(data.get(i));

        }



    }

}
