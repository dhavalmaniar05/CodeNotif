package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {
    private TextInputLayout fn;
    private TextInputLayout cc;
    private TextInputLayout cf;
    private TextInputLayout lc;

    private ImageView imageView;

    private EditText fullName;
    private EditText codeChef;
    private EditText codeForces;
    private EditText leetCode;

    private FirebaseAuth mauth;
    private FirebaseUser currentUser;
    private DatabaseReference referenceProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fn = findViewById(R.id.outlinedTextFieldFullName_edit);
        cc = findViewById(R.id.outlinedTextFieldCodeChef_edit);
        cf = findViewById(R.id.outlinedTextFieldCodeForces_edit);
        lc = findViewById(R.id.outlinedTextFieldLeetCode_edit);

        fullName = fn.getEditText();
        codeChef = cc.getEditText();
        codeForces = cf.getEditText();
        leetCode = lc.getEditText();

        imageView = findViewById(R.id.imageView_profile_dp_edit);

        mauth = FirebaseAuth.getInstance();
        currentUser = mauth.getCurrentUser();

        Button upload = findViewById(R.id.buttonUpload_edit);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,UploadProfilePicActivity.class));
            }
        });

        Uri uri = currentUser.getPhotoUrl();

        Picasso.get().load(uri).into(imageView);
        Button register = findViewById(R.id.buttonRegister_edit);
        register.setOnClickListener(new View.OnClickListener() {
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
                    userDetails user = new userDetails(name,codechef,codeforces,leetcode);

                    Log.d("Dhaval",currentUser.getUid());
                    referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceProfile.child(currentUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserProfileActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserProfileActivity.this,HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(UserProfileActivity.this, "The User could not be made", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}