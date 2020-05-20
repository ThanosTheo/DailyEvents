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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    public static TextView data;
    LinearLayout linearLayout;
    TextView[] textViewArray = new TextView[ParseData.number_of_events];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout=(LinearLayout) findViewById(R.id.linearLayout);
        for(int i=0;i<ParseData.number_of_events;i++)
            {
                textViewArray[i]= new TextView(this);
                //textViewArray[i].setText(String.valueOf(i));
                textViewArray[i].setTextColor(Color.BLUE);
                textViewArray[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textViewArray[i].setTextSize(20);
                linearLayout.addView(textViewArray[i]);
            }

        Button getEvents=(Button) findViewById(R.id.getEvents);
        getEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Makelayout();
            }
        });

        Makelayout();

        Button goToStores=(Button) findViewById(R.id.go_to_stores);
        goToStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),katastimata_diaskedasis.class);
                startActivity(intent);
            }
        });

        prefs = getSharedPreferences("com.ttheodosiou.dailyevents", MODE_PRIVATE);
    }

    public void Makelayout()
    {
        //εμφάνηση event
        ParseData task=new ParseData();
        task.execute();

        if(ParseData.done)
        {
            task.cancel(true);
            for(int i=0;i<ParseData.number_of_events;i++)
            {
                textViewArray[i].setText(ParseData.Title[i]);
                final int finalI = i;
                textViewArray[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ParseData.URL[finalI]));
                        startActivity(browserIntent);
                    }
                });
            }
            ParseData.done=false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {

            AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, ResetClass.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, alarmIntent);

            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}
