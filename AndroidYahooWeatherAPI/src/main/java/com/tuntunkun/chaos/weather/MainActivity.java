package com.tuntunkun.chaos.weather;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    /*
     * My information about Yahoo Weather API
     */
    private String key      = "dj0zaiZpPW1lSUozbFZabTd1RSZkPVlXazlVVlpZTkVFeE5XRW1jR285TUEtLSZzPWNvbnN1bWVyc2VjcmV0Jng9OWY-";
    private String position = "140.22036,39.611813";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Get weather information from Yahoo API
         */
        try {
            YahooWeatherAPI yahooapi = new YahooWeatherAPI(key, position);
            Weathers weathers = yahooapi.getWeathers();

            /*
             * define layout
             */
            LinearLayout linerLayout = new LinearLayout(this);
            linerLayout.setOrientation(LinearLayout.VERTICAL);
            this.setContentView(linerLayout);

            TextView text = new TextView(this);
            text.setText(weathers.toString());
            linerLayout.addView(text);

            for (Weather weather:weathers) {
                TextView t = new TextView(this);
                t.setText(weather.toString());
                linerLayout.addView(t);
            }

        } catch (IOException e) {
            // write code here, when internet access failed.
        } catch (XMLParseException e) {
            // write code here, when xml parse failed.
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
