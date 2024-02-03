package com.german;

import com.github.javafaker.Faker;

import javax.mail.MessagingException;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


public class MainForm extends JFrame{
    private JPanel mainPanel;
    private JButton btnSelectFormat;
    private JTextField tfCountryCode;
    private JTextField tfPhoneNumber;
    private JTextField tfAmount;
    private JTextField tfNumberPerFile;
    private JButton btnStartGenerate;
    private JTextField tfPrefix;
    private JTable tbSheet;
    private JTable tbSheetDetail;
    private JTextField tfSplitFileCount;
    private JButton btnCalculate;
    private JTextField tfFileName;
    private JRadioButton thaiPhoneRadioButton;
    private JRadioButton laoPhoneRadioButton;
    private JRadioButton mobilePhoneRadioButton;
    private JRadioButton countryCallingCodeRadioButton1;
    private JRadioButton checkDuplicateRadioButton;
    private JRadioButton donTCheckDuplicateRadioButton;
    private JRadioButton TXTRadioButton;
    private JRadioButton VCFRadioButton;
    private JButton randomButton;
    private JTextField tfOutputFolder;
    private JButton setOutputFolderButton;
    private JButton checkInFolderButton;
    private JRadioButton customRadioButton;
    private JPanel customPhonePanel;
    private JTabbedPane tabbedPane1;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JButton cancelButton;
    private JLabel lbWriteSpeed;
//    private JTextArea textArea1;
    private JButton randomButton1;
    private JButton settingButton;
    private JPanel cardPanel;
    private JButton convertVcfButton;
    private JPanel randomPanel;
    private JPanel convertVCFPanel;
    private JPanel settingPanel;
    private JLabel lbUser;
    private JTextField tfLicenseKey;
    private JButton btnLicenseSubmit;
    private JLabel lbAbc;
    private JLabel lbDef;
    private JLabel lbHij;
    private JLabel lbKlm;
    private JLabel lbNop;
    private JLabel lbQrs;
    private JLabel lbTuv;
    private JLabel lbWxy;
    private JRadioButton thaiRadioButton;
    private JRadioButton englishRadioButton;
    private JLabel lbSuccess;
    private JLabel lbActivate;
    private JLabel lbLicense;
    private JLabel lbChangeLanguage;
    private JLabel lbActivated;
    private JLabel lbexpired;
    private JTextField tfExpired;
    private JTextField tfExpiredYear;
    private JTextField tfExpiredMounth;
    private JTextField tfExpiredDay;
    private JTextField tfExpiredHour;
    private JTextField tfExpiredMinute;
    private JTextField tfExpiredSecode;
    private JPanel panelChangeLanguage;
    private JTextField tfVCFFIleName;
    private JButton selectFileButton;
    private JButton generateVcfButton;
    private JTextField tfVCFOutPutFolder;
    private JButton setOutputFolderButton1;
    private JButton openFolderButton;
    private JLabel lbInputfile;
    private JLabel lbOutputfolder;
    private JPanel panelLicense;
    private JPanel panelExpired;
    private JLabel lbExpiredLittle;
    private JTextField tfVCFFileName2;
    private JLabel lbVCFFileName;
    private JButton logOutButton;
    private JLabel lbuser;
    private JLabel lbduration;
    private JLabel lbyears;
    private JLabel lbmonths;
    private JLabel lbdays;
    private JLabel lbhours;
    private JLabel lbminutes;
    private JLabel lbseconds;
    private JLabel lbTitle;
    private JPanel menuPanel;
    private JButton clearHistoryButton;
    private JTextField tfDbUsername;
    private JTextArea jTextArea = new JTextArea();

    public String id;
    public String username;
    public String password;
    public String fullName;
    public String email;

    String projectRoot = System.getProperty("user.dir");
    String outputFolder = projectRoot+File.separator+"output";
    String fileName = "FILENAME";
    String countryId;
    boolean addCountryCode = false;

    String[] columnNameSheet = {"ID","Sheet Name"};
    String[] columnNameSheetDetail = {"ID","Phone Number"};

    DefaultTableModel sheetModel = new DefaultTableModel();
    DefaultTableModel sheetDetailModel = new DefaultTableModel();

    private String[] data;

    PHPAction phpAction;
    IsNull isNull = new IsNull();
    OTP otpGenerate = new OTP();
    SendEmail sendEmail ;
    licenseDateAndTime licenseDateAndTime;
     encryption encryp = new encryption();

    private String OTP = "";

    private String filePathNumber;
    private String fileOutput = projectRoot+File.separator+"output vcf file"+File.separator;

    ArrayList<ArrayList> userLicenseActivate = new ArrayList<>();
    LocalDateTime finalExpiredTime =null;
    long[] expiredDuration= new long[7];
    loginDialog loginDialog;



    MainForm() throws SQLException, ClassNotFoundException, IOException {
        System.out.println(fileOutput);
        JWindow window = new JWindow();
        window.getContentPane().add(
                new JLabel("", new ImageIcon(projectRoot+File.separator+"img"+File.separator+"Social MarketingV1.gif"), SwingConstants.CENTER));
        window.setBounds(500, 150, 500, 500);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - window.getWidth()) / 2;
        int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x,y);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setIconImage(new ImageIcon(projectRoot+File.separator+"img"+File.separator+"Social MarketingV1.png").getImage());
        setTitle("Social Marketing V1");
//        PrintStream out = new PrintStream(new FileOutputStream(projectRoot+File.separator+"src"+File.separator+"log.txt"));
//        System.setOut(out);
//        System.setErr(out);

        Desktop desktop = Desktop.getDesktop();
        tfVCFOutPutFolder.setText(fileOutput);

        Timer t = new Timer(1000, updateClockAction);

        sendEmail = new SendEmail();
        phpAction = new PHPAction();
        licenseDateAndTime = new licenseDateAndTime();
        loginDialog = new loginDialog();

        loginDialog.setLocation(x, y);

        Properties defaultLanguage = read(Path.of(projectRoot+File.separator+"src"+File.separator+"defaultLanguage.properties"));
        String defaultLanguageString = defaultLanguage.getProperty("defaultLanguage");
        loadLanguage(defaultLanguageString);
        System.out.println(defaultLanguageString);
        if (defaultLanguageString.equals("thai")){
            System.out.println("thai select english disable");
            thaiRadioButton.setSelected(true);
            englishRadioButton.setSelected(false);
        }else {
            System.out.println("thai disable english select");
            thaiRadioButton.setSelected(false);
            englishRadioButton.setSelected(true);
        }

        lbTitle.setFont(new Font("Angsana New",Font.PLAIN,26));
        loginDialog.setSize(800,600);
        loginDialog.pack();

        loginDialog.changePasswordPanel.setVisible(false);
        loginDialog.btnSaveNewPassword.setEnabled(false);
        setVisible(false);
        window.setVisible(false);

        window.dispose();

        if(isRememberMeNull()==true){
            System.out.println("remember is null");
        }else {
            System.out.println("remember is not null");
            loginDialog.showLoading();
            String[] userAndPass = loadRememberme();
            boolean isUserNameNull = false;
            boolean isPasswordNull = false;

            if (isUserNameNull == true && isPasswordNull == true){
            }

            String username = userAndPass[0];
            String password = userAndPass[1];
            loginDialog.isMatch = phpAction.login(encryp.encrypt(username),encryp.encrypt(password));

            if (loginDialog.isMatch == true){
                id = phpAction.dbUserID;
                username = encryp.decrypt(phpAction.dbUserUsername);
                password = encryp.decrypt(phpAction.dbUserPassword);
                fullName = encryp.decrypt(phpAction.dbUserFullName);
                email = encryp.decrypt(phpAction.dbUserEmail);

                lbUser.setText(fullName);

                System.out.println("-- user activate license --");

                finalExpiredTime = phpAction.getUserLicenseExpired(id);
                System.out.println("final expired time from main : "+finalExpiredTime);

                if (finalExpiredTime==null){
                    System.out.println("null");
                    stopProgram();
                    t.stop();
                }else {
                    startProgram();
                    expiredDuration = phpAction.getExpiredDuration();
                    System.out.println(expiredDuration[0]+","+expiredDuration[1]+","+expiredDuration[2]+","+
                            expiredDuration[3]+","+expiredDuration[4]+","+expiredDuration[5]);
                    tfExpired.setText(licenseDateAndTime.dateToString(finalExpiredTime));
                    t.start();
                }

                System.out.println("-- user activate license --");

                setVisible(true);


                window.setVisible(false);
                window.dispose();

            }else{
                loginDialog.loginButton1.doClick();
                JOptionPane.showMessageDialog(null, "username or password is incorrect","login fail",JOptionPane.ERROR_MESSAGE);
            }


        }


        loginDialog.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
                Runnable runner = new Runnable() {
                    public void run() {

                        loginDialog.showLoading();
                        boolean isUserNameNull = isNull.isTextFieldNull(loginDialog.tfUsername);
                        boolean isPasswordNull = isNull.isTextFieldNull(loginDialog.tfPassword);

                        if(isUserNameNull && isPasswordNull){
                            JOptionPane.showMessageDialog(null, "value not be null","null",JOptionPane.ERROR_MESSAGE);
                            loginDialog.loginButton1.doClick();
                            return;
                        }

                        String username = encryp.encrypt(loginDialog.tfUsername.getText());
                        String password = encryp.encrypt(loginDialog.tfPassword.getText());

                        try {
                            loginDialog.isMatch = phpAction.login(username,password);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "username or password is incorrect","login fail",JOptionPane.ERROR_MESSAGE);
                            loginDialog.loginButton1.doClick();
                            throw new RuntimeException(ex);
                        }

                        if(loginDialog.isMatch == false){
                            JOptionPane.showMessageDialog(null, "username or password is incorrect","login fail",JOptionPane.ERROR_MESSAGE);
                            loginDialog.loginButton1.doClick();
                            return;
                        }

                        id = phpAction.dbUserID;
                        username = encryp.decrypt(phpAction.dbUserUsername);
                        password = encryp.decrypt(phpAction.dbUserPassword);
                        fullName = encryp.decrypt(phpAction.dbUserFullName);
                        email = encryp.decrypt(phpAction.dbUserEmail);

                        lbUser.setText(fullName);

                        System.out.println("-- user activate license --");

                        try {
                            finalExpiredTime = phpAction.getUserLicenseExpired(id);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println("final expired time from main : "+finalExpiredTime);


                        try {
                            if (loginDialog.cxRememberMe.isSelected()){
                                setRememberMe(username,password);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        if (finalExpiredTime==null){
                            System.out.println("null");
                            stopProgram();
                        }else {
                            startProgram();
                            expiredDuration = phpAction.getExpiredDuration();
//                            System.out.println(expiredDuration[0]+","+expiredDuration[1]+","+expiredDuration[2]+","+
//                                    expiredDuration[3]+","+expiredDuration[4]+","+expiredDuration[5]);
                            tfExpired.setText(licenseDateAndTime.dateToString(finalExpiredTime));
                            t.start();
                        }

                        System.out.println("-- user activate license --");
                        setVisible(true);
                        loginDialog.dispose();
                    }
                };
                Thread t = new Thread(runner, "Code Executer");
                t.start();

            }
        });


        loginDialog.btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Runnable runner = new Runnable() {
                    public void run() {
                        loginDialog.showLoading();
                        IsNull isNull = new IsNull();
                        boolean isUserNull = isNull.isTextFieldNull(loginDialog.tfRegisterUsername);
                        boolean isFullNameNull = isNull.isTextFieldNull(loginDialog.tfRegisterFullName);
                        boolean isEmailNull = isNull.isTextFieldNull(loginDialog.tfRegisterEmail);
                        boolean isPasswordNull = isNull.isTextFieldNull(loginDialog.tfRegisterPassword);
                        boolean isConfirmPasswordNull = isNull.isTextFieldNull(loginDialog.tfRegisterConfirmPassword);


                        if(isUserNull && isFullNameNull && isEmailNull && isPasswordNull && isConfirmPasswordNull){
                            JOptionPane.showMessageDialog(null, "value not be null","null",JOptionPane.ERROR_MESSAGE);
                            loginDialog.registerButton.doClick();
                            return;
                        }

                        String registerPassword = loginDialog.tfRegisterPassword.getText();
                        String registerConfirmPassword = loginDialog.tfRegisterConfirmPassword.getText();

                        if(!registerPassword.equals(registerConfirmPassword)){
                            JOptionPane.showMessageDialog(null, "password don't match","error",JOptionPane.ERROR_MESSAGE);
                            loginDialog.registerButton.doClick();
                            return;
                        }

                        String registerEmail = loginDialog.tfRegisterEmail.getText();
                        boolean isEmailAvailable = false;

                        try {
                            isEmailAvailable = phpAction.isEmailAvailable(registerEmail);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "email is available\nplease use another email","error",JOptionPane.ERROR_MESSAGE);
                            loginDialog.registerButton.doClick();
                            throw new RuntimeException(ex);
                        }

                        if(isEmailAvailable==true){
                            JOptionPane.showMessageDialog(null, "email is available\nplease use another email","error",JOptionPane.ERROR_MESSAGE);
                            loginDialog.registerButton.doClick();
                            return;
                        }

                        OTP = otpGenerate.getOTP();
                        OTPinput otPinput;

                        try {
                            sendEmail.sendOTP(registerEmail, OTP);
                            otPinput = new OTPinput();
                            otPinput.setLocation(x, y);

                            otPinput.OTP = OTP;
                            otPinput.lbInfo.setText("we send otp to " + registerEmail + "     please check your inbox");
                            otPinput.setVisible(true);
                        } catch (MessagingException ex) {
                            throw new RuntimeException(ex);
                        }

                        if(!otPinput.isCorrect){
                            JOptionPane.showMessageDialog(null, "Invalid OTP","error",JOptionPane.ERROR_MESSAGE);
                            loginDialog.loginButton1.doClick();
                            return;
                        }

                        String registerUsername = loginDialog.tfRegisterUsername.getText();
                        String registerFullName = loginDialog.tfRegisterFullName.getText();

                        try {
                            phpAction.register(registerUsername,registerPassword,registerFullName,registerEmail);
                            JOptionPane.showMessageDialog(null, "register success\nnow you can sign in","register",JOptionPane.OK_OPTION);
                            loginDialog.loginButton1.doClick();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error while register","error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException(ex);
                        }

                    }
                };

                Thread t = new Thread(runner, "Code Executer");
                t.start();

            }
        });


        loginDialog.sendOTPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runner = new Runnable() {
                    public void run() {

                        loginDialog.showLoading();
                        boolean isEmailNull = isNull.isTextFieldNull(loginDialog.tfForgotEmail);

                        if(isEmailNull == true){
                            JOptionPane.showMessageDialog(null, "email is null","error",JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String email = loginDialog.tfForgotEmail.getText();

                        boolean isEmailAvailable = false;

                        try{
                            isEmailAvailable = phpAction.isEmailAvailable(email);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        if(!isEmailAvailable){
                            JOptionPane.showMessageDialog(null, "email is valid","error",JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        OTP = otpGenerate.getOTP();

                        try {
                            sendEmail.sendOTP(email,OTP);
                        } catch (MessagingException ex) {
                            throw new RuntimeException(ex);
                        }

                        OTPinput otPinput = new OTPinput();
                        int x = (screenSize.width - otPinput.getWidth()) / 2;
                        int y = (screenSize.height - otPinput.getHeight()) / 2;
                        otPinput.setLocation(x,y);

                        otPinput.OTP = OTP;

                        otPinput.setVisible(true);

                        if(!otPinput.isCorrect){
                            JOptionPane.showMessageDialog(null, "invalid otp","error",JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        loginDialog.changePasswordPanel.setVisible(true);
                        loginDialog.btnSaveNewPassword.setEnabled(true);
                        try {
                            data = phpAction.showDataByEmail(email);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        loginDialog.tfForgotUsername.setText(data[1]);
                        loginDialog.forgotPasswordButton.doClick();



//                        if (isEmailNull == false){
//                            String email = loginDialog.tfForgotEmail.getText();
//
//                            try {
//                                if (phpAction.isEmailAvailable(email) == true){
//
//                                    try {
//
//                                        OTP = otpGenerate.getOTP();
//                                        sendEmail.sendOTP(email,OTP);
//                                        OTPinput otPinput = new OTPinput();
//
//                                        int x = (screenSize.width - otPinput.getWidth()) / 2;
//                                        int y = (screenSize.height - otPinput.getHeight()) / 2;
//                                        otPinput.setLocation(x,y);
//
//                                        otPinput.OTP = OTP;
//
//                                        otPinput.setVisible(true);
//
//                                        if (otPinput.isCorrect == true){
//
//                                            loginDialog.changePasswordPanel.setVisible(true);
//                                            loginDialog.btnSaveNewPassword.setEnabled(true);
//                                            data = phpAction.showDataByEmail(email);
//                                            loginDialog.tfForgotUsername.setText(data[1]);
//
//                                        }
//
//                                    } catch (MessagingException ex) {
//                                        ex.printStackTrace();
//                                    }
//
//                                }else{
//                                    JOptionPane.showMessageDialog(null, "email is valid","error",JOptionPane.ERROR_MESSAGE);
//                                }
//                            } catch (SQLException ex) {
//                                throw new RuntimeException(ex);
//                            }
//
//                        }else {
//                            JOptionPane.showMessageDialog(null, "email is null","error",JOptionPane.ERROR_MESSAGE);
//                        }
//                        loginDialog.forgotPasswordButton.doClick();





                    }
                };
                Thread t = new Thread(runner, "Code Executer");
                t.start();




            }
        });


        loginDialog.btnSaveNewPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runner = new Runnable() {
                    public void run() {
                        loginDialog.showLoading();
                        boolean isPasswordNull = isNull.isTextFieldNull(loginDialog.tfForgotNewPassword);
                        boolean isConfirmPasswordNull = isNull.isTextFieldNull(loginDialog.tfForgotConfirmPassword);
                        if (isPasswordNull == false && isConfirmPasswordNull == false){

                            if (loginDialog.tfForgotNewPassword.getText().equals(loginDialog.tfForgotConfirmPassword.getText())){

                                boolean isUpdate = false;
                                try {
                                    isUpdate = phpAction.changePassword(data[0],loginDialog.tfForgotNewPassword.getText());
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }

                                if (isUpdate == true){

                                    JOptionPane.showMessageDialog(null, "change password success","change password",JOptionPane.PLAIN_MESSAGE);
                                    loginDialog.changePasswordPanel.setVisible(false);
                                    loginDialog.btnSaveNewPassword.setEnabled(false);

                                }else {
                                    JOptionPane.showMessageDialog(null, "change password fail\nplease try again","error",JOptionPane.ERROR_MESSAGE);
                                }


                            }else{
                                JOptionPane.showMessageDialog(null, "password is don't match","error",JOptionPane.ERROR_MESSAGE);
                            }

                        }else {
                            JOptionPane.showMessageDialog(null, "password is null","error",JOptionPane.ERROR_MESSAGE);
                        }
                        loginDialog.forgotPasswordButton.doClick();
                    }
                };
                Thread t = new Thread(runner, "Code Executer");
                t.start();




            }
        });



//        PrintStream out = new PrintStream(new FileOutputStream(projectRoot+File.separator+"src\\log.txt"));
//        System.setOut(out);
//        System.setErr(out);


        DatabaseAction dbaction = new DatabaseAction();
        Generate generate = new Generate();
        Faker faker = new Faker();

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        progressBar1.setStringPainted(true);
        progressBar2.setStringPainted(true);
        dbaction.showAllHistory(jTextArea);
        jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
        tabbedPane1.removeAll();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        tabbedPane1.addTab("history",new JScrollPane(jTextArea) );

        jTextArea.setEditable(false);
        tfOutputFolder.setText(outputFolder);

        customRadioButton.setSelected(false);
        tfCountryCode.setEnabled(false);
        tfPrefix.setEnabled(false);
        tfPhoneNumber.setEnabled(false);

        thaiPhoneRadioButton.setSelected(true);
        mobilePhoneRadioButton.setSelected(true);
        TXTRadioButton.setSelected(true);
        checkDuplicateRadioButton.setSelected(true);

        jTextArea.setCaretPosition(jTextArea.getDocument().getLength());


        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tfAmount.getText().equals("") || tfNumberPerFile.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "value is null","error",JOptionPane.ERROR_MESSAGE);
                }else {
                    int amount = Integer.parseInt(tfAmount.getText());
                    int perfile = Integer.parseInt(tfNumberPerFile.getText());

                    if (amount <= 0 || perfile <=0){
                        JOptionPane.showMessageDialog(null, "Size must be greater than 0","error",JOptionPane.ERROR_MESSAGE);
                    }else {
                        dbaction.splitPartition(amount,perfile);
                        tfSplitFileCount.setText(String.valueOf(dbaction.getPartitionedList().size()));
                    }


                }


            }
        });

        thaiPhoneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                laoPhoneRadioButton.setSelected(false);
                if (!thaiPhoneRadioButton.isSelected()){
                    thaiPhoneRadioButton.setSelected(true);
                }

                countryId = "1";

            }
        });

        laoPhoneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                thaiPhoneRadioButton.setSelected(false);
                if (!laoPhoneRadioButton.isSelected()){
                    laoPhoneRadioButton.setSelected(true);
                }

                countryId = "3";

            }
        });

        mobilePhoneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countryCallingCodeRadioButton1.setSelected(false);
                if (!mobilePhoneRadioButton.isSelected()){
                    mobilePhoneRadioButton.setSelected(true);
                }
                addCountryCode = false;
            }
        });

        countryCallingCodeRadioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mobilePhoneRadioButton.setSelected(false);
                if (!countryCallingCodeRadioButton1.isSelected()){
                    countryCallingCodeRadioButton1.setSelected(true);
                }
                addCountryCode = true;
            }
        });

        customRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (customRadioButton.isSelected()){
                    tfCountryCode.setEnabled(true);
                    tfPrefix.setEnabled(true);
                    tfPhoneNumber.setEnabled(true);

                    thaiPhoneRadioButton.setEnabled(false);
                    laoPhoneRadioButton.setEnabled(false);
                    mobilePhoneRadioButton.setEnabled(false);
                    countryCallingCodeRadioButton1.setEnabled(false);

                }else {
                    tfCountryCode.setEnabled(false);
                    tfPrefix.setEnabled(false);
                    tfPhoneNumber.setEnabled(false);

                    thaiPhoneRadioButton.setEnabled(true);
                    laoPhoneRadioButton.setEnabled(true);
                    mobilePhoneRadioButton.setEnabled(true);
                    countryCallingCodeRadioButton1.setEnabled(true);

                }

            }
        });

        checkDuplicateRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                donTCheckDuplicateRadioButton.setSelected(false);
                if (!checkDuplicateRadioButton.isSelected()){
                    checkDuplicateRadioButton.setSelected(true);
                }
            }
        });

        donTCheckDuplicateRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkDuplicateRadioButton.setSelected(false);
                if (!donTCheckDuplicateRadioButton.isSelected()){
                    donTCheckDuplicateRadioButton.setSelected(true);
                }
            }
        });

        TXTRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VCFRadioButton.setSelected(false);
                if (!TXTRadioButton.isSelected()){
                    TXTRadioButton.setSelected(true);
                }
            }
        });

        VCFRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TXTRadioButton.setSelected(false);
                if (!VCFRadioButton.isSelected()){
                    VCFRadioButton.setSelected(true);
                }
            }
        });

        setOutputFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(null);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    tfOutputFolder.setText(file.getPath());
                    outputFolder = file.getPath();
                }
            }
        });
        checkInFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("explorer.exe /select," + outputFolder);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Runnable runner = new Runnable()
                {
                    public void run() {
                        //Your original code with the loop here.

                        randomButton.setEnabled(false);

                        //main
                        long startTime = System.nanoTime();
                        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH mm").format(Calendar.getInstance().getTime());
                        String fileType = "txt";
                        if (TXTRadioButton.isSelected()){
                            fileType = "txt";
                        }else if (VCFRadioButton.isSelected()){
                            fileType = "vcf";
                        }
                        if (thaiPhoneRadioButton.isSelected()){
                            countryId = "1";
                        }else if (laoPhoneRadioButton.isSelected()){
                            countryId = "3";
                        }

                        if (mobilePhoneRadioButton.isSelected()){
                            addCountryCode = false;
                        }else if (countryCallingCodeRadioButton1.isSelected()){
                            addCountryCode = true;
                        }

                        ArrayList<String > generateNumber = new ArrayList<>();
                        String fileName = tfFileName.getText();
                        String number = generate.getGenerateNumber(tfCountryCode.getText(),tfPrefix.getText(),tfPhoneNumber.getText()   );
                        System.out.println(number);

                        int amount = Integer.parseInt(tfAmount.getText()),
                                chunksize = Integer.parseInt(tfNumberPerFile.getText());

                        dbaction.splitPartition(amount,chunksize);

                        progressBar1.setMinimum(0);
                        progressBar2.setMaximum(0);
                        progressBar1.setMaximum(dbaction.getPartitionedList().size());

                        File file = null;
                        FileWriter fileWriter = null;
                        Map<String, FileWriter> map = new HashMap<>();

                        //new
                        String countryCode2 = "";
                        ArrayList<String > prefixes = new ArrayList<>();
                        int formatCount = 0;
                        try {
                             countryCode2 = generate.getCountryCode(countryId);
                             prefixes = generate.getPrefix(countryId);
                             formatCount = generate.formatCount(countryId);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        //new


                        for (int i = 0; i<dbaction.getPartitionedList().size(); i++){

                            progressBar1.setValue(i);

                            try {
                                file = new File(outputFolder+File.separator+fileName+" "+dbaction.getPartitionedList().get(i).size()+" "+timeStamp+" "+" ("+(i+1)+")."+fileType+" ");
                                file.createNewFile();
                                 fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
                                map.put(file.getAbsolutePath(), fileWriter);

                                progressBar2.setMaximum(dbaction.getPartitionedList().get(i).size());
                                for (int j = 0; j<dbaction.getPartitionedList().get(i).size(); j++){
                                    long startTimeWrite = System.nanoTime();
                                    progressBar2.setValue(j);
                                    if (!customRadioButton.isSelected()){
                                        // custom isn't selected

//                                        String autoNumber = generate.getGenerateNumberAuto(countryId,addCountryCode);

                                        //new
                                        String autoNumber = generate.getGenerateNumberAuto2(countryCode2,prefixes,formatCount,addCountryCode);
                                        //new

                                        if (TXTRadioButton.isSelected()){

                                            if (checkDuplicateRadioButton.isSelected()){
                                                if (!dbaction.isDuplicate(autoNumber)){
                                                    dbaction.addHistoryToTextArea(autoNumber,jTextArea);
                                                    fileWriter.write(autoNumber+"\n");
                                                }
                                            }else {
                                                if (dbaction.isDuplicate(autoNumber)){
                                                    dbaction.addHistoryToTextArea("[duplicate]\t"+autoNumber,jTextArea);
                                                }else {
                                                    dbaction.addHistoryToTextArea(autoNumber,jTextArea);
                                                }
                                                fileWriter.write(autoNumber+"\n");
//                                                jTextArea.setCaretPosition(jTextArea.getDocument().getLength());

                                            }
                                            generateNumber.add(autoNumber);
//                                            dbaction.addHistory(autoNumber);
                                            jTextArea.setCaretPosition(jTextArea.getDocument().getLength());


                                        }else if (VCFRadioButton.isSelected()){

                                            if (checkDuplicateRadioButton.isSelected()){
                                                if (!dbaction.isDuplicate(autoNumber)){
                                                    dbaction.addHistoryToTextArea(autoNumber,jTextArea);
                                                    jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
                                                    String name = faker.name().firstName();
                                                    fileWriter.write("" +
                                                            "BEGIN:VCARD\n" +
                                                            "VERSION:3.0\n" +
                                                            "N:;"+name+";;;\n" +
                                                            "FN:"+name+"\n" +
                                                            "TEL;TYPE=CELL:"+autoNumber+"\n" +
                                                            "END:VCARD\n");
                                                }
                                            }else {
                                                if (dbaction.isDuplicate(autoNumber)){
                                                    dbaction.addHistoryToTextArea("[duplicate]\t"+autoNumber,jTextArea);
                                                }else {
                                                    dbaction.addHistoryToTextArea(autoNumber,jTextArea);
                                                }
                                                String name = faker.name().firstName();
                                                fileWriter.write("" +
                                                        "BEGIN:VCARD\n" +
                                                        "VERSION:3.0\n" +
                                                        "N:;"+name+";;;\n" +
                                                        "FN:"+name+"\n" +
                                                        "TEL;TYPE=CELL:"+autoNumber+"\n" +
                                                        "END:VCARD\n");
                                                jTextArea.setCaretPosition(jTextArea.getDocument().getLength());

                                            }
                                            generateNumber.add(autoNumber);
//                                            dbaction.addHistory(autoNumber);
                                            jTextArea.setCaretPosition(jTextArea.getDocument().getLength());


                                        }

                                    }else {
                                        // custom is selected
                                        System.out.println("custom");
                                        String countryCode = tfCountryCode.getText(),
                                                prefix = tfPrefix.getText(),
                                                format = tfPhoneNumber.getText();

                                        String customNumber = generate.getGenerateNumber(countryCode,prefix,format  );

                                        if (TXTRadioButton.isSelected()){

                                            if (checkDuplicateRadioButton.isSelected()){
                                                if (!dbaction.isDuplicate(customNumber)){
                                                    dbaction.addHistoryToTextArea(customNumber,jTextArea);
                                                    jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
                                                    fileWriter.write(customNumber+"\n");
                                                }
                                            }else {
                                                if (dbaction.isDuplicate(customNumber)){
                                                    dbaction.addHistoryToTextArea("[duplicate]\t"+customNumber,jTextArea);
                                                }else {
                                                    dbaction.addHistoryToTextArea(customNumber,jTextArea);
                                                }
                                                fileWriter.write(customNumber+"\n");
                                                jTextArea.setCaretPosition(jTextArea.getDocument().getLength());

                                            }
                                            generateNumber.add(customNumber);
//                                            dbaction.addHistory(customNumber);
                                            jTextArea.setCaretPosition(jTextArea.getDocument().getLength());





//                                            fileWriter.write(customNumber+"\n");






                                        }else if (VCFRadioButton.isSelected()){

                                            if (checkDuplicateRadioButton.isSelected()){
                                                if (!dbaction.isDuplicate(customNumber)){
                                                    dbaction.addHistoryToTextArea(customNumber,jTextArea);
                                                    jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
                                                    String name = faker.name().firstName();
                                                    fileWriter.write("" +
                                                            "BEGIN:VCARD\n" +
                                                            "VERSION:3.0\n" +
                                                            "N:;"+name+";;;\n" +
                                                            "FN:"+name+"\n" +
                                                            "TEL;TYPE=CELL:"+customNumber+"\n" +
                                                            "END:VCARD\n");
                                                }
                                            }else {
                                                if (dbaction.isDuplicate(customNumber)){
                                                    dbaction.addHistoryToTextArea("[duplicate]\t"+customNumber,jTextArea);
                                                }else {
                                                    dbaction.addHistoryToTextArea(customNumber,jTextArea);
                                                }
                                                String name = faker.name().firstName();
                                                fileWriter.write("" +
                                                        "BEGIN:VCARD\n" +
                                                        "VERSION:3.0\n" +
                                                        "N:;"+name+";;;\n" +
                                                        "FN:"+name+"\n" +
                                                        "TEL;TYPE=CELL:"+customNumber+"\n" +
                                                        "END:VCARD\n");
                                                jTextArea.setCaretPosition(jTextArea.getDocument().getLength());

                                            }
                                            generateNumber.add(customNumber);
//                                            dbaction.addHistory(customNumber);
                                            jTextArea.setCaretPosition(jTextArea.getDocument().getLength());





//                                            String name = faker.name().firstName();
//                                            fileWriter.write("" +
//                                                    "BEGIN:VCARD\n" +
//                                                    "VERSION:3.0\n" +
//                                                    "N:;"+name+";;;\n" +
//                                                    "FN:"+name+"\n" +
//                                                    "TEL;TYPE=CELL:"+customNumber+"\n" +
//                                                    "END:VCARD\n");





                                        }

                                    }



//                                    progressBar2.setValue(j);
                                    long endTime = System.nanoTime();
                                    long totalTime = endTime - startTimeWrite;
                                    double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;
                                    if (i == 0){
                                        lbWriteSpeed.setText("1 number / "+elapsedTimeInSecond+" sec");
//                                        textArea1.append(elapsedTimeInSecond+"\n");
                                    }
                                    if (i == 10){
                                        lbWriteSpeed.setText("1 number / "+elapsedTimeInSecond+" sec");
//                                        textArea1.append(elapsedTimeInSecond+"\n");
                                    }


                                }
                                jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
//                                fileWriter.close();



                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }


                        }

                        for (FileWriter file1 : map.values()) {
                            try {
                                file1.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }

                        cancelButton.setEnabled(false);
                        progressBar2.setMaximum(0);
                        progressBar2.setMaximum(generateNumber.size());
                        for (int i = 0 ;i<generateNumber.size(); i++){
                            try {
                                dbaction.addHistory(generateNumber.get(i));
                                progressBar2.setValue(i);

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }

                        progressBar1.setMaximum(1);
                        progressBar2.setMaximum(1);
                        progressBar1.setValue(1);
                        progressBar2.setValue(1);
                        long endTime = System.nanoTime();
                        long totalTime = endTime - startTime;
                        double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;
                        System.out.println("generate time = " + elapsedTimeInSecond);
                        //main
                        randomButton.setEnabled(true);
                        cancelButton.setEnabled(true);

                    }
                };
                Thread t = new Thread(runner, "Code Executer");
                t.start();

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t.interrupt();
                    }
                });

            }



        });


        randomButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(randomPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        convertVcfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(convertVCFPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(settingPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });



        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    phpAction.connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }));


        // todo : submit activate license
        btnLicenseSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                phpAction.setupDriver();
                String inputLicense = tfLicenseKey.getText();
                try {
                    if (phpAction.isLicenseActivate(inputLicense) == false){// license is not use
                        t.stop();
                        String[] data = phpAction.getLicenseData(inputLicense);
                        String startTime = licenseDateAndTime.getStringStartTime();
                        String endTime = licenseDateAndTime.getExpiredTime(startTime, Integer.parseInt(data[2]));
                        System.out.println("start time -: "+startTime);
                        System.out.println("end tine   -: "+endTime);
                        phpAction.activateLicense(inputLicense,id,startTime,endTime );

                        finalExpiredTime = phpAction.getUserLicenseExpired(id);
                        System.out.println(finalExpiredTime);
                        if (finalExpiredTime==null){
                            System.out.println("null");
                        }else {
                            startProgram();
                            expiredDuration = phpAction.getExpiredDuration();
                            System.out.println(expiredDuration[0]+","+expiredDuration[1]+","+expiredDuration[2]+","+
                                    expiredDuration[3]+","+expiredDuration[4]+","+expiredDuration[5]);
                            tfExpired.setText(licenseDateAndTime.dateToString(finalExpiredTime));
                            t.start();
                        }
    //                    expiredDuration = phpAction.getExpiredDuration();
    //                    System.out.println(expiredDuration[0]+","+expiredDuration[1]+","+expiredDuration[2]+","+
    //                            expiredDuration[3]+","+expiredDuration[4]+","+expiredDuration[5]);
    //                    t.start();

                    }else {// license is use

                        JOptionPane.showMessageDialog(null, "invalid license\nplease use another key","error",JOptionPane.ERROR_MESSAGE);

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });


        thaiRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    englishRadioButton.setSelected(false);
                    defaultLanguage.setProperty("defaultLanguage","thai");
                    loadLanguage("thai");
                    defaultLanguage.store(new FileOutputStream(projectRoot+File.separator+"src"+File.separator+"defaultLanguage.properties"), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        loginDialog.lbThai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("Yay you clicked me");

                try {
                    defaultLanguage.setProperty("defaultLanguage","thai");
                    loadLanguage("thai");
                    defaultLanguage.store(new FileOutputStream(projectRoot+File.separator+"src"+File.separator+"defaultLanguage.properties"), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        englishRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    thaiRadioButton.setSelected(false);
                    defaultLanguage.setProperty("defaultLanguage","english");
                    loadLanguage("english");
                    defaultLanguage.store(new FileOutputStream(projectRoot+File.separator+"src"+File.separator+"defaultLanguage.properties"), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loginDialog.lbEnglish.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("Yay you clicked me");

                try {
                    defaultLanguage.setProperty("defaultLanguage","english");
                    loadLanguage("english");
                    defaultLanguage.store(new FileOutputStream(projectRoot+File.separator+"src"+File.separator+"defaultLanguage.properties"), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFileChooser fileChooser = new JFileChooser("C:\\");
                fileChooser.setFont(new Font("Sarabun",Font.PLAIN,14));
                //Angsana New

                int response = fileChooser.showSaveDialog(null);

                if(response == JFileChooser.APPROVE_OPTION){

                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                    tfVCFFIleName.setText(String.valueOf(file));
                    filePathNumber = String.valueOf(file);

                    System.out.println("== select file ==");
                    System.out.println(file);
                    System.out.println(filePathNumber);
                    System.out.println("=================");


                }


            }
        });
        generateVcfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                Runnable runner = new Runnable() {
                    public void run() {

                        try {

                            String line = null;
                            ArrayList<String> numbers = new ArrayList<>();
                            BufferedReader bufferedReader = new BufferedReader(
                                    new InputStreamReader(new FileInputStream(filePathNumber),"UTF-8")
                            );

                            while (true){

                                if (!((line = bufferedReader.readLine()) != null)) break;

                                String[] value = line.split(",");
                                numbers.add(value[0]) ;
                            }

//                            FileWriter fileWriter = null;
                            String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH mm ss").format(Calendar.getInstance().getTime());
//                            File outputFile = new File(fileOutput+"\\"+tfVCFFileName2.getText()+" " +timeStamp+".vcf  ");


                            System.out.println("== path ==");
                            System.out.println(fileOutput+File.separator+tfVCFFileName2.getText()+" " +timeStamp+".vcf  ");
                            System.out.println("==========");
                            File outputFile = new File(fileOutput+File.separator+tfVCFFileName2.getText()+" " +timeStamp+".vcf  ");

                            System.out.println("== output ==");
                            System.out.println(outputFile);
                            if (outputFile.createNewFile()) {
                                System.out.println("File created: " + outputFile.getName());
                            } else {
                                System.out.println("File already exists.");
                            }


                            String outputFilePath = outputFile.getName();
//                            fileOutput = outputFile.getPath();
                            System.out.println("== genereate ==");

                            System.out.println(fileOutput);

                            System.out.println("===============");

//                            outputFile.createNewFile();
                            FileWriter fileWriter = new FileWriter(outputFile, StandardCharsets.UTF_8);


                            progressBar1.setMinimum(0);
                            progressBar1.setMaximum(numbers.size());

                            for (int i = 0; i<numbers.size(); i++){
//                    int randomFileName = ThreadLocalRandom.current().nextInt(0, phone.size()-1 + 1);
                                    progressBar1.setValue(i+1);
                                    String name = faker.name().firstName();
                                    String replaceString=numbers.get(i).replaceAll("-","");
                                    fileWriter.write("" +
                                            "BEGIN:VCARD\n" +
                                            "VERSION:3.0\n" +
                                            "N:;"+name+";;;\n" +
                                            "FN:"+name+"\n" +
                                            "TEL;TYPE=CELL:"+replaceString.trim()+"\n" +
                                            "END:VCARD\n");



                            }

                            fileWriter.close();
//                            fileOutput = outputFile.getPath();
                            System.out.println("=-= output file =-=");
                            System.out.println("get path -: "+outputFile.getPath());
                            System.out.println("get absolute path -: "+outputFile.getAbsolutePath());
                            System.out.println("=-=-=-=-=-=-=-=-=-=");
                            JOptionPane.showMessageDialog(null, "write vcf success\n"+outputFilePath,"dialog",JOptionPane.PLAIN_MESSAGE);






                        } catch (UnsupportedEncodingException ex) {
                            ex.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }












                    }
                };

                Thread t = new Thread(runner, "Code Executer");
                t.start();


            }
        });
        setOutputFolderButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(null);
                if(option == JFileChooser.APPROVE_OPTION){

                    File file = fileChooser.getSelectedFile();
                    tfVCFOutPutFolder.setText(file.getPath());
                    fileOutput = file.getPath();

                    System.out.println("== set output ==");

                }


            }
        });
        openFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("explorer.exe /select," + fileOutput);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                t.stop();
                loginDialog.setVisible(true);
                try {
                    setRememberMeToNull();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                loginDialog.tfUsername.setText("");
                loginDialog.tfPassword.setText("");
                loginDialog.loginButton2.doClick();
            }
        });

        clearHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int result = JOptionPane.showConfirmDialog(mainPanel,"Sure? You want to clear history?", "action",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        dbaction.deleteAllHistory();
                        dbaction.showAllHistory(jTextArea);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }



    public void setUsername(String value){
        lbUser.setText(value);
    }

    private Properties read(  Path file ) throws IOException {
        final var properties = new Properties();

        try( final var in = new InputStreamReader(
                new FileInputStream( file.toFile() ), StandardCharsets.UTF_8 ) ) {
            properties.load( in );
        }

        return properties;
    }

    private String[] loadRememberme() throws IOException {
        String[] userAndPass = new String[2];
        Properties remember = read(Path.of(projectRoot+File.separator+"src"+File.separator+"rememberme.properties"));
        userAndPass[0] = encryp.decrypt(remember.getProperty("username"));
        userAndPass[1] = encryp.decrypt(remember.getProperty("password"));
        return userAndPass;
    }

    private void setRememberMe(String username,String password) throws IOException {
        Properties remember = read(Path.of(projectRoot+File.separator+"src"+File.separator+"rememberme.properties"));
        remember.setProperty("username",encryp.encrypt(username));
        remember.setProperty("password",encryp.encrypt(password));
        remember.store(new FileOutputStream(projectRoot+File.separator+"src"+File.separator+"rememberme.properties"), null);
    }

    private void setRememberMeToNull() throws IOException {
        Properties remember = read(Path.of(projectRoot+File.separator+"src"+File.separator+"rememberme.properties"));
        remember.setProperty("username",encryp.encrypt("null"));
        remember.setProperty("password",encryp.encrypt("null"));
        remember.store(new FileOutputStream(projectRoot+File.separator+"src"+File.separator+"rememberme.properties"), null);
    }

    private boolean isRememberMeNull() throws IOException {
        String[] remember = loadRememberme();
        boolean isNull = false;
        if (remember[0].equals("null") && remember[1].equals("null")){
            isNull = true;
        }else {
            isNull = false;
        }
        return isNull;
    }




    private void loadLanguage(String languageInput) throws IOException {


        Properties language = new Properties();

        if (languageInput.equals("thai")){
            language = read(Path.of(projectRoot+File.separator+"src"+File.separator+"thai.properties"));
        }else {
            language = read(Path.of(projectRoot+File.separator+"src"+File.separator+"english.properties"));
        }
        System.out.println(language.getProperty("btnClearHistory"));
        lbAbc.setText(language.getProperty("lbAbc"));
        lbDef.setText(language.getProperty("lbDef"));
        lbHij.setText(language.getProperty("lbHij"));
        lbKlm.setText(language.getProperty("lbKlm"));
        btnCalculate.setText(language.getProperty("btnCalculate"));
        clearHistoryButton.setText(language.getProperty("btnClearHistory"));
        thaiPhoneRadioButton.setText(language.getProperty("thaiPhoneRadioButton"));
        mobilePhoneRadioButton.setText(language.getProperty("mobilePhoneRadioButton"));
        customRadioButton.setText(language.getProperty("customRadioButton"));
        laoPhoneRadioButton.setText(language.getProperty("laoPhoneRadioButton"));
        countryCallingCodeRadioButton1.setText(language.getProperty("countryCallingCodeRadioButton1"));
        lbNop.setText(language.getProperty("lbNop"));
        lbQrs.setText(language.getProperty("lbQrs"));
        lbTuv.setText(language.getProperty("lbTuv"));
        checkDuplicateRadioButton.setText(language.getProperty("checkDuplicateRadioButton"));
        donTCheckDuplicateRadioButton.setText(language.getProperty("donTCheckDuplicateRadioButton"));
        lbWxy.setText(language.getProperty("lbWxy"));
        setOutputFolderButton.setText(language.getProperty("setOutputFolderButton"));
        checkInFolderButton.setText(language.getProperty("checkInFolderButton"));
        lbSuccess.setText(language.getProperty("lbSuccess"));
        lbChangeLanguage.setText(language.getProperty("lbChangeLanguage"));
        thaiRadioButton.setText(language.getProperty("thaiRadioButton"));
        englishRadioButton.setText(language.getProperty("englishRadioButton"));
        lbActivate.setText(language.getProperty("lbActivate"));
        lbLicense.setText(language.getProperty("lbLicense"));
        btnLicenseSubmit.setText(language.getProperty("bntLicenseSubmit"));
        lbActivated.setText(language.getProperty("lbActivated"));
        lbexpired.setText(language.getProperty("lbexpired"));
        randomButton1.setText(language.getProperty("randomButton1"));
        randomButton1.setText(language.getProperty("randomButton1"));
        convertVcfButton.setText(language.getProperty("convertVcfButton"));
        settingButton.setText(language.getProperty("settingButton"));
        cancelButton.setText(language.getProperty("cancelButton"));
        lbInputfile.setText(language.getProperty("lbInputfile"));
        selectFileButton.setText(language.getProperty("selectFileButton"));
        generateVcfButton.setText(language.getProperty("generateVcfButton"));
        lbOutputfolder.setText(language.getProperty("lbOutputfolder"));
        setOutputFolderButton1.setText(language.getProperty("setOutputFolderButton1"));
        openFolderButton.setText(language.getProperty("openFolderButton"));
        lbuser.setText(language.getProperty("lbuser"));
        lbuser.setText(language.getProperty("lbuser"));
        logOutButton.setText(language.getProperty("logOutButton"));
        lbduration.setText(language.getProperty("lbduration"));
        lbyears.setText(language.getProperty("lbyears"));
        lbmonths.setText(language.getProperty("lbmonths"));
        lbdays.setText(language.getProperty("lbdays"));
        lbhours.setText(language.getProperty("lbhours"));
        lbminutes.setText(language.getProperty("lbminutes"));
        lbseconds.setText(language.getProperty("lbseconds"));
        lbVCFFileName.setText(language.getProperty("lbVCFFileName"));



        changePanelText(panelChangeLanguage,language.getProperty("changePanelText"));
        changePanelText(panelLicense,language.getProperty("panelLicense"));
        changePanelText(panelExpired,language.getProperty("panelExpired"));


        loginDialog.lbLogin.setText(language.getProperty("lbLogin"));
        loginDialog.lbusername.setText(language.getProperty("lbusername"));
        loginDialog.lbpassword.setText(language.getProperty("lbpassword"));
        loginDialog.loginButton.setText(language.getProperty("loginButton"));
        loginDialog.registerButton.setText(language.getProperty("registerButton"));
        loginDialog.forgotPasswordButton.setText(language.getProperty("forgotPasswordButton"));
        loginDialog.lbRegister.setText(language.getProperty("lbRegister"));
        loginDialog.lbname.setText(language.getProperty("lbname"));
        loginDialog.lbFullname.setText(language.getProperty("lbFullname"));
        loginDialog.lbEmail.setText(language.getProperty("lbEmail"));
        loginDialog.lbPassword.setText(language.getProperty("lbPassword"));
        loginDialog.lbConfirmPassword.setText(language.getProperty("lbConfirmPassword"));
        loginDialog.loginButton1.setText(language.getProperty("loginButton1"));
        loginDialog.btnRegister.setText(language.getProperty("btnRegister"));
        loginDialog.lbForgot.setText(language.getProperty("lbForgot"));
        loginDialog.lbemail.setText(language.getProperty("lbemail"));
        loginDialog.sendOTPButton.setText(language.getProperty("sendOTPButton"));
        loginDialog.lbUsername.setText(language.getProperty("lbUsername"));
        loginDialog.lbNewpassword.setText(language.getProperty("lbNewpassword"));
        loginDialog.lbConfirm.setText(language.getProperty("lbConfirm"));
        loginDialog.loginButton2.setText(language.getProperty("loginButton2"));
        loginDialog.btnSaveNewPassword.setText(language.getProperty("btnSaveNewPassword"));
        loginDialog.cxRememberMe.setText(language.getProperty("cxRememberMe"));




    }

    public void changePanelText(JPanel panel, String text){
        Border ChangeLanguageBorder = BorderFactory.createTitledBorder(text);
        panel.setBorder(ChangeLanguageBorder);
    }








    ActionListener updateClockAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Assumes clock is a custom component

            long[] expiredDuration = phpAction.getExpiredDuration();
            if (expiredDuration[0]<=0 && expiredDuration[1]<=0
            && expiredDuration[2] <= 0 && expiredDuration[3] <=0
            && expiredDuration[4] <= 0 && expiredDuration[5] <= 0){
                System.out.println("expired");
                stopProgram();
                lbExpiredLittle.setText("0000-00-00 00:00:00");
            }else {
                startProgram();
                tfExpiredYear.setText(String.valueOf(expiredDuration[0]));
                tfExpiredMounth.setText(String.valueOf(expiredDuration[1]));
                tfExpiredDay.setText(String.valueOf(expiredDuration[2]));
                tfExpiredHour.setText(String.valueOf(expiredDuration[3]));
                tfExpiredMinute.setText(String.valueOf(expiredDuration[4]));
                tfExpiredSecode.setText(String.valueOf(expiredDuration[5]));
                lbExpiredLittle.setText(expiredDuration[0]+"-"+expiredDuration[1]+"-"+expiredDuration[2]+" "+expiredDuration[3]+":"+expiredDuration[4]+":"+expiredDuration[5]);
            }





        }
    };

    public void stopProgram(){
        randomButton.setEnabled(false);
        btnCalculate.setEnabled(false);
        setOutputFolderButton.setEnabled(false);
        checkInFolderButton.setEnabled(false);
        generateVcfButton.setEnabled(false);
        selectFileButton.setEnabled(false);
        setOutputFolderButton1.setEnabled(false);
        openFolderButton.setEnabled(false);

        tfAmount.setEnabled(false);
        tfNumberPerFile.setEnabled(false);
        tfFileName.setEnabled(false);
        lbExpiredLittle.setText("0000-00-00 00:00:00");
        tfExpired.setText("");
        tfExpiredYear.setText("");
        tfExpiredMounth.setText("");
        tfExpiredDay.setText("");
        tfExpiredHour.setText("");
        tfExpiredMinute.setText("");
        tfExpiredSecode.setText("");
    }

    public void startProgram(){
        randomButton.setEnabled(true);
        btnCalculate.setEnabled(true);
        setOutputFolderButton.setEnabled(true);
        checkInFolderButton.setEnabled(true);
        generateVcfButton.setEnabled(true);
        selectFileButton.setEnabled(true);
        setOutputFolderButton1.setEnabled(true);
        openFolderButton.setEnabled(true);

        tfAmount.setEnabled(true);
        tfNumberPerFile.setEnabled(true);
        tfFileName.setEnabled(true);
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

        setUIFont(new FontUIResource(new Font("Angsana New", 0, 16)));
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch(Exception ignored){}



//        loginDialog loginDialog = new loginDialog();



//        if (loginDialog.isMatch == true){
//            System.out.println("true from main");
//            MainForm mainForm = new MainForm();
//            System.out.println("user info from main");
//            mainForm.username = loginDialog.dbUsername;
//            mainForm.password = loginDialog.dbPassword;
//            mainForm.fullName = loginDialog.dbFullName;
            new MainForm();
//
//
//
//        }









    }



}
