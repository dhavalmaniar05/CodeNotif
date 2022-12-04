package com.example.cchelper;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    View mview;
    TextView txttitle,txttime;
    CardView noteCard;
    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        mview=itemView;
        txttitle=(TextView) mview.findViewById(R.id.note_title);
        txttime=(TextView) mview.findViewById(R.id.note_time);
        noteCard = mview.findViewById(R.id.note_card);
    }
    public void setNoteTitle(String Title){
        txttitle.setText(Title);

    }
    public void setNoteTime(String time)
    {
        txttime.setText(time);
    }
}