package com.german;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class licenseDateAndTime {

    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime getStartTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        return myDateObj;
    }

    public static LocalDateTime getStartTimeNonStatic(){
        LocalDateTime myDateObj = LocalDateTime.now();
        return myDateObj;
    }

    public static String getStringStartTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        String formattedDate = myDateObj.format(format);
        return formattedDate;
    }

    public String getExpiredTime(String startDate,int hour){
        LocalDateTime startTimeObj = LocalDateTime.parse(startDate,format);

        LocalDateTime endTimeObj = startTimeObj.plusHours(hour);
        String endDateTime = endTimeObj.format(format);
        return endDateTime;
    }

    public String dateToString(LocalDateTime dateTime){

        String endDateTime = dateTime.format(format);
        return endDateTime;

    }

    public static LocalDateTime stringToDate(String date){
        LocalDateTime timeObj = LocalDateTime.parse(date,format);
        return timeObj;
    }

    public LocalDateTime stringToDateNonStatic(String date){
        LocalDateTime timeObj = LocalDateTime.parse(date,format);
        return timeObj;
    }

    public static ArrayList<LocalDateTime> sortDate(ArrayList<String> inputStringDate){

        System.out.println("input string date = "+inputStringDate);
        ArrayList<LocalDateTime> dateSorted = new ArrayList<LocalDateTime>();

        for (int i = 0; i<inputStringDate.size(); i++){
            String inputDate = inputStringDate.get(i);
            System.out.println(inputDate+"\tinput date");
            LocalDateTime startTimeObj = LocalDateTime.parse(inputDate,format);
            dateSorted.add(startTimeObj);
        }

        System.out.println("before");
        System.out.println(dateSorted);

        Collections.sort(dateSorted, new Comparator<LocalDateTime>() {
            @Override
            public int compare(LocalDateTime o1, LocalDateTime o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println("after sort");
        System.out.println(dateSorted);

        return dateSorted;

    }


    public static LocalDateTime getFinalExpiredTime(ArrayList allStartDate, ArrayList allDuration){

        ArrayList<LocalDateTime> sortTime = sortDate(allStartDate);

        LocalDateTime firstActivate = sortTime.get(0);
        int totalDuration = 0;
        for (int i = 0; i<allDuration.size(); i++){
            int duration = (int) allDuration.get(i);
            totalDuration+=duration;
        }

        LocalDateTime finalExpiredTime = firstActivate.plusHours(totalDuration);
        return finalExpiredTime;

    }

    public long dbYear;
    public long dbMounth;
    public long dbDay;
    public long dbHour;
    public long dbMinute;
    public long dbSecond;


    public static long[] findBetweenStartDateAndEnd(LocalDateTime fromDateTime, LocalDateTime toDateTime){

        LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

        long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS );
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS );
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS );
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS );

        System.out.println( years + " years " +
                months + " months " +
                days + " days " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds.");

        long[] duration = new long[7];
        duration[0] = years;
        duration[1] = months;
        duration[2] = days;
        duration[3] = hours;
        duration[4] = minutes;
        duration[5] = seconds;

        return duration;

    }

    public static long[] findBetweenStartDateAndEndNonStatic(LocalDateTime fromDateTime, LocalDateTime toDateTime){

        LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

        long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS );
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS );
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS );
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS );

        System.out.println( years + " years " +
                months + " months " +
                days + " days " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds.");

        long[] duration = new long[7];
        duration[0] = years;
        duration[1] = months;
        duration[2] = days;
        duration[3] = hours;
        duration[4] = minutes;
        duration[5] = seconds;

        return duration;

    }

    public LocalDateTime expiredTime;

    public int yearToHour(int years){
        int hour = years*8765;
        return hour;
    }

    public int monthToHour(int month){
        int hour = month*730;
        return hour;
    }

    public int dayToHour(int day){
        int hour = day*24;
        return hour;
    }


















}


