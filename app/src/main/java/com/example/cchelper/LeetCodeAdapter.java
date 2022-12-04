package com.example.cchelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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

public class LeetCodeAdapter extends RecyclerView.Adapter<LeetCodeAdapter.ViewHolder> implements TimePickerDialog.OnTimeSetListener{
    private List<ContestPagesModel> listitem;

    private Context context;

    public LeetCodeAdapter(List<ContestPagesModel> listitem, Context context) {
        this.listitem = listitem;

        this.context = context;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.leetcode,parent,false);
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

        ContestPagesModel listitm=listitem.get(position);

        holder.hard.setText("Hard-");
        holder.medium.setText("Medium=");
        holder.easy.setText("Easy-");
        holder.har.setText(listitm.getHard()+"/"+listitm.getTotal_easy());
        holder.esy.setText(listitm.getEasy()+"/"+listitm.getTotal_easy());
        holder.med.setText(listitm.getMedium()+"/"+listitm.getTotal_medium());
        holder.total.setText("Total questions Solved- "+listitm.getTotal());
        holder.rate.setText("Acceptance Rate- "+listitm.getAcceptance());
        holder.pbar1.setMax(Integer.parseInt(listitm.getTotal_hard()));

        holder.pbar1.setProgress(Integer.parseInt(listitm.getHard()));
        holder.pbar2.setMax(Integer.parseInt(listitm.getTotal_medium()));

        holder.pbar2.setProgress(Integer.parseInt(listitm.getMedium()));
        holder.pbar3.setMax(Integer.parseInt(listitm.getTotal_easy()));


        holder.pbar3.setProgress(Integer.parseInt(listitm.getEasy()));

    }

    @Override
    public int getItemCount() {

        return listitem.size();
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
            hard=(TextView) itemView.findViewById(R.id.hard);
            easy=(TextView) itemView.findViewById(R.id.easy);
            medium=(TextView)itemView.findViewById(R.id.medium);
            total=(TextView) itemView.findViewById(R.id.Totalproblems);
            rate=(TextView) itemView.findViewById(R.id.acceptancerate);
            pbar1=(ProgressBar) itemView.findViewById(R.id.p_Bar);
            har=(TextView) itemView.findViewById(R.id.hardcount);
            pbar2=(ProgressBar) itemView.findViewById(R.id.p_Bar1);
            pbar3=(ProgressBar) itemView.findViewById(R.id.p_Bar3);
            med=(TextView) itemView.findViewById(R.id.mediumcount);
            esy=(TextView) itemView.findViewById(R.id.easycount);
            name=(TextView) itemView.findViewById(R.id.name);
            start_time=(TextView) itemView.findViewById(R.id.start_time);




        }

    }



}
