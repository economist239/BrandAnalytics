package ru.brandanalyst.frontend.services.youtube;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequester {
    public static String sendGetRequest(String request) {
        String result = null;
        if (!request.startsWith("http://") || request.indexOf(' ') != -1) {
            return null;
        }
        try {
            // Send a GET request to the servlet
            URLConnection conn = new URL(request).openConnection();
            result = readStreamToString(conn.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String readStreamToString(InputStream in) throws IOException {
        StringBuffer b = new StringBuffer();
        InputStreamReader r = new InputStreamReader(in);

        int c;
        while ((c = r.read()) != -1) {
            b.append((char) c);
        }

        return b.toString();
    }
}
