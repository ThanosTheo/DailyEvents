package com.unipi.ttheodosiou.dailyevents;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class katastimata_diaskedasis extends AppCompatActivity {


    static TextView katastimataArray[]=new TextView[6];
    static TextView ratingsArray[]=new TextView[6];
    int RatingArray[]=new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katastimata_diaskedasis);

        Button goToEvents=(Button) findViewById(R.id.go_to_events);
        goToEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        katastimataArray[0]=(TextView) findViewById(R.id.katastima);
        ratingsArray[0]=(TextView) findViewById(R.id.rating);
        katastimataArray[1]=(TextView) findViewById(R.id.katastima1);
        ratingsArray[1]=(TextView) findViewById(R.id.rating1);
        katastimataArray[2]=(TextView) findViewById(R.id.katastima2);
        ratingsArray[2]=(TextView) findViewById(R.id.rating2);
        katastimataArray[3]=(TextView) findViewById(R.id.katastima3);
        ratingsArray[3]=(TextView) findViewById(R.id.rating3);
        katastimataArray[4]=(TextView) findViewById(R.id.katastima4);
        ratingsArray[4]=(TextView) findViewById(R.id.rating4);
        katastimataArray[5]=(TextView) findViewById(R.id.katastima5);
        ratingsArray[5]=(TextView) findViewById(R.id.rating5);

        for(int i=0;i<6;i++)
        {
            katastimataArray[i].setTextColor(Color.BLUE);
        }

        for(int i=0;i<6;i++)
        {
        Katastimata.getRating((String)katastimataArray[i].getText(),i);
        final int number_of=i;
        katastimataArray[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Rate_Katastima.class);
                intent.putExtra("Katastima", number_of);
                startActivity(intent);
            }
        });
        }
    }
}
