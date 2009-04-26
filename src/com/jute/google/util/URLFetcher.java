package com.jute.google.util;

import com.google.appengine.api.urlfetch.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.SocketTimeoutException;
import java.io.*;

/**
 * 
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 9:52:56 AM
 */
public class URLFetcher {

    public static com.google.appengine.api.urlfetch.HTTPResponse fetch(URL url,Object ... args) throws IOException {
        URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        HTTPRequest request = new HTTPRequest(url);
        return service.fetch(request);
    }

    public static HTTPResponse get(URL url,Object ... args) {
        HTTPResponse response = null;
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
            connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.9) Gecko/2009040820 Firefox/3.0.9,gzip(gfe)");
            connection.connect();
            long t2 = System.currentTimeMillis();
            response = new HTTPResponse(connection.getResponseCode());
            response.setConnectTime((int) (t2-t1));
            if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                InputStream input = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int n = -1;
                while ((n=input.read(buffer))!=-1) {
                    out.write(buffer,0,n);
                }
                response.setContent(out.toByteArray());
            }
            long t3 = System.currentTimeMillis();
            response.setReadTime((int) (t3-t2));
        }
        catch (SocketTimeoutException e) {
            response = new HTTPResponse(-1);
        }
        catch (IOException e1) {
            response = new HTTPResponse(-2);
        }
        return response;
    }
}
