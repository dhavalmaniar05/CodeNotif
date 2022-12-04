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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class CodeForcesContests extends RecyclerView.Adapter<CodeForcesContests.ViewHolder> implements TimePickerDialog.OnTimeSetListener{

    private List<ListItems> contest_data;
    private Context context;

    public CodeForcesContests(List<ListItems> contestdata, Context context) {

        this.contest_data=contestdata;
        this.context = context;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.codeforcescontests,parent,false);
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

        Log.d("alankar", "onBindViewHolder1: ");
        ListItems data=contest_data.get(position);
        Log.d("alankar", "onBindViewHolder2: ");
        holder.name.setText(data.getHeading());
        Log.d("alankar", "onBindViewHolder3: ");
        holder.start_time.setText(data.getDesc());
        Log.d("alankar", "onBindViewHolder4: ");
    }

    @Override
    public int getItemCount() {
        Log.d("animish", "getItemCount: "+ contest_data.size());
        return contest_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView hard;
        private TextView medium;
        private  TextView easy;
        private TextView total;
        private TextView rate;
        private ProgressBar pbar1;
        private ProgressBar pbar2;
        private ProgressBar pbar3;
        private TextView har;
        private TextView med;
        private TextView esy;
        private TextView name;
        private TextView start_time;
        private TextView end_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.namecodeforces);
            start_time=(TextView) itemView.findViewById(R.id.start_time_codeforces);




        }

    }



}
