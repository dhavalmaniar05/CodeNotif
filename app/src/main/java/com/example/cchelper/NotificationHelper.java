package com.example.cchelper;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static String one="Channel1";
    public static String oneid="Channel1ID";
    public static String two="Channel2";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannels();

    }
    public void createChannels()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1= new NotificationChannel(oneid,one, NotificationManager.IMPORTANCE_DEFAULT);
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLightColor(R.color.black);
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(channel1);
        }
    }
    public NotificationManager getManager()
    {
        if(mManager==null)
            mManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return mManager;
    }
    public NotificationCompat.Builder getCHannelNotification(String title,String message)
    {
        return new NotificationCompat.Builder(getApplicationContext(),oneid).setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.ic_launcher_foreground);
    }
}