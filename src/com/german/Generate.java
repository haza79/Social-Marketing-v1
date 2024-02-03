package com.german;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Generate {

    DatabaseAction dbaction = new DatabaseAction()  ;

    public Generate() throws SQLException, ClassNotFoundException {
    }

    public String getGenerateNumber(String countryCode,String prefix,String format){

        int formatCount = getNumberFormatCount(format);
        int formatLength = getNumberlength(formatCount);
        int max = getMaxValue(formatLength);
        int random = ThreadLocalRandom.current().nextInt(formatLength, max + 1);
        System.out.println("format count : "+formatCount);
        System.out.println("format length : "+formatLength);
        System.out.println("max : "+max );
        System.out.println("random : "+random);
        String fullNumber = countryCode+""+prefix+""+random;
        System.out.println("full name = "+fullNumber);
        return fullNumber;


    }

    //new


    public String getCountryCode(String countryID) throws SQLException {
        String countryCode = dbaction.getNumberCountryCode(countryID);
        return countryCode;
    }

    public ArrayList getPrefix(String countryID) throws SQLException {
        ArrayList<String> prefix = dbaction.getNumberPrefix(countryID);
        return prefix;
    }

    public int formatCount(String countryID) throws SQLException {
        int formatCount = getNumberFormatCount(dbaction.getNumberFormat(countryID));
        return formatCount;
    }

    //new

    public String getGenerateNumberAuto(String countryID ,boolean addCountryCode) throws SQLException {

        String countryCode = dbaction.getNumberCountryCode(countryID);
        System.out.println("generate : "+countryCode);

        ArrayList<String> prefix = dbaction.getNumberPrefix(countryID);
        System.out.println("prefix : "+prefix);

        int formatCount = getNumberFormatCount(dbaction.getNumberFormat(countryID));
        System.out.println("format count : "+formatCount);

        int formatLength = getNumberlength(formatCount);
        System.out.println("format length : "+formatLength);

        int max = getMaxValue(formatLength);
        System.out.println("max : "+max);

        int random = ThreadLocalRandom.current().nextInt(formatLength, max + 1);
        System.out.println("random : "+random);

        int randomPrefix = ThreadLocalRandom.current().nextInt(0, prefix.size() );
        System.out.println("random prefix : "+randomPrefix+" :: "+prefix.get(randomPrefix));

        System.out.println("format count : "+formatCount);
        System.out.println("format length : "+formatLength);
        System.out.println("max : "+max );
        System.out.println("random : "+random);
        String fullNumber;

        if (addCountryCode == true){
            fullNumber = countryCode+""+prefix.get(randomPrefix)+""+random;
        }else {
            fullNumber = "0"+prefix.get(randomPrefix)+""+random;
        }

        System.out.println("full name = "+fullNumber);
        System.out.println("[from generate] get gerenate number auto : "+fullNumber);

        return fullNumber;

    }


    public String getGenerateNumberAuto2(String countryCode, ArrayList prefix, int formatCount,boolean addCountryCode) throws SQLException {

//        String countryCode = dbaction.getNumberCountryCode(countryID);
//        System.out.println("generate : "+countryCode);
//
//        ArrayList<String> prefix = dbaction.getNumberPrefix(countryID);
//        System.out.println("prefix : "+prefix);
//
//        int formatCount = getNumberFormatCount(dbaction.getNumberFormat(countryID));
//        System.out.println("format count : "+formatCount);

        int formatLength = getNumberlength(formatCount);
        System.out.println("format length : "+formatLength);

        int max = getMaxValue(formatLength);
        System.out.println("max : "+max);

        int random = ThreadLocalRandom.current().nextInt(formatLength, max + 1);
        System.out.println("random : "+random);

        int randomPrefix = ThreadLocalRandom.current().nextInt(0, prefix.size() );
        System.out.println("random prefix : "+randomPrefix+" :: "+prefix.get(randomPrefix));

        System.out.println("format count : "+formatCount);
        System.out.println("format length : "+formatLength);
        System.out.println("max : "+max );
        System.out.println("random : "+random);
        String fullNumber;

        if (addCountryCode == true){
            fullNumber = countryCode+""+prefix.get(randomPrefix)+""+random;
        }else {
            fullNumber = "0"+prefix.get(randomPrefix)+""+random;
        }

        System.out.println("full name = "+fullNumber);
        System.out.println("[from generate] get gerenate number auto : "+fullNumber);

        return fullNumber;

    }




    public int getNumberFormatCount(String format){
        int count = format.length();
        return count;
    }

    public int getNumberlength(int count){
        String number ="1";
        for (int i = 0; i<count-1; i++){
            number+="0";
        }
        return Integer.parseInt(number);


    }

    public int getMaxValue(int min){

        String strMin = String.valueOf(min);
        String replace1;
        replace1 = strMin.replaceAll("1","9" );
        String replace2 = replace1.replaceAll("0","9");
//        strMin.replaceAll("0","9");
//        strMin.replaceAll("1","9");
//        strMin = strMin.substring(0, 0) + "9"
//                + strMin.substring(0 + 1);

        int max = Integer.parseInt(replace2);

        return max;

    }








}
