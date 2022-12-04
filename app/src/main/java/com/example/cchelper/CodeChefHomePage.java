package com.example.cchelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class CodeChefHomePage extends RecyclerView.Adapter<CodeChefHomePage.ViewHolder> implements TimePickerDialog.OnTimeSetListener{
    private List<com.example.cchelper.codeChefData> listitem;

    private Context context;

    public CodeChefHomePage(List<com.example.cchelper.codeChefData> listitem, Context context) {
        this.listitem = listitem;

        this.context = context;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.homepagecodechef,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);

        updatetimetext(c);
        startalarm(c);
    }
    private void updatetimetext(Calendar c)
    {
        String timetext="Alarm set for :";
        timetext+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

    }
    private void startalarm(Calendar c)
    {
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent=new Intent(context,AlertReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("Dhaval", "onBindViewHolder1: " );
        com.example.cchelper.codeChefData listitm=listitem.get(position);
        Log.e("Dhaval", "onBindViewHolder2: " + listitm.getUsername() );
        holder.name.setText("Name - "+ listitm.getUsername());
        Log.e("Dhaval", "onBindViewHolder3: " );
        holder.rating.setText("Current Rating- "+listitm.getRating().toString());
        Log.e("Dhaval", "onBindViewHolder4: " );
        holder.globalRank.setText("Global Rank - "+listitm.getGlobalRank().toString());
        Log.e("Dhaval", "onBindViewHolder5: " );
        holder.countryrank.setText("Country Rank -" + listitm.getCountryRank().toString());
        Log.e("Dhaval", "onBindViewHolder6: " );
        holder.totalprob.setText("Total Problems Solved -" + listitm.getProblemsSolved().toString());
        Log.e("Dhaval", "onBindViewHolder7: " );

    }

    @Override
    public int getItemCount() {

        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       private TextView name;
       private TextView rating;
       private TextView highestRating;
       private TextView globalRank;
       private TextView countryrank;
       private TextView totalprob;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.Name);
            rating=(TextView) itemView.findViewById(R.id.rating);

            globalRank=(TextView) itemView.findViewById(R.id.globalrank);
            countryrank=(TextView) itemView.findViewById(R.id.countryRank);
            totalprob=(TextView) itemView.findViewById(R.id.Totalproblems);





        }

    }



}
