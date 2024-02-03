package com.german;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class OTP {

    public String getOTP(){
        Random r = new Random();


            LocalDateTime now = LocalDateTime.now();
            ArrayList<String> randomChar = new ArrayList<>();


            int min = 1000;
            int max = 9999;

            String day = String.valueOf(now.getDayOfMonth());
            String month = String.valueOf(now.getMonthValue());

            String hour = String.valueOf(now.getHour());
            String minute = String.valueOf(now.getMinute());
            String sec = String.valueOf(now.getSecond());

            String fullTime = day + month + hour + minute + sec;

            String alphabet = "QAZWSXEDCRFVTGBYHNUJMIKOLPQWERTYUIOPASDFGHJKLZXCVBNMQPWOEIRUTYALSKDJFHGZMXNCBV";
            for (int i = 0; i < 3; i++) {
                randomChar.add(String.valueOf(alphabet.charAt(r.nextInt(alphabet.length()))));
            }

            String fullChar = randomChar.get(0) + randomChar.get(1) + randomChar.get(2);

            int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
//            int random_int_confirm = (int) Math.floor(Math.random() * (max - min + 1) + min);

            int fullRandomNum = random_int;

            String refNo = fullTime + fullChar + fullRandomNum;

            System.out.println(refNo);
            return refNo;

    }

}
