package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextInputLayout fn;
    private TextInputLayout cc;
    private TextInputLayout cf;
    private TextInputLayout lc;

    private ImageView imageView;

    private Toolbar toolbar;
    private NavigationView nav;
    private Button navBtn;
    private Button hideBtn;

    private EditText fullName;
    private EditText codeChef;
    private EditText codeForces;
    private EditText leetCode;

    private FirebaseAuth mauth;
    private FirebaseUser currentUser;
    private DatabaseReference referenceProfile;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fn = findViewById(R.id.outlinedTextFieldFullName);
        cc = findViewById(R.id.outlinedTextFieldCodeChef);
        cf = findViewById(R.id.outlinedTextFieldCodeForces);
        lc = findViewById(R.id.outlinedTextFieldLeetCode);

        fullName = fn.getEditText();
        codeChef = cc.getEditText();
        codeForces = cf.getEditText();
        leetCode = lc.getEditText();

        imageView = findViewById(R.id.imageView_profile_dp);

        mauth = FirebaseAuth.getInstance();
        currentUser = mauth.getCurrentUser();

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);

        showProfile(currentUser);

        Button upload = findViewById(R.id.buttonUpload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this,UploadProfilePicActivity.class));
                finish();
            }
        });

        Button update = findViewById(R.id.buttonUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fullName.getText().toString();
                String codechef = codeChef.getText().toString();
                String codeforces = codeForces.getText().toString();
                String leetcode = leetCode.getText().toString();

                if(TextUtils.isEmpty(name)){
                    fullName.setError("Full Name is required");
                    fullName.requestFocus();
                }else{
                    userDetails updatedUser = new userDetails(name,codechef,codeforces,leetcode);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered Users");

                    String uid = currentUser.getUid();

                    ref.child(uid).setValue(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EditProfileActivity.this, "Successfully updated details", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(EditProfileActivity.this,UserProfileShow.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                try {
                                    task.getException();
                                }catch (Exception e){
                                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }


    private void showProfile(FirebaseUser currentUser) {
        String uid = currentUser.getUid();

        Uri uri1 = mauth.getCurrentUser().getPhotoUrl();
        Log.d("DHAVAL",uri1.toString());
        Picasso.get().load(uri1).into(imageView);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");

        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDetails user = snapshot.getValue(userDetails.class);
                if(user!=null){
                    String name = user.getName();
                    String codechef = user.getCodechef();
                    String codeforces = user.getCodeforces();
                    String leetcode = user.getLeetcode();

                    fullName.setText(name);
                    codeChef.setText(codechef);
                    codeForces.setText(codeforces);
                    leetCode.setText(leetcode);
                }else{
                    Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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