package com.german;

import org.apache.commons.lang3.RandomStringUtils;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class LicenseManager extends JFrame{
    private JPanel panelMain;
    private JPanel panelCard;
    private JPanel panelLicense;
    private JTable tbLicense;
    private JSpinner spnYears;
    private JSpinner spnMonths;
    private JSpinner spnDays;
    private JSpinner spnHours;
    private JButton addNewButton;
    private JTextField tfLicenseKey;
    private JButton licenseButton;
    private JButton userDataButton;
    private JPanel panelUser;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfFullname;
    private JTextField tfEmail;
    private JTable tbUser;
    private JTextField textField5;
    private JTextField textField6;
    private JButton addNewUserButton;
    private JButton generateKeyButton;
    private JLabel lbLIcenseManager;
    private JLabel lbYears;
    private JLabel lbMonths;
    private JLabel lbDays;
    private JLabel lbHours;
    private JLabel lblicensekey;
    private JLabel lbAlllicensekey;
    private JLabel lbSearch;
    private JLabel lbThai;
    private JLabel blEnglish;
    private JLabel lbusername;
    private JLabel lbpassword;
    private JLabel lbfullname;
    private JLabel lbemail;
    private JLabel lbSh;
    private JLabel Alluser;
    private JCheckBox alreadyUsedCheckBox;
    private JCheckBox neverUsedCheckBox;
    private JRadioButton allRadioButton;
    private JTextField tfUserBy;
    private JSpinner spnReportYear;
    private JRadioButton allRadioButton1;
    private JSpinner spnReportMonth;
    private JSpinner spnReportDay;
    private JSpinner spnReportHour;
    private JRadioButton allRadioButton2;
    private JTextField tfReportFrom;
    private JTextField tfReportTo;
    private JButton startReportButton;
    private JButton btnSaveUserData;
    private JTextField tfUserID;
    private JButton deleteButton;
    private JButton clearButton;
    private JPanel lbDuration;

    private JLabel lbReport;
    private JLabel lbUsedby;
    private JLabel lbYear;
    private JLabel lbMonth;
    private JLabel lbDay;
    private JLabel lbHour;
    private JLabel lbSelect;
    private JLabel lbFrom;
    private JLabel lbTo;
    private JLabel lbDuration2;
    private JButton excelButton;
    private JButton updateButton;
    private JSpinner spnCount;
    private JButton updateButton1;
    private JLabel lbCount;
    String projectRoot = System.getProperty("user.dir");
    DefaultTableModel modelLicenseTable = new DefaultTableModel();
    DefaultTableModel modelUserTable = new DefaultTableModel();

    String[] columnNameLicense = {"id","key","duration","is activate","activate by user","activate time","expired time","action"};
    String[] columnNameUser = {"id","username","password","fullname","email","remaining use time","total usage time"};

    PHPAction phpAction;
    licenseDateAndTime ldt = new licenseDateAndTime();
    encryption encryp = new encryption();

    SpinnerModel modelYear = new SpinnerNumberModel(0, 0, null, 1);
    SpinnerModel modelMonths = new SpinnerNumberModel(0, 0, null, 1);
    SpinnerModel modelDays = new SpinnerNumberModel(0, 0, null, 1);
    SpinnerModel modelHours = new SpinnerNumberModel(0, 0, null, 1);

    SpinnerModel modelCount = new SpinnerNumberModel(1, 1, null, 1);

    ArrayList<LocalDateTime> userExpired = new ArrayList<>();
    ArrayList<long[]> userDuration = new ArrayList<>();



    LicenseManager() throws SQLException {
        LoadWebsite.loadWebsite();

        Timer t = new Timer(1000, updateClockAction);

        pack();

        setIconImage(new ImageIcon(projectRoot+File.separator+"img"+File.separator+"Social MarketingV1.png").getImage());
        setTitle("License Manager");

        spnYears.setModel(modelYear);
        spnMonths.setModel(modelMonths);
        spnDays.setModel(modelDays);
        spnHours.setModel(modelHours);
        spnCount.setModel(modelCount);

        final TableRowSorter<TableModel> sorterLicense = new TableRowSorter<TableModel>(modelLicenseTable);
        tbLicense.setRowSorter(sorterLicense);

        final TableRowSorter<TableModel> sorterUser = new TableRowSorter<TableModel>(modelUserTable);
        tbUser.setRowSorter(sorterUser);

        enableUserBy(allRadioButton.isSelected());
        enableDuration(allRadioButton1.isSelected());
        enableSelect(allRadioButton2.isSelected());

        setSize(800,600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelMain);
        setVisible(true);

        phpAction = new PHPAction();

        modelLicenseTable.setColumnIdentifiers(columnNameLicense);
        modelUserTable.setColumnIdentifiers(columnNameUser);
        tbLicense.setModel(modelLicenseTable);
        tbUser.setModel(modelUserTable);

        showAllLicense();
        showAllUser();

        System.out.println(phpAction.getAllUser());


        for (int i = 0; i<tbUser.getRowCount(); i++){

            String id = tbUser.getValueAt(i,0).toString();
            LocalDateTime expiredTime = phpAction.getUserLicenseExpired(id);
            userExpired.add(expiredTime);
            if (expiredTime==null){
                userDuration.add(null);
            }else {
                long[] temp = phpAction.getExpiredDuration();
                userDuration.add(temp);
            }


            if (expiredTime==null){

                modelUserTable.setValueAt("expired",i,6);

            }else {

                String date = ldt.dateToString(expiredTime);
                modelUserTable.setValueAt(date,i,6);

            }



        }

        System.out.println(userDuration);

        t.start();

        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = (int) spnYears.getValue();
                int month = (int) spnMonths.getValue();
                int day = (int) spnDays.getValue();
                int hour = (int) spnHours.getValue();

                int duration = 0;
                duration+=ldt.yearToHour(year);
                duration+=ldt.monthToHour(month);
                duration+=ldt.dayToHour(day);
                duration+=hour;

                int count = (int) spnCount.getValue();
                boolean onetime = false;
                for (int i = 0; i<count; i++){
                    if (onetime == false){
                        String pwd = tfLicenseKey.getText();
                        try {
                            phpAction.addNewLicenseKey(pwd, String.valueOf(duration));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        onetime=true;
                    }else {
                        String key = randomKey();
                        try {
                            phpAction.addNewLicenseKey(key, String.valueOf(duration));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }


                }
//                String pwd = tfLicenseKey.getText();
//                phpAction.addNewLicenseKey(pwd, String.valueOf(duration));



                generateKeyButton.doClick();
                try {
                    showAllLicense();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = randomKey();
                System.out.println(key);
                System.out.println(key.length());
                tfLicenseKey.setText(key);
//                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//                boolean isDuplicate = false;
//
//                String pwd = RandomStringUtils.random( 20, characters );
//
//                for (int count = 0; count < modelLicenseTable.getRowCount(); count++){
//
//                    String tableData = modelLicenseTable.getValueAt(count, 1).toString();
//
//                    if (pwd.equals(tableData)){
//
//                        isDuplicate=true;
//
//                        while (!pwd.equals(tableData)){
//
//
//                            pwd = RandomStringUtils.random( 20, characters );
//
//                        }
//
//                    }else {
//
//                        isDuplicate = false;
//                        tfLicenseKey.setText(pwd);
//
//                    }
//
//                }


            }
        });


        lbThai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    loadLanguage("thai");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        blEnglish.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    loadLanguage("english");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });


        licenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCard.removeAll();
                panelCard.add(panelLicense);
                panelCard.repaint();
                panelCard.revalidate();
            }
        });
        userDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCard.removeAll();
                panelCard.add(panelUser);
                panelCard.repaint();
                panelCard.revalidate();
            }
        });


        tbLicense.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int col = tbLicense.columnAtPoint(e.getPoint());
                int row = tbLicense.rowAtPoint(e.getPoint());
                if (col == 7){
                    int result = JOptionPane.showConfirmDialog(panelMain,"Sure? You want to Delete?\n"+tbLicense.getValueAt(row,1), "action",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        //yes
//                        String id = (String) tbLicense.getValueAt(row,0);
                        try {
                            phpAction.deleteLicense(tbLicense.getValueAt(row,0).toString());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            showAllLicense();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }else if (result == JOptionPane.NO_OPTION){

                    }
                }

//                String id = tbLicense.getValueAt(row,0).toString();
//                System.out.println("id = "+id);

            }
        });


        tbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int row = tbUser.rowAtPoint(e.getPoint());
                String id = tbUser.getValueAt(row,0).toString();
                String username = tbUser.getValueAt(row,1).toString();
                String password = tbUser.getValueAt(row,2).toString();
                String fullname = tbUser.getValueAt(row,3).toString();
                String email = tbUser.getValueAt(row,4).toString();

                tfUserID.setText(id);
                tfUsername.setText(username);
                tfPassword.setText(password);
                tfFullname.setText(fullname);
                tfEmail.setText(email);

            }
        });

        btnSaveUserData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = tfUserID.getText();
                String username = tfUsername.getText();
                String password = tfPassword.getText();
                String fullname = tfFullname.getText();
                String email = tfEmail.getText();

                int result = JOptionPane.showConfirmDialog(panelMain,"Sure? You want to Update?\n"+username, "action",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    //yes
                    boolean isSuccess = false;
                    try {
                        isSuccess = phpAction.updateUserData(id,encryp.encrypt(username),encryp.encrypt(password),encryp.encrypt(fullname),encryp.encrypt(email));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (isSuccess == true){
                        JOptionPane.showMessageDialog(panelMain,"success");
                    }else {
                        JOptionPane.showMessageDialog(panelMain,"fail");
                    }
                    clearUserScreen();
                    try {
                        showAllUser();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }else if (result == JOptionPane.NO_OPTION){

                }



            }
        });

        addNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = tfUsername.getText();
                String password = tfPassword.getText();
                String fullname = tfUsername.getText();
                String email = tfEmail.getText();

                int result = JOptionPane.showConfirmDialog(panelMain,"Sure? You want to add new user?\n"+username, "action",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    //yes
                    try {
                        phpAction.register(username,password,fullname,email);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    clearUserScreen();
                    try {
                        showAllUser();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }else if (result == JOptionPane.NO_OPTION){

                }


            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                int result = JOptionPane.showConfirmDialog(panelMain,"Sure? You want to delete user?\n"+tfUsername.getText(), "action",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    //yes
                    try {
                        phpAction.deleteUser(tfUserID.getText());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    clearUserScreen();
                    try {
                        showAllUser();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }else if (result == JOptionPane.NO_OPTION){

                }

            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearUserScreen();
            }
        });
        allRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableUserBy(allRadioButton.isSelected());
            }
        });
        allRadioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableDuration(allRadioButton1.isSelected());
            }
        });
        allRadioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableSelect(allRadioButton2.isSelected());
            }
        });
//        startReportButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                ArrayList<String> licenseKeyForPrint = new ArrayList<>();
//                ArrayList<String> durationForPrint = new ArrayList<>();
//
//                ArrayList<ArrayList> data = phpAction.getAllLicenseKey();
//                if (alreadyUsedCheckBox.isSelected()){
//
//
//
//                    for (int i = 0; i<data.size(); i++){
//                        String isActivate = data.get(i).get(3).toString();
//                        if (isActivate.equals("1")){
//                            licenseKeyForPrint.add(data.get(i).get(1).toString());
//                        }
//
//
//                    }
//
//
//
//
//                }
//
//
//            }
//        });


        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    phpAction.connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }));


        excelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    ArrayList<ArrayList> arrLicenseData = phpAction.getAllLicenseKey();
                    ArrayList<ArrayList> arrUserData = phpAction.getAllUser();

                    Workbook wb = new HSSFWorkbook();

                    CreationHelper createHelper = wb.getCreationHelper();
                    Sheet sheet = wb.createSheet("license data");

                    Row hrow = sheet.createRow((short) 0);
                    hrow.createCell(0).setCellValue("license key");
                    hrow.createCell(1).setCellValue("duration");
                    hrow.createCell(2).setCellValue("is activate");
                    hrow.createCell(3).setCellValue("activate by user");
                    hrow.createCell(4).setCellValue("start time");
                    hrow.createCell(5).setCellValue("end time");
                    hrow.createCell(6).setCellValue("duration date format");

                    for (int i = 0; i<arrLicenseData.size(); i++){
                        Row row = sheet.createRow((short) i+1);
                        for (int j = 1; j<7; j++){
                            row.createCell(j-1).setCellValue(arrLicenseData.get(i).get(j).toString());
                            int duration = Integer.parseInt(arrLicenseData.get(i).get(2).toString());
                            long[] expiredDuration = ldt.findBetweenStartDateAndEndNonStatic(ldt.getStartTimeNonStatic(),ldt.getStartTimeNonStatic().plusHours(duration));
//                            emptyDateTime.plusHours((Long) arrLicenseData.get(i).get(2));
                            row.createCell(6).setCellValue(expiredDuration[0]+"-"+expiredDuration[1]+"-"+expiredDuration[2]+" "+expiredDuration[3]+":"+expiredDuration[4]+":"+expiredDuration[5]);
                        }

                    }




                    Sheet sheet2 = wb.createSheet("user data");
                    Row hrow2 = sheet2.createRow((short) 0);
                    hrow2.createCell(0).setCellValue("id");
                    hrow2.createCell(1).setCellValue("username");
                    hrow2.createCell(2).setCellValue("password");
                    hrow2.createCell(3).setCellValue("full name");
                    hrow2.createCell(4).setCellValue("email");
                    for (int i = 0; i<arrUserData.size(); i++){
                        Row row = sheet2.createRow((short) i+1);
                        for (int j = 0; j<5; j++){
                            row.createCell(j).setCellValue(arrUserData.get(i).get(j).toString());
                        }

                    }



// Write the output to a file
                    FileOutputStream fileOut = new FileOutputStream(projectRoot+File.separator+"report"+File.separator+"report.xls");
                    wb.write(fileOut);
                    fileOut.close();
                    Desktop.getDesktop().open(new File(projectRoot+File.separator+"report"+File.separator+"report.xls"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        textField6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                String text = textField6.getText();
                if (text.trim().length() == 0) {
                    sorterLicense.setRowFilter(null);
                } else {
                    sorterLicense.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }

            }
        });
        textField5.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                super.keyReleased(e);

                String text = textField5.getText();
                if (text.trim().length() == 0) {
                    sorterUser.setRowFilter(null);
                } else {
                    sorterUser.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.stop();

                userExpired = new ArrayList<>();
                userDuration = new ArrayList<>();
                try {
                    showAllUser();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                for (int i = 0; i<tbUser.getRowCount(); i++){

                    String id = tbUser.getValueAt(i,0).toString();
                    LocalDateTime expiredTime = null;
                    try {
                        expiredTime = phpAction.getUserLicenseExpired(id);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    userExpired.add(expiredTime);
                    if (expiredTime==null){
                        userDuration.add(null);
                    }else {
                        long[] temp = phpAction.getExpiredDuration();
                        userDuration.add(temp);
                    }


                    if (expiredTime==null){

                        modelUserTable.setValueAt("expired",i,6);

                    }else {

                        String date = ldt.dateToString(expiredTime);
                        modelUserTable.setValueAt(date,i,6);

                    }



                }

                t.restart();

            }
        });
        updateButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    showAllLicense();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public void showAllLicense() throws SQLException {
        modelLicenseTable.setRowCount(0);
        ArrayList<ArrayList> data = phpAction.getAllLicenseKey();
        for (int i = 0; i<data.size(); i++){

            Object[] dataObj = {
                data.get(i).get(0),
                    data.get(i).get(1),
                    data.get(i).get(2),
                    data.get(i).get(3),
                    data.get(i).get(4),
                    data.get(i).get(5),
                    data.get(i).get(6),
                    "delete"
            };

            modelLicenseTable.addRow(dataObj);
        }
        tbLicense.setModel(modelLicenseTable);
    }

    public void showAllUser() throws SQLException {
        modelUserTable.setRowCount(0);
        ArrayList<ArrayList> data = phpAction.getAllUser();
        for (int i = 0; i<data.size(); i++){

            Object[] dataObj = {
                    data.get(i).get(0),
                    data.get(i).get(1),
                    data.get(i).get(2),
                    data.get(i).get(3),
                    data.get(i).get(4)
            };

            modelUserTable.addRow(dataObj);
        }
        tbUser.setModel(modelUserTable);


    }

    public static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

    private Properties read(Path file ) throws IOException {
        final var properties = new Properties();

        try( final var in = new InputStreamReader(
                new FileInputStream( file.toFile() ), StandardCharsets.UTF_8 ) ) {
            properties.load( in );
        }

        return properties;
    }

    private void loadLanguage(String languageInput) throws IOException {


        Properties language = new Properties();

        if (languageInput.equals("thai")){
            language = read(Path.of(projectRoot+File.separator+"src"+File.separator+"licenseManagerTH.properties"));
        }else {
            language = read(Path.of(projectRoot+File.separator+"src"+File.separator+"licenseManagerEN.properties"));
        }

        licenseButton.setText(language.getProperty("licenseButton"));
        userDataButton.setText(language.getProperty("userDataButton"));
        lbLIcenseManager.setText(language.getProperty("lbLIcenseManager"));
        lbYears.setText(language.getProperty("lbYears"));
        lbMonths.setText(language.getProperty("lbMonths"));
        lbDays.setText(language.getProperty("lbDays"));
        lbHours.setText(language.getProperty("lbHours"));
        lblicensekey.setText(language.getProperty("lblicensekey"));
        generateKeyButton.setText(language.getProperty("generateKeyButton"));
        addNewButton.setText(language.getProperty("addNewButton"));
        lbAlllicensekey.setText(language.getProperty("lbAlllicensekey"));
        lbSearch.setText(language.getProperty("lbSearch"));
        lbThai.setText(language.getProperty("lbThai"));
        blEnglish.setText(language.getProperty("blEnglish"));
        lbusername.setText(language.getProperty("lbusername"));
        lbpassword.setText(language.getProperty("lbpassword"));
        lbfullname.setText(language.getProperty("lbfullname"));
        lbemail.setText(language.getProperty("lbemail"));
        addNewUserButton.setText(language.getProperty("addNewUserButton"));
        Alluser.setText(language.getProperty("Alluser"));
        lbSh.setText(language.getProperty("lbSh"));
        lbReport.setText(language.getProperty("lbReport"));
        alreadyUsedCheckBox.setText(language.getProperty("alreadyUsedCheckBox"));
        neverUsedCheckBox.setText(language.getProperty("neverUsedCheckBox"));
        lbUsedby.setText(language.getProperty("lbUsedby"));
        allRadioButton.setText(language.getProperty("allRadioButton"));
        lbDuration2.setText(language.getProperty("lbDuration2"));
        allRadioButton1.setText(language.getProperty("allRadioButton1"));
        lbYear.setText(language.getProperty("lbYear"));
        lbMonth.setText(language.getProperty("lbMonth"));
        lbDay.setText(language.getProperty("lbDay"));
        lbHour.setText(language.getProperty("lbHour"));
        lbSelect.setText(language.getProperty("lbSelect"));
        allRadioButton2.setText(language.getProperty("allRadioButton2"));
        lbFrom.setText(language.getProperty("lbFrom"));
        lbTo.setText(language.getProperty("lbTo"));
        lbCount.setText(language.getProperty("lbCount"));
        updateButton1.setText(language.getProperty("updateButton1"));
        excelButton.setText(language.getProperty("excelButton"));
        clearButton.setText(language.getProperty("clearButton"));
        deleteButton.setText(language.getProperty("deleteButton"));
        btnSaveUserData.setText(language.getProperty("btnSaveUserData"));
        updateButton.setText(language.getProperty("updateButton"));




    }

    private void clearUserScreen(){
        tfUserID.setText("");
        tfUsername.setText("");
        tfPassword.setText("");
        tfFullname.setText("");
        tfEmail.setText("");
    }

    private void enableUserBy(boolean enable){
        tfUserBy.setEnabled(!enable);
    }

    private void enableDuration(boolean enable){
        spnReportYear.setEnabled(!enable);
        spnReportMonth.setEnabled(!enable);
        spnReportDay.setEnabled(!enable);
        spnReportHour.setEnabled(!enable);
    }

    private void enableSelect(boolean enable){

        tfReportFrom.setEnabled(!enable);
        tfReportTo.setEnabled(!enable);

    }

    ActionListener updateClockAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Assumes clock is a custom component

            for (int i = 0; i<userDuration.size(); i++){

                if (userDuration.get(i) == null){//null

                    modelUserTable.setValueAt("expired",i,5);

                }else {//not null

//                    long[] expiredDuration = userDuration.get(i);
                    LocalDateTime dateNow = licenseDateAndTime.getStartTime();
                    long[] time = licenseDateAndTime.findBetweenStartDateAndEnd(dateNow,userExpired.get(i)  );
                    long[] expiredDuration = time;
                    if (expiredDuration[0]<=0 && expiredDuration[1]<=0
                            && expiredDuration[2] <= 0 && expiredDuration[3] <=0
                            && expiredDuration[4] <= 0 && expiredDuration[5] <= 0){
                        //expired
                        modelUserTable.setValueAt("expired",i,5);

                    }else {

                        String expiredCountDown = expiredDuration[0]+"-"+expiredDuration[1]+"-"+expiredDuration[2]+" "+expiredDuration[3]+":"+expiredDuration[4]+":"+expiredDuration[5];
                        modelUserTable.setValueAt(expiredCountDown,i,5);

                    }

                }

            }










        }
    };

    ActionListener updateData = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Assumes clock is a custom component

            userExpired = new ArrayList<>();
            userDuration = new ArrayList<>();
            for (int i = 0; i<tbUser.getRowCount(); i++){

                String id = tbUser.getValueAt(i,0).toString();
                LocalDateTime expiredTime = null;
                try {
                    expiredTime = phpAction.getUserLicenseExpired(id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                userExpired.add(expiredTime);
                if (expiredTime==null){
                    userDuration.add(null);
                }else {
                    long[] temp = phpAction.getExpiredDuration();
                    userDuration.add(temp);
                }


                if (expiredTime==null){

                    modelUserTable.setValueAt("expired",i,6);

                }else {

                    String date = ldt.dateToString(expiredTime);
                    modelUserTable.setValueAt(date,i,6);

                }



            }


        }
    };

    public String randomKey(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        LocalDateTime now = LocalDateTime.now();
        String day = String.valueOf(now.getDayOfMonth());
        String month = String.valueOf(now.getMonthValue());

        String hour = String.valueOf(now.getHour());
        String minute = String.valueOf(now.getMinute());
        String sec = String.valueOf(now.getSecond());

        String part1 = RandomStringUtils.random( 3, characters );
        String part2 = RandomStringUtils.random( 3, characters );
        String part3 = RandomStringUtils.random( 3, characters );
        String part4 = RandomStringUtils.random( 3, characters );

        String key = part1+""+day+""+hour+""+part2+""+minute+""+month+""+part3+""+sec;
        return key;


    }

    public static void main(String[] args) throws SQLException {
        setUIFont(new FontUIResource(new Font("Angsana New", 0, 16)));
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch(Exception ignored){}
        new LicenseManager();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
