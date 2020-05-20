package com.unipi.ttheodosiou.dailyevents;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class ResetClass extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Katastimata.resetRatings();
    }
}
