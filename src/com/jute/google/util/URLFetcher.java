package com.jute.google.util;

import com.google.appengine.api.urlfetch.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.SocketTimeoutException;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 9:52:56 AM
 */
public class URLFetcher {

    public static HTTPResponse fetch(URL url,Object ... args) throws IOException {
        URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        HTTPRequest request = new HTTPRequest(url);
        return service.fetch(request);
    }

    public static String get(URL url,Object ... args) throws IOException, SocketTimeoutException {
        BufferedReader reader=null;
        try {            
            long t1 = System.currentTimeMillis();
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int connectTimeout = 1000;
            int readTimeout = 5000;
            if (args.length>0 && args[0] instanceof Integer) {
                connectTimeout = (Integer) args[0];
            }
            if (args.length>1 && args[1] instanceof Integer) {
                readTimeout = (Integer) args[1];
            }
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setDoInput ( true ) ;
            connection.setDoOutput ( false ) ;             
            connection.setRequestMethod (  "GET"  );
            connection.setRequestProperty("jute_monitor", "true");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.9) Gecko/2009040820 Firefox/3.0.9,gzip(gfe)");
            connection.connect();
            long t2 = System.currentTimeMillis();
            System.err.println(t2-t1);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            String line;
            StringBuilder sb = new StringBuilder();
            while ( (line = reader.readLine())!=null ) {
                sb.append(line);
            }            
            long t3 = System.currentTimeMillis();
            System.err.println(t3-t2);
            return sb.toString();
        }
        finally {
            if (reader!=null) {
                reader.close();
            }
        }
    }
}
