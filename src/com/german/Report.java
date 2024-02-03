package com.german;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Report {
    String projectRoot = System.getProperty("user.dir");
    public void printReport(ArrayList<String> licenseKey,ArrayList<String> duration) throws IOException {
        File f = new File(projectRoot+File.separator+"report\\index.html");
        BufferedWriter bw = new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8));

        bw.write("" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            font-family: arial, sans-serif;\n" +
                "            border-collapse: collapse;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "        \n" +
                "        td,\n" +
                "        th {\n" +
                "            border: 1px solid #dddddd;\n" +
                "            text-align: left;\n" +
                "            padding: 8px;\n" +
                "        }\n" +
                "        \n" +
                "        tr:nth-child(even) {\n" +
                "            background-color: #dddddd;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "    <h2>report</h2>\n" +
                "\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>No</th>\n" +
                "            <th>License Key</th>\n" +
                "            <th>Duration</th>\n" +
                "        </tr>" +
                "");

        for (int i = 0; i<licenseKey.size(); i++){
            bw.write("<tr>\n" +
                    "            <td>"+(i+1)+"</td>\n" +
                    "            <td>"+licenseKey.get(i)+"</td>\n" +
                    "            <td>"+duration.get(i)+"</td>\n" +
                    "        </tr>");
        }

        bw.write("\n" +
                "    </table>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>");

    }

}
