package com.german;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;

public class loginDialog extends JFrame {
    public JPanel contentPane;
    public JButton buttonOK;
    public JButton buttonCancel;
    public JTextField tfUsername;
    public JTextField tfPassword;
    public JButton loginButton;
    public JButton registerButton;
    public JButton forgotPasswordButton;
    public JPanel cardPanel;
    public JPanel loginPanel;
    public JPanel registerPanel;
    public JTextField tfRegisterUsername;
    public JTextField tfRegisterFullName;
    public JTextField tfRegisterEmail;
    public JTextField tfRegisterPassword;
    public JTextField tfRegisterConfirmPassword;
    public JButton btnRegister;
    public JButton loginButton1;
    public JPanel resetPasswordPanel;
    public JTextField tfForgotEmail;
    public JButton sendOTPButton;
    public JTextField tfForgotNewPassword;
    public JTextField tfForgotConfirmPassword;
    public JButton btnSaveNewPassword;
    public JTextField tfForgotUsername;
    public JButton loginButton2;
    public JPanel loadingPanel;
    public JLabel loadingLabel;
    public JPanel changePasswordPanel;
    public JLabel lbLogin;
    public JLabel lbusername;
    public JLabel lbpassword;
    public JLabel lbRegister;
    public JLabel lbname;
    public JLabel lbFullname;
    public JLabel lbEmail;
    public JLabel lbPassword;
    public JLabel lbConfirmPassword;
    public JLabel lbemail;
    public JLabel lbForgot;
    public JLabel lbNewpassword;
    public JLabel lbConfirm;
    public JLabel lbUsername;
    public JLabel lbThai;
    public JLabel lbEnglish;
    public JCheckBox cxRememberMe;
    public boolean isMatch = false;

    public String dbUsername;
    public String dbPassword;
    public String dbFullName;
    public String dbEmail;
    String projectRoot = System.getProperty("user.dir");
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

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
//    ImageIcon img = new ImageIcon(projectRoot+File.separator+"img\\Social MarketingV1.png");
    public loginDialog() {

//        setIconImage(img.getImage());
        setIconImage(new ImageIcon(projectRoot+ File.separator+"img"+File.separator+"Social MarketingV1.png").getImage());
        setTitle("Social Marketing Login");

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadingLabel.setIcon(new ImageIcon(projectRoot+File.separator+"img"+File.separator+"loading.gif"));
        loadingLabel.setBackground( new Color(255, 0, 0, 20) );
        setLocationRelativeTo(null);
        setVisible(true);

        setUIFont(new FontUIResource(new Font("Angsana New", 0, 16)));
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch(Exception ignored){}
//        setSize(500,350);




//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                boolean isUserNameNull = IsNull.isTextFieldNull(tfUsername);
//                boolean isPasswordNull = IsNull.isTextFieldNull(tfPassword);
//                if (isUserNameNull == false && isPasswordNull == false){
//                    String username = tfUsername.getText();
//                    String password = tfPassword.getText();
//                    isMatch = phpAction.login(username,password);
//                    if (isMatch == false){
//                        JOptionPane.showMessageDialog(null, "username or password is incorrect","login fail",JOptionPane.ERROR_MESSAGE);
//                    }else {
//                        phpAction.closeDriver();
//                        dispose();
//                    }
//                }
//
//
//            }
//        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(registerPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(resetPasswordPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        loginButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(loginPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        loginButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(loginPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });












    }
    public void showLoading(){
        cardPanel.removeAll();
        cardPanel.add(loadingPanel);
        cardPanel.repaint();
        cardPanel.revalidate();
    }




}
