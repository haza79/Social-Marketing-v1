package com.german;

import javax.swing.*;
import java.awt.event.*;

public class OTPinput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField tfOTP;
    public JLabel lbInfo;
    private JButton buttonCancel;

    public String OTP;
    public boolean isCorrect;

    public OTPinput() {



        pack();
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);
        pack();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        if (tfOTP.getText().equals(OTP)){
            isCorrect = true;
            dispose();
        }else {
            JOptionPane.showMessageDialog(null, "otp is incorrect","error",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        OTPinput dialog = new OTPinput();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
