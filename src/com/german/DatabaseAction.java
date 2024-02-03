package com.german;

import org.apache.commons.collections4.ListUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

public class DatabaseAction {
    String projectRoot = System.getProperty("user.dir");
    private String databaseURL ="jdbc:ucanaccess://"+projectRoot+"/db/number.accdb";
    private String derbyDatabaseURL ="jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";
    private Connection connection;
    private Connection derbyConnection;
    private Statement statement;
    private Statement derbyStatement;
    private ResultSet result;
    private ResultSet derbyResult;
    private String sql;
    private String derbySql;

    DatabaseAction() throws ClassNotFoundException, SQLException {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        connection = DriverManager.getConnection(databaseURL);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    public void showAllCountryCode(DefaultTableModel model, JTable table) throws SQLException {

        sql = "SELECT * FROM tb_number_format ";
        result = statement.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("ID");
            String countryCode = result.getString("country_code");
            String countryName = result.getString("country_name");

            Object[] data = new Object[]{
                    id,
                    countryCode,
                    countryName
            };
            model.addRow(data);

        }
        table.setModel(model);

    }

    private java.util.List<java.util.List<Integer>> partitionedList;

    public List<List<Integer>> getPartitionedList() {
        return partitionedList;
    }

    public void splitPartition(int amount, int chunkSize){



        ArrayList<String> largeList = new ArrayList<>();
        for (int i = 0; i<amount; i++){
            largeList.add(String.valueOf(i));
        }

        java.util.List<Integer> inputList = new ArrayList(largeList);

        partitionedList = ListUtils.partition(inputList, chunkSize);
//        System.out.println(partitionedList);
//        System.out.println(partitionedList.size());
//        for (int i = 0; i<partitionedList.size(); i++){
//            System.out.println(i+"] "+partitionedList.get(i).size());
//        }

    }

    public void setDataToTable(String number) throws SQLException {

        sql = "SELECT ID,department_id,name FROM tb_position";
        result = statement.executeQuery(sql);

        result.moveToInsertRow();
        result.updateInt("ID", 0);
        result.insertRow();

    }

    public void showAllSheet(DefaultTableModel model, JTable table) throws SQLException {
        model.setRowCount(0);

        sql = "SELECT * FROM tb_sheet";
        result = statement.executeQuery(sql);

        while (result.next()){

            int id = result.getInt("ID");
            String name = result.getString("sheet_name");

            Object[] data = new Object[]{
                    id,
                    name
            };

            model.addRow(data);




        }
        table.setModel(model);
    }

    public void showSheetDetailBySheetID(DefaultTableModel model, JTable table,String index) throws SQLException {
        model.setRowCount(0);

        sql = "SELECT * FROM tb_sheetDetail WHERE sheet_id = '"+index+"'  ";
        result = statement.executeQuery(sql);

        while (result.next()){

            int id = result.getInt("ID");
            String name = result.getString("phone_number");

            Object[] data = new Object[]{
                    id,
                    name
            };

            model.addRow(data);




        }
        table.setModel(model);
    }

    public ArrayList<String> getNumberPrefix(String countryID) throws SQLException {
        ArrayList<String> prefix = new ArrayList<>();

        sql = "SELECT * FROM tb_perfix WHERE country_id = '"+countryID+"' ";
//        Statement statement = connection.createStatement();
         result = statement.executeQuery(sql);

        while (result.next()){

            prefix.add(result.getString("perfix")) ;

        }
        System.out.println("prefix = "+prefix);
        return prefix;
    }

    public String getNumberCountryCode(String countryID) throws SQLException {

        String countryCode = "";
        sql = "SELECT * FROM tb_countryCode WHERE country_id = '"+countryID+"' ";
//        Statement statement = connection.createStatement();
         result = statement.executeQuery(sql);

        while (result.next()){

            countryCode=result.getString("country_code") ;

        }
        System.out.println("country code = "+countryCode);
        return countryCode;

    }


    public String getNumberFormat(String countryID) throws SQLException {
        String numberFormat = "";
        sql = "SELECT * FROM tb_format WHERE country_id = '"+countryID+"' ";
//        Statement statement =connection.createStatement();
         result = statement.executeQuery(sql);

        while (result.next()){

            numberFormat=result.getString("format") ;

        }
        return numberFormat;
    }

    public boolean isDuplicate(String phoneNumber) throws SQLException {
        boolean isDuplicate = false;
        sql = "SELECT * FROM tb_sheetDetail WHERE phone_number = '"+phoneNumber+"' ";
//        Statement statement =connection.createStatement();
         result = statement.executeQuery(sql);
        if (result.next() == true){
            isDuplicate = true;
        }else {
            isDuplicate = false;
        }
        System.out.println("is duplicate : "+isDuplicate);
        return isDuplicate;
    }

    public void addHistory(String phoneNumber) throws SQLException {

//        sql = "SELECT * FROM tb_sheetDetail";
//        result = statement.executeQuery(sql);
//
//        result.moveToInsertRow();
//        result.updateInt("ID", 0);
//        result.updateInt("sheet_id",1);
//        result.updateString("phone_number", phoneNumber);
//        result.updateDate("date_created",new java.sql.Date(System.currentTimeMillis()));
//        result.insertRow();
        String sql = "INSERT INTO tb_sheetDetail (sheet_id, phone_number, date_created) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1); // Assuming sheet_id is 1
        preparedStatement.setString(2, phoneNumber);
        preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
        int rowsAffected = preparedStatement.executeUpdate();

    }

    public void addHistory2(ArrayList<String> phoneNumber,int count,JProgressBar progressBar) throws SQLException {

        sql = "INSERT INTO tb_sheetDetail (sheet_id,phone_number)\n" +
                "VALUES ('1','0984477510');";
        Statement statement = connection.createStatement();
        result = statement.executeQuery(sql);




    }

    public void addHistoryToTextArea(String phoneNumber,JTextArea textArea){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
        String v1 = "[ "+timeStamp+" ]";
        String v2 = phoneNumber;
        textArea.append(v1+"\t"+v2+"\n");

    }

    public void showAllHistory(JTextArea textArea) throws SQLException {
        textArea.setText(null);
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-mm-yyyy hh-mm-ss");
        sql = "SELECT * FROM tb_sheetDetail";
//        Statement statement = connection.createStatement();
         result = statement.executeQuery(sql);


        while (result.next()){
//            String date = "[ "+String.valueOf(result.getDate("date_created"))+" ]";

            java.util.Date date ;
//            String dateformat;


            try {
//                date = DATE_FORMAT.format(result.getTimestamp("date_created"));
                date = result.getTimestamp("date_created");
//                System.out.println(result.getDate);
//                dateformat = DATE_FORMAT.format(date);
            }catch (NullPointerException e){
                date = null;
            }


            String phoneNumber = result.getString("phone_number");
            textArea.append("[ "+date+" ]\t"+phoneNumber+"\n");
        }

    }

    public void deleteAllHistory() throws SQLException {
        sql = "DELETE FROM tb_sheetDetail WHERE sheet_id = 1"; // Corrected SQL statement
        statement.executeUpdate(sql); // Use executeUpdate() for DELETE operations
    }



}
