package com.german;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class encryption {

    private static final String SECRET_KEY = "jtCd5BMYi.zs@6ANyAj%m!PQA-T7~bD^>,r.k!^#WayErA4i8,bDtpTZKEj^}4:4:a14oJf5+6)@GYs=bq_Aexw0zp+x:y:~QNyv";
    private static final String SALT = "3,2X*UWf}KHMZ]Bhi}oeXnT+-QvhZ*2vop^+T^NPenQHecCH!=Bdb=UN*EYTm+Zu+*zfP0!3Hn^GC^_QPk_k>dvHwGhdB~n*BvE!";

    public String encrypt(String strToEncrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
    public String decrypt(String strToDecrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }



    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        encryption encryption = new encryption();

//        String originalString = "howtodoinjava.com";
//
//        String encryptedString = encryption.encrypt(originalString);
//        String decryptedString = encryption.decrypt(encryptedString);
//
//        System.out.println(originalString);
//        System.out.println(encryptedString);
//        System.out.println(decryptedString);



        System.out.println("0] encryp");
        System.out.println("1] decryp");
        String s = scanner.nextLine();
        if (s.equals("0")){
            while(true){
                System.out.println("===== encryption =====");
                String originalString = scanner.nextLine();

                String encryptedString = encryption.encrypt(originalString);
//                String decryptedString = encryption.decrypt(encryptedString);

//                System.out.println(originalString);
                System.out.println(encryptedString);
//                System.out.println(decryptedString);
                System.out.println("===== encryption =====\n");

            }
        }else {
            while(true){
                System.out.println("===== decryption =====");
                System.out.print("encryp   : ");
                String originalStr = scanner.nextLine();
                String decryp = encryption.decrypt(originalStr);
                System.out.println("decryp : "+decryp);
                System.out.println("===== decryption =====\n");

            }
        }











    }

}
