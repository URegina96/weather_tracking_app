package com.example.weathertrackingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadJSONTask task = new DownloadJSONTask();
        task.execute("https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}");
    }

    private static class DownloadJSONTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
//            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.i("fserfse", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
//                String name = jsonObject.getString("name");
//                Log.i("pasfe", name);
                JSONObject main = jsonObject.getJSONObject("main");
                String temp = main.getString("main.temp");
                String pressure = main.getString("main.pressure");
//                Log.i("Spasfe", main.temp);
//                Log.i("Fpasfe", main.pressure);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}