package com.example.cchelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.timepicker.MaterialTimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class contestsAdapter extends RecyclerView.Adapter<contestsAdapter.ViewHolder> implements TimePickerDialog.OnTimeSetListener{
    private List<ListItems> listitem;
    private Context context;

    public contestsAdapter(List<ListItems> listitem, Context context) {
        this.listitem = listitem;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.contestlist,parent,false);
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

        ListItems listitm=listitem.get(position);
        holder.heading.setText(listitm.getHeading());
        holder.desc.setText(listitm.getDesc());
        holder.desc.setMovementMethod(LinkMovementMethod.getInstance());
       if(listitem.get(position).isIsexpanded())
       {
           TransitionManager.beginDelayedTransition(holder.parent);
           holder.relativeLayout.setVisibility(View.VISIBLE);
           holder.dwnarrow.setVisibility(View.GONE);
       }
       else
       {
           TransitionManager.beginDelayedTransition(holder.parent);
           holder.relativeLayout.setVisibility(View.GONE);

     };
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder nb=holder.mNotificationHelper.getCHannelNotification(listitm.getHeading(),listitm.getDesc());
                holder.mNotificationHelper.getManager().notify(1,nb.build());
            }

        });
        holder.alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(((AppCompatActivity)context).getSupportFragmentManager()," time picker");
            }
        });


    }

    @Override
    public int getItemCount() {

        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView desc;
        public TextView heading;
        public RelativeLayout relativeLayout;
        private CardView parent;
        private ImageView dwnarrow;
        private ImageView upArrow;
        private ImageView alarm;
        private NotificationHelper mNotificationHelper;
        private MaterialTimePicker picker;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             heading= (TextView) itemView.findViewById(R.id.text1);
             dwnarrow=itemView.findViewById(R.id.imgdown);
             upArrow=itemView.findViewById(R.id.imgup);
             desc=(TextView) itemView.findViewById(R.id.desc);
             relativeLayout=itemView.findViewById(R.id.expandableview);
             parent=itemView.findViewById(R.id.pnt);
             mNotificationHelper= new NotificationHelper(context);
             alarm=itemView.findViewById(R.id.alarm);
             dwnarrow.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     ListItems item=listitem.get(getAbsoluteAdapterPosition());
                     item.setIsexpanded(!item.isIsexpanded());
                     notifyItemChanged(getAbsoluteAdapterPosition());
                 }
             });
   upArrow.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           ListItems item=listitem.get(getAbsoluteAdapterPosition());
           item.setIsexpanded(!item.isIsexpanded());
           notifyItemChanged(getAbsoluteAdapterPosition());
       }
   });

         }

     }



}
