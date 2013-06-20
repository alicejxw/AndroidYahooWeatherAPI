package com.tuntunkun.chaos.weather;

/*
 * for debug
 */
import android.util.Log;
import java.io.IOException;

/*
 * for Http access
 */
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/*
 * for parse xml
 */
import java.io.ByteArrayInputStream;
import java.util.Date;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by naoya sawada on 13/06/02.
 */
public class YahooWeatherAPI {
    /*
     * Yahoo weather API
     * http://blog.olp.yahoo.co.jp/archives/20121219_weather.html
     *
     * API key register page
     * http://developer.yahoo.co.jp/
     */
    private final String url = "http://weather.olp.yahooapis.jp/v1";
    private String uri = null;
    private String key;
    private String gps;

    /*
     * configure Yahoo Weather API
     */
    public YahooWeatherAPI(String key, String gps) {
        this.key = key;
        this.gps = gps;

        /*
         * make uri from key + gps
         */
        this.uri = String.format("%s/place?appid=%s&coordinates=%s", this.url, this.key, this.gps);
    }

    /*
     * get xml from Yahoo Weather API
     */
    public Weathers getWeathers() throws IOException, XMLParseException {
        String xml;

        /*
         * Information: We must use android.os.AsyncTask when we access the Internet.
         * From Android 3.0, default StrictMode setting changed to always on.
         * If don't it, we will get the exception.
         *
         * Warning: If you want to access the Internet, you have to declaration on Manifest.xml.
         * <uses-permission android:name="android.permission.INTERNET"/>
         */
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... urls) {
                /*
                 * ignore after first arguments.
                 */
                String url = urls[0];
                String xml = null;

                /*
                 * prepare http request
                 */
                HttpGet method = new HttpGet(url);
                HttpClient client = new DefaultHttpClient();

                try {
                    /*
                     * transmit the http request
                     */
                    HttpResponse response = client.execute(method);

                    /*
                     * confirm http status code
                     */
                    int status = response.getStatusLine().getStatusCode();
                    if (status != HttpStatus.SC_OK) throw new Exception();

                    /*
                     * convert charset to UTF-8
                     */
                    xml = EntityUtils.toString(response.getEntity(), "UTF-8");
                } catch (Exception e) {
                    return null;
                }

                return xml;
            }
        };

        try {
            /*
             * submit request and wait result.
             */
            task.execute(this.uri);
            xml = task.get();
        } catch (Exception e) {
            xml = null;
        }

        if (xml == null) throw new IOException("Failed to get XML data from Yahoo Weather API.\n");

        return new Weathers(xml);
    }
}
