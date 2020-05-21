package com.unipi.ttheodosiou.dailyevents;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  implements Adapter.OnEventListener {

    SharedPreferences prefs = null;

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.ttheodosiou.dailyevents", MODE_PRIVATE);

        title = new ArrayList<>();
        content = new ArrayList<>();
        try {
            Load_Events();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,title,content, this);
        recyclerView.setAdapter(adapter);
    }

    public void Load_Events() throws ExecutionException, InterruptedException {
        //get events
        ParseData task=new ParseData();
        task.execute().get();

        if(ParseData.done)
        {
            task.cancel(true);

            //fill cards
            for(int i=0; i<ParseData.number_of_events; i++){
                title.add(ParseData.Title[i]);
                content.add(ParseData.StartTime[i]);
            }

            ParseData.done=false;
        }
    }

    @Override
    public void onEventClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(ParseData.URL[position]));
        startActivity(browserIntent);
    }
}
