package com.unipi.ttheodosiou.dailyevents;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Thanos on 5/2/2018.
 */

public class ParseData extends AsyncTask<Void,Void,Void>
{
    static int number_of_events= 25;
    String url1="http://api.eventful.com/json/events/search?app_key="+BuildConfig.ApiKey+"&location="+"Athens"+"&page_size="+String.valueOf(number_of_events);
    String data="";
    static boolean done=false;
    static String[] Title=new String[number_of_events];
    static String[] URL=new String[number_of_events];
    static String[] StartTime=new String[number_of_events];
    static String[] Address=new String[number_of_events];

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            URL url = new URL(url1);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader  bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            String line="";
            while(line != null)
            {
                line = bufferedReader.readLine();
                data=data+line;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            final JSONObject MainObject= new JSONObject(data);
            final JSONObject Events= MainObject.getJSONObject("events");
            final JSONArray Event = Events.getJSONArray("event");

            for (int i=0;i<Event.length();i++)
            {
                    JSONObject  obj= (JSONObject) Event.get(i);

                    //αποθήκευση δεδομένων
                    Title[i]=obj.getString("title");
                    URL[i]=obj.getString("url");
                    StartTime[i]=obj.getString("start_time");
                    Address[i]=obj.getString("venue_address")+" "+obj.getString("region_name")+","+obj.getString("city_name");
            }
            done=true;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
