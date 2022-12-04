package com.example.cchelper;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Animation extends android.view.animation.Animation implements Animation1 {

    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from;
    private float to;
    private FirebaseAuth mauth;
    private FirebaseUser currUser;


    public Animation(Context context, ProgressBar progressBar, TextView textView, float from, float to){

        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;

    }


    @Override
    public void applyTransformation(float time, Transformation t){

        super.applyTransformation(time, t);
        float value = from + (to-from)*time;
        progressBar.setProgress((int)value);
        textView.setText((int)value + "%");


        if(value == to){
            mauth = FirebaseAuth.getInstance();
            currUser= mauth.getCurrentUser();
            if(currUser==null){
                context.startActivity(new Intent(context,LoginActivity.class));
            }else{
                context.startActivity(new Intent(context, HomeActivity.class));
            }

        }
    }

    @Override
    public void applyTransformation() {

    }
}
