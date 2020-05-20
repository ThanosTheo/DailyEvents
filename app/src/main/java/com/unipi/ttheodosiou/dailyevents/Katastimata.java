package com.unipi.ttheodosiou.dailyevents;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import javax.security.auth.callback.Callback;

/**
 * Created by Thanos on 8/2/2018.
 */

public class Katastimata extends AppCompatActivity{



    static int[] Ratings=new int[6];
    public  static void getRating(String katastima, final int i)
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        final DatabaseReference tempReference= databaseReference.child(katastima.trim()).child("Rating");

        tempReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Ratings[i]=dataSnapshot.getValue(Integer.class);
                if(Ratings[i]<0)
                {
                    katastimata_diaskedasis.ratingsArray[i].setText("N.R.Y.");
                }
                else
                {
                    katastimata_diaskedasis.ratingsArray[i].setText(Katastimata.Ratings[i]+"/10");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static  void setRating(final int number,final int rating)
    {

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        final DatabaseReference tempReference= databaseReference.child(katastimata_diaskedasis.katastimataArray[number].getText().toString().trim()).child("Rating");
        tempReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(Ratings[number]==-1)
                {
                    tempReference.setValue(rating);
                    Ratings[number]=rating;
                }
                else
                {
                    Ratings[number]=(Ratings[number]+rating)/2;
                    tempReference.setValue(Ratings[number]);
                }
                getRating(katastimata_diaskedasis.katastimataArray[number].getText().toString(),number);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static  void getAddress(String katastima)
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference();
        final DatabaseReference tempReference= databaseReference.child(katastima.trim()).child("Address");

        tempReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Rate_Katastima.addressbox.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


     public static void resetRatings()
    {
        String[] stores=new String[6];
        stores[0]="Akanthus Summer Club";
        stores[1]="Frontistirio Club";
        stores[2]="Geluk";
        stores[3]="Half Note Jazz Club";
        stores[4]="Ola White Club";
        stores[5]="Vega Summer Club Restaurant";
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        for(int i=0;i<6;i++)
        {
            DatabaseReference tempReference= databaseReference.child(stores[i].trim()).child("Rating");
            tempReference.setValue(-1);
        }
    }
}
