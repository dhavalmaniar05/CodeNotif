package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NotesListActivity extends AppCompatActivity{
    private FirebaseAuth mauth;
    private RecyclerView recview;
    private DatabaseReference databaseReference;
    private FirebaseUser currUser;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        recview = findViewById(R.id.notes_list);
        mauth = FirebaseAuth.getInstance();
        currUser = mauth.getCurrentUser();

        Button add = findViewById(R.id.addNote_list);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesListActivity.this,NewNoteActivity.class));
            }
        });

        layoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        recview.setLayoutManager(layoutManager);

        if(currUser!=null){
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Notes").child(currUser.getUid());
        }else{
            Toast.makeText(this, "Please Sign In again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NotesListActivity.this,LoginActivity.class));
            finish();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = databaseReference.orderByValue();
        FirebaseRecyclerOptions<NoteModel> options =
                new FirebaseRecyclerOptions.Builder<NoteModel>()
                        .setQuery(query, NoteModel.class)
                        .build();
        Log.d("dhaval","On start");
        FirebaseRecyclerAdapter<NoteModel, NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>(options) {

            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.d("dhaval","hello");
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note_layout,parent,false);
                Log.d("dhaval","hello1");
                return new NoteViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(NoteViewHolder holder, int position,NoteModel model) {
                Log.d("dhaval","hello3");
                String noteid=getRef(position).getKey();
                Log.d("dhaval",noteid);
                databaseReference.child(noteid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String title=snapshot.child("title").getValue().toString();
                        String timestamp=snapshot.child("timestamp").getValue().toString();
                        Log.d("dhaval",title);
                        holder.setNoteTitle(title);
                        holder.setNoteTime(timestamp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };
        recview.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
}