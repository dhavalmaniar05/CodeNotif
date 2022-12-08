package com.example.cchelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getCHannelNotification("Contest Reminder","Its time for ur Contest. Kindly Check upcoming Contests Lists");

        notificationHelper.getManager().notify(1, nb.build());
    }
}