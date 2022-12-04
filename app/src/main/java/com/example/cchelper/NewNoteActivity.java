package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class NewNoteActivity extends AppCompatActivity {

    private TextInputLayout tt;
    private TextInputLayout dc;

    private EditText title;
    private EditText desc;

    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;
    private FirebaseUser currUser;

    private String noteID;

    private boolean isExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        tt=findViewById(R.id.new_note_title);
        dc=findViewById(R.id.new_note_desc);

        title = tt.getEditText();
        desc = dc.getEditText();

        mauth = FirebaseAuth.getInstance();
        currUser = mauth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notes").child(currUser.getUid());

        Button btn = findViewById(R.id.buttonCreateNote);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title.getText().toString();
                String d = desc.getText().toString();
                if(TextUtils.isEmpty(t)){
                    title.setError("Title is Required!");
                    title.requestFocus();
                }else if(TextUtils.isEmpty(d)){
                    desc.setError("Description is required");
                    desc.requestFocus();
                }else{
                    createNote(t,d);
                }
            }
        });

    }

    private void createNote(String t, String d) {
        if(currUser!=null){
            DatabaseReference newNote =databaseReference.push();
            Map notemap =new HashMap();
            notemap.put("title",t);
            notemap.put("content",d);
            notemap.put("timestamp", ServerValue.TIMESTAMP);
            Thread mainthread=new Thread(new Runnable() {
                @Override
                public void run() {
                    newNote.setValue(notemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(NewNoteActivity.this, "Note Added", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(NewNoteActivity.this, "Note Not Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
            mainthread.start();
        }
        else{
            Toast.makeText(this, "User Not Signed In!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NewNoteActivity.this, LoginActivity.class));
            finish();
        }

    }

    private void deleteNote() {

        databaseReference.child(noteID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(NewNoteActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                    noteID = "none";
                    finish();
                } else {
                    Log.e("NewNoteActivity", task.getException().toString());
                    Toast.makeText(NewNoteActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}