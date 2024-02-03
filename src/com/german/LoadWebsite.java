package com.german;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoadWebsite {
    public static void loadWebsite(){
        String websiteURL = "https://dev-social-marketing-v1.pantheonsite.io/"; // Your initial website URL here

        try {
            URL url = new URL(websiteURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            // Check if the response code indicates a redirection (301 or 302)
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                // Get the new location from the "Location" header field
                String newURL = connection.getHeaderField("Location");
                System.out.println("The resource has been moved to: " + newURL);
                // Update the website URL to the new location
                websiteURL = newURL;
                // Now you can make a new connection to the new URL if needed
                url = new URL(websiteURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                responseCode = connection.getResponseCode();
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Website accessed successfully.");
                // Continue with your application logic here
            } else {
                System.out.println("Failed to access website. Response code: " + responseCode);
                // Handle other response codes as needed
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any IOException that occurs during the connection attempt
        }
    }
}
