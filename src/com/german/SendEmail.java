package com.german;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    final String auth_host = "smtp-relay.sendinblue.com";
    final String auth_port = "587";
    final String auth_email = "asas4358gfk@gmail.com";
    final String auth_password = "w9ROhxNQjA4y6dXa";
    Session mailSession;



    SendEmail(){
        Properties props = new Properties();
        props.put("mail.smtp.host", auth_host);
        props.put("mail.smtp.socketFactory.port", auth_port);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", auth_port);

        mailSession = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication
                                (auth_email,auth_password);
                    }
                });


    }

    public void sendOTP(String recipient,String OTP) throws MessagingException {
        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("germaincom3@gmail.com")); // From

        /*** Recipient ***/

        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient)); // To

        message.setSubject("OTP for E-mail Address Verification Random Phone Number");
        message.setText("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\n" +
                "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n" +
                "    <div style=\"border-bottom:1px solid #eee\">\n" +
                "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">Random Phone Number E-mail Address Verification Code</a>\n" +
                "    </div>\n" +
                "    <p style=\"font-size:1.1em\">Hi,</p>\n" +
                "    <p>ตามที่ท่านได้ทำการลงทะเบียนอีเมลบนบริการ Random Phone Number กรุณานำรหัสยืนยันกรอกลงในหน้ายืนยันอีเมลบนโปรแกรม กรุณานำรหัสนี้ไปใช้ภายใน 24 ชม.</p>\n" +
                "    <p>To confirm this E-mail address, please enter the verification code listed below on Random phone number application. This code is valid within 24 hours.</p>\n" +
                "    <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">"+OTP+"</h2>\n" +
                "    <hr style=\"border:none;border-top:1px solid #eee\" />\n" +
                "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n" +
                "      <p>Germain</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>");

        Transport.send(message);

        System.out.println("Mail Send Successfully.");
    }



    }



