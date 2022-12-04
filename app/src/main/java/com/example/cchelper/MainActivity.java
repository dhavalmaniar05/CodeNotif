package com.example.cchelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {


    private NotificationHelper mNotificationHelper;

    ProgressBar progressBar;
    TextView textView;

    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progress);
        textView = findViewById(R.id.textView);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();


        mNotificationHelper= new NotificationHelper(this);
    }

    public void progressAnimation(){

        Animation anim = new Animation(this, progressBar, textView, 0f, 100f);

        anim.setDuration(2500);
        progressBar.setAnimation(anim);
    }




    public void sendOnchannel1(String title,String Message)
    {
        NotificationCompat.Builder nb=mNotificationHelper.getCHannelNotification(title,Message);
        mNotificationHelper.getManager().notify(1,nb.build());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.MONTH,1);//month
        calSet.set(Calendar.YEAR,2015);//year
        calSet.set(Calendar.DAY_OF_MONTH,12);//day
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }



        updatetimetext(calSet);
        startalarm(calSet);
    }
    private void updatetimetext(Calendar c)
    {
        String timetext="Alarm set for :";
        timetext+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

    }
    private void startalarm(Calendar c)
    {
        AlarmManager alarmManager=(AlarmManager) getSystemService(this.ALARM_SERVICE);
        Intent intent=new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        if(c.before(Calendar.getInstance()))
            c.add(Calendar.DATE,1);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
}