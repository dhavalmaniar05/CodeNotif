package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfileShow extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView email,fullName,codeChef,codeForces,leetCode;
    private String em,fn,cc,cf,lc;
    private FirebaseAuth mauth;
    private ImageView img;

    private Toolbar toolbar;
    private NavigationView nav;
    private Button navBtn;
    private Button hideBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_show);
        email = findViewById(R.id.textView_Email);
        fullName = findViewById(R.id.textView_FullName);
        codeChef = findViewById(R.id.textView_CodeChef);
        codeForces = findViewById(R.id.textView_CodeForces);
        leetCode = findViewById(R.id.textView_LeetCode);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("User Details");
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);

        mauth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mauth.getCurrentUser();

        img = findViewById(R.id.imageView_profile);
        
        Button sout = findViewById(R.id.buttonSignOut);
        sout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                Toast.makeText(UserProfileShow.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserProfileShow.this,LoginActivity.class));
                finish();
            }
        });

        Button btn = findViewById(R.id.buttonRegister_edit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileShow.this, EditProfileActivity.class));
            }
        });

        if(currUser==null){
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserProfileShow.this,LoginActivity.class));
        }else{
            showUserProfile(currUser);
        }
    }

    private void showUserProfile(FirebaseUser currUser) {
        String uid = currUser.getUid();
        Uri uri1 = mauth.getCurrentUser().getPhotoUrl();
        if(uri1!=null){
            Picasso.get().load(uri1).into(img);
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDetails user = snapshot.getValue(userDetails.class);
                if(user!=null){
                    em=currUser.getEmail();
                    fn=user.getName();
                    cc=user.getCodechef();
                    cf=user.getCodeforces();
                    lc=user.getLeetcode();

                    email.setText(em);
                    fullName.setText(fn);
                    codeChef.setText(cc);
                    codeForces.setText(cf);
                    leetCode.setText(lc);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showNav(View view){

        navBtn = findViewById(R.id.navBtn);
        nav = findViewById(R.id.navigationView);

        nav.setVisibility(View.VISIBLE);
    }


    public void hideNav(View view){

        nav = findViewById(R.id.navigationView);
        hideBtn = (Button) nav.findViewById(R.id.hideNavBtn);

        nav.setVisibility(View.GONE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_dsa) {

            Intent dsa = new Intent(this, DsaActivity.class);
            startActivity(dsa);
        }
        else if(id==R.id.menu_home){

            Intent home = new Intent(this, HomeActivity.class);
            startActivity(home);
        }
        else if(id==R.id.menu_profile){
            Intent home = new Intent(this, UserProfileShow.class);
            startActivity(home);
        }else if(id==R.id.menu_codechef){

            Intent lc = new Intent(this, CodeChefPage.class);
            startActivity(lc);

        }else if(id==R.id.menu_codeforces){

            Intent lc = new Intent(this, CodeforcesPage.class);
            startActivity(lc);

        }
        else if(id==R.id.menu_leetcode){

            Intent lc = new Intent(this, LeetCode_page.class);
            startActivity(lc);

        }
        else if(id==R.id.menu_contest){

            Intent lc = new Intent(this, ContestsActivity.class);
            startActivity(lc);
    }
        return true;
    }
}