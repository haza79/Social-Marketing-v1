package com.german;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class login {

    public String dbUsername;
    public String dbPassword;
    public String dbFullName;
    public String dbEmail;

    WebDriver driver ;
    private String keyUrl = "http://haza.infinityfreeapp.com/login.php";
    ChromeOptions options = new ChromeOptions();

    login(){
//        System.setProperty("webdriver.chrome.driver", projectRoot+File.separator+"libs\\selenium\\chromedriver.exe");
        options.setHeadless(true);
    }

    public void setupDriver(){
        WebDriverManager.chromedriver().setup();
        System.out.println("setup driver ...");
        driver = new ChromeDriver(options);
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
            rowData.add(splitData[0]);//id
            rowData.add(splitData[1]);//user
            rowData.add(splitData[2]);//pass
            rowData.add(splitData[3]);//fullname
            rowData.add(splitData[4]);//email

            data.add(rowData);

        }

//        System.out.println(data);

        return data;
    }

    public boolean isUserMatchFromSQL(String username, String password){
        boolean isMatch = false;
        boolean isUserNull = false;
        boolean isPassNull = false;

        if (username.equals(null) || username.equals("") || username == "" || username == null){
            isUserNull = true;
        }else {
            isUserNull = false;
        }

        if (password.equals(null) || password.equals("") || password == "" || password == null){
            isPassNull = true;
        }else {
            isPassNull = false;
        }

        if (isUserNull == false && isPassNull == false) {

            driver.get("http://haza.infinityfreeapp.com/loginCheck.php");
            driver.findElement(By.xpath("/html/body/form/input[1]")).sendKeys(username);
            driver.findElement(By.xpath("/html/body/form/input[2]")).sendKeys(password);
            driver.findElement(By.xpath("/html/body/form/input[3]")).submit();
            String status = driver.findElement(By.xpath("//*[@id=\"status\"]")).getText();

            if (status.equals("success")){
                isMatch = true;
            }else {
                JOptionPane.showMessageDialog(null, "username or password is incorrect","login fail",JOptionPane.ERROR_MESSAGE);
                isMatch =false;
            }

        }

        driver.quit();
        return isMatch;

    }



    public boolean isUserMatch(String username, String password){

        boolean isMatch = false;
        boolean isUserNull = false;
        boolean isPassNull = false;

        if (username.equals(null) || username.equals("") || username == "" || username == null){
            isUserNull = true;
        }else {
            isUserNull = false;
        }

        if (password.equals(null) || password.equals("") || password == "" || password == null){
            isPassNull = true;
        }else {
            isPassNull = false;
        }

        if (isUserNull == false && isPassNull == false){
            ArrayList<ArrayList> data = showAllKey();
            for (int i = 0; i<data.size(); i++){
                if (data.get(i).get(1).equals(username)){
                    if (data.get(i).get(2).equals(password)){
                        System.out.println("match");
                        dbUsername = data.get(i).get(1).toString();
                        dbPassword = data.get(i).get(2).toString();
                        dbFullName = data.get(i).get(3).toString();
                        dbEmail = data.get(i).get(4).toString();

                        System.out.println("user info login");
                        System.out.println(dbUsername);
                        System.out.println(dbPassword);
                        System.out.println(dbFullName);
                        System.out.println(dbEmail);

                        isMatch = true;
                    }else {
                        System.out.println("don't math from password");
                        isMatch = false;
                        JOptionPane.showMessageDialog(null, "username or password is incorrect","login fail",JOptionPane.ERROR_MESSAGE);
                    }

                }else {
                    System.out.println("don't math from username");
                    isMatch = false;
//                    JOptionPane.showMessageDialog(null, "username or password is incorrect","login fail",JOptionPane.ERROR_MESSAGE);
                }
            }
        }else {
            System.out.println("null");
        }

        driver.quit();
        return isMatch;

    }




    public static void main(String[] args) {
        login login = new login();
        login.setupDriver();
        login.showAllKey();
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        System.out.println(login.isUserMatch(username,password));

    }

}
