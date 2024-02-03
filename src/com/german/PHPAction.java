package com.german;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PHPAction {

    Connection connection;
    encryption encryp = new encryption();

    licenseKey licenseKey = new licenseKey();
    public String dbUserID;
    public String dbUserUsername;
    public String dbUserPassword;
    public String dbUserFullName;
    public String dbUserEmail;
    public PHPAction(){
        registerDb();
    }

    private void registerDb() {
        String url = "jdbc:mysql://34.136.232.236:12577/pantheon";
        String username = "pantheon";
        String password = "n8RM9UxX62fHlK8WZqq3EzwK4YERQesD";

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            connection = DriverManager.getConnection(url, username, password);

            // Check if the connection is successful
            if (connection != null) {
                System.out.println("Connected to the database!");
//                connection.close(); // Close the connection when done
            } else {
                System.out.println("Failed to connect to the database!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed! Check output console");
            e.printStackTrace();
        }
    }

    public boolean isEmailAvailable(String email) throws SQLException {

        String query = "SELECT * FROM userData WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, encryp.encrypt(email));
        ResultSet resultSet = statement.executeQuery(); // Corrected this line

        if (resultSet.next()) {
            // Email exists in the database
            System.out.println("Email exists in the database.");
            return true;
        }
        System.out.println("Email does not exist in the database.");
        return false;
    }

    public void register(String username, String password, String fullName, String email) throws SQLException {
            username = encryp.encrypt(username);
            password = encryp.encrypt(password);
            fullName = encryp.encrypt(fullName);
            email = encryp.encrypt(email);

            String insertQuery = "INSERT INTO userData (username, password, fullname, email) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, fullName);
            preparedStatement.setString(4, email);

            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User data inserted successfully.");
            } else {
                System.out.println("Failed to insert user data.");
            }
    }

    public boolean login(String username, String password) throws SQLException {
        // Prepare SQL SELECT statement with placeholders
        String selectQuery = "SELECT * FROM userData WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        // Execute the SELECT statement
        ResultSet resultSet = preparedStatement.executeQuery();

        // Check if a row is returned
        if (resultSet.next()) {
            System.out.println("Login successful!");
            dbUserID = resultSet.getString("ID");
            dbUserUsername = resultSet.getString("username");
            dbUserPassword = resultSet.getString("password");
            dbUserFullName = resultSet.getString("fullname");
            dbUserEmail = resultSet.getString("email");
            return true;
            // You can retrieve user data from the result set if needed
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public String[] showDataByEmail(String email) throws SQLException {
        email = encryp.encrypt(email);
        String[] data  = new String[5];
        boolean isVisible = false;
        // Prepare SQL SELECT statement with placeholders
        String selectQuery = "SELECT * FROM userData WHERE email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, email);

        boolean status = false;
        // Execute the SELECT statement
        ResultSet resultSet = preparedStatement.executeQuery();

        // Check if a row is returned
        if (resultSet.next()) {
            System.out.println("Email exists in the database.");
            status = true;
            isVisible = true;
            // You can retrieve additional data from the result set if needed
        } else {
            System.out.println("Email does not exist in the database.");
            isVisible = false;
        }
        if (status){
            isVisible = true;
            data[0] = resultSet.getString("ID");
            data[1] = encryp.decrypt(resultSet.getString("username"));
            data[2] = encryp.decrypt(resultSet.getString("password"));
            data[3] = encryp.decrypt(resultSet.getString("fullname"));
            data[4] = encryp.decrypt(resultSet.getString("email"));

        }else {
            isVisible = false;
        }

        return data;

    }

    public boolean changePassword(String userID,String newPassword) throws SQLException {
//        userID = encryp.encrypt(userID);
        newPassword = encryp.encrypt(newPassword);
        String updateQuery = "UPDATE userData SET password = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, newPassword);
        preparedStatement.setString(2, userID);

        // Execute the UPDATE statement
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Password updated successfully.");
            return true;
        } else {
            System.out.println("Failed to update password.");
            return false;
        }
    }

    public boolean isLicenseActivate(String license) throws SQLException {
        license = encryp.encrypt(license);
        boolean isActivated = false;
        String selectQuery = "SELECT * FROM licenseKey WHERE `key` = ? AND isActivate = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, license);

        // Execute the SELECT statement
        ResultSet resultSet = preparedStatement.executeQuery();

        // Check if a row is returned
        if (resultSet.next()) {
            System.out.println("License key is valid and not activated.");
            return false;
            // You can retrieve additional data from the result set if needed
        } else {
            System.out.println("License key is either not found or already activated.");
            return true;
        }

    }

    public boolean activateLicense(String license,String userID,String activateTime,String expiredTime) throws SQLException {
        userID = encryp.encrypt(userID);
        license = encryp.encrypt(license);
        boolean isActivated = false;
        String updateQuery = "UPDATE licenseKey SET isActivate = 1, activateByUser = ?, activateTime = ?, expiredTime = ? WHERE `key` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, userID);
        preparedStatement.setString(2, activateTime);
        preparedStatement.setString(3, expiredTime);
        preparedStatement.setString(4, license);

        // Execute the UPDATE statement
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            isActivated = true;
            JOptionPane.showMessageDialog(null, "activate license success","license",JOptionPane.PLAIN_MESSAGE);
        }else {
            isActivated = false;
        }

        return isActivated;

        //2022-07-14 00:17:00
        //14-07-2022 00:07:46

    }

    public ArrayList<ArrayList> getUserActivateLicense(String userID) throws SQLException {
        userID = encryp.encrypt(userID);
        System.out.println("in get user activate license");

        ////div[@id='data']/div[1]/p[@id='dbID']
        ArrayList<ArrayList> data = new ArrayList<>();

        String selectQuery = "SELECT * FROM licenseKey WHERE activateByUser = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, userID);

        // Execute the SELECT statement
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            ArrayList<String> userData = new ArrayList<>();
            String dbID = resultSet.getString("ID");
            String dbKey = encryp.decrypt(resultSet.getString("key"));
            String dbDuration = encryp.decrypt(resultSet.getString("duration"));
            String dbIsActivate = resultSet.getString("isActivate");
            String dbActivateByUser = encryp.decrypt(resultSet.getString("activateByUser"));
            String dbActivateTime = resultSet.getString("activateTime");
            String dbExpiredTime = resultSet.getString("expiredTime");

            userData.add(dbID);
            userData.add(dbKey);
            userData.add(dbDuration);
            userData.add(dbIsActivate);
            userData.add(dbActivateByUser);
            userData.add(dbActivateTime);
            userData.add(dbExpiredTime);

            data.add(userData);
        }

//        System.out.println(data);
        System.out.println("data = "+data);
        return data;
    }

    public String[] getLicenseData(String key) throws SQLException {

        key = encryp.encrypt(key);
        String[] data = new String[7];
        String selectQuery = "SELECT * FROM licenseKey WHERE `key` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, key);

        // Execute the SELECT statement
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            System.out.println("<p id='status'>success</p>");

            // Retrieve data from the ResultSet
//            int id = resultSet.getInt("ID");
//            String keyD = resultSet.getString("key");
//            String duration = resultSet.getString("duration");
//            String isActivate = resultSet.getString("isActivate");
//            String activateByUser = resultSet.getString("activateByUser");
//            String activateTime = resultSet.getString("activateTime");
//            String expiredTime = resultSet.getString("expiredTime");

            data[0] = encryp.decrypt(String.valueOf(resultSet.getInt("ID")));
            data[1] = encryp.decrypt(resultSet.getString("key"));
            data[2] = encryp.decrypt(resultSet.getString("duration"));
            data[3] = encryp.decrypt(resultSet.getString("isActivate"));
            data[4] = encryp.decrypt(resultSet.getString("activateByUser"));
            data[5] = encryp.decrypt(resultSet.getString("activateTime"));
            data[6] = encryp.decrypt(resultSet.getString("expiredTime"));

        } else {
            System.out.println("<p id='status'>fail</p>");
        }


        return data;

    }

    private LocalDateTime finalExpiredTime = null;

    public LocalDateTime getUserLicenseExpired(String userID) throws SQLException {

//        userID = encryp.encrypt(userID);
        ArrayList<ArrayList> licenseDate = getUserActivateLicense(userID);
        System.out.println("license date "+licenseDate);
        if (licenseDate==null){
            System.out.println("license date = null");
            finalExpiredTime = null;

        }else {
            System.out.println("license date = not null "+licenseDate);
            ArrayList<Integer> totalDuration = new ArrayList<>();
            ArrayList<String> allStartdate = new ArrayList<>();
            ArrayList<String> allEnddate = new ArrayList<>();

            for (int i = 0; i<licenseDate.size(); i++){
                System.out.println(licenseDate.get(i)+"\n");

                String durationString = (String) licenseDate.get(i).get(2);
                String startDate = (String) licenseDate.get(i).get(5);
                String endDate = (String) licenseDate.get(i).get(6);

                if (licenseDateAndTime.getStartTime().isBefore(licenseDateAndTime.stringToDate(endDate))){
                    totalDuration.add(Integer.valueOf(durationString));
                    allStartdate.add(startDate);
                }


            }

            if (allStartdate.isEmpty()){
                finalExpiredTime = null;
            }else {
                finalExpiredTime = licenseDateAndTime.getFinalExpiredTime(allStartdate,totalDuration);
                System.out.println(finalExpiredTime);
            }

        }

        System.out.println("final expired from php action : "+finalExpiredTime);
        return finalExpiredTime;
    }

    public long[] getExpiredDuration(){

        LocalDateTime dateNow = licenseDateAndTime.getStartTime();
        long[] time = licenseDateAndTime.findBetweenStartDateAndEnd(dateNow,finalExpiredTime  );
        return time;

    }

    public ArrayList<ArrayList> getAllLicenseKey() throws SQLException {

        System.out.println("in get user activate license");

        ////div[@id='data']/div[1]/p[@id='dbID']
        ArrayList<ArrayList> data = new ArrayList<>();

        // Prepare SQL SELECT statement
        String selectQuery = "SELECT * FROM licenseKey";
        Statement statement = connection.createStatement();

        // Execute the SELECT statement
        ResultSet resultSet = statement.executeQuery(selectQuery);
        while (resultSet.next()) {
            ArrayList<String> userData = new ArrayList<>();

            // Retrieve data for each column
            String dbID = String.valueOf(resultSet.getInt("ID"));
            String dbKey = encryp.decrypt(resultSet.getString("key"));
            String dbDuration = encryp.decrypt(resultSet.getString("duration"));
            String dbIsActivate = resultSet.getString("isActivate");
            String dbActivateByUser = encryp.decrypt(resultSet.getString("activateByUser"));
            String dbActivateTime = resultSet.getString("activateTime");
            String dbExpiredTime = resultSet.getString("expiredTime");

            // Add data to the userData ArrayList
            userData.add(dbID);
            userData.add(dbKey);
            userData.add(dbDuration);
            userData.add(dbIsActivate);
            userData.add(dbActivateByUser);
            userData.add(dbActivateTime);
            userData.add(dbExpiredTime);

            // Add userData to the data ArrayList
            data.add(userData);
        }

        return data;
    }

    public void addNewLicenseKey(String keyName,String duration) throws SQLException {
        keyName = encryp.encrypt(keyName);
        duration = encryp.encrypt(String.valueOf(duration));
        String insertQuery = "INSERT INTO licenseKey (`ID`, `key`, `duration`) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

        // Set parameters for the INSERT statement
        preparedStatement.setNull(1, Types.NULL); // Assuming ID is auto-incremented
        preparedStatement.setString(2, keyName);
        preparedStatement.setString(3, duration);

        // Execute the INSERT statement
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the insertion was successful
        if (rowsAffected > 0) {
            System.out.println("Data inserted successfully.");
        } else {
            System.out.println("Failed to insert data.");
        }
    }

    public ArrayList<ArrayList> getAllUser() throws SQLException {

        System.out.println("in get user activate license");

        // Prepare SQL SELECT statement
        String selectQuery = "SELECT * FROM userData";
        Statement statement = connection.createStatement();
        ArrayList<ArrayList> data = new ArrayList<>();
        // Execute the SELECT statement
        ResultSet resultSet = statement.executeQuery(selectQuery);
        while (resultSet.next()) {
            ArrayList<String> userData = new ArrayList<>();

            // Retrieve data for each column
            String dbID = String.valueOf(resultSet.getInt("ID"));
            String dbUser = encryp.decrypt(resultSet.getString("username"));
            String dbPassword = encryp.decrypt(resultSet.getString("password"));
            String dbFullName = encryp.decrypt(resultSet.getString("fullname"));
            String dbEmail = encryp.decrypt(resultSet.getString("email"));

            // Decrypt data if necessary
            // For demonstration purposes, we assume decryption is done here
            // Replace this with your actual decryption logic
            // String decryptedUser = decrypt(dbUser);
            // String decryptedPassword = decrypt(dbPassword);
            // String decryptedFullName = decrypt(dbFullName);
            // String decryptedEmail = decrypt(dbEmail);

            // Add decrypted data to the userData ArrayList
            userData.add(dbID);
            userData.add(dbUser);
            userData.add(dbPassword);
            userData.add(dbFullName);
            userData.add(dbEmail);

            // Add userData to the data ArrayList
            data.add(userData);
        }


        return data;
    }

    public void deleteLicense(String licenseId) throws SQLException {
        String deleteQuery = "DELETE FROM licenseKey WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

        // Set parameter for the DELETE statement
        preparedStatement.setInt(1, Integer.parseInt(licenseId));

        // Execute the DELETE statement
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the deletion was successful
        if (rowsAffected > 0) {
            System.out.println("Deletion successful.");
        } else {
            System.out.println("No rows were deleted.");
        }
    }

    public boolean updateUserData(String ID,String username, String password, String fullname, String email) throws SQLException {

        boolean isSuccess = true;
        String updateQuery = "UPDATE userData SET username = ?, password = ?, fullname = ?, email = ? WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

        // Set parameters for the UPDATE statement
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, fullname);
        preparedStatement.setString(4, email);
        preparedStatement.setInt(5, Integer.parseInt(ID));

        // Execute the UPDATE statement
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the update was successful
        if (rowsAffected > 0) {
            System.out.println("Update successful.");
            return true;
        } else {
            System.out.println("No rows were updated.");
        }

        return false;

    }

    public void deleteUser(String id) throws SQLException {
        String deleteQuery = "DELETE FROM userData WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

        // Set parameter for the DELETE statement
        preparedStatement.setInt(1, Integer.parseInt(id));

        // Execute the DELETE statement
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the deletion was successful
        if (rowsAffected > 0) {
            System.out.println("Deletion successful.");
        } else {
            System.out.println("No rows were deleted.");
        }
    }

}
