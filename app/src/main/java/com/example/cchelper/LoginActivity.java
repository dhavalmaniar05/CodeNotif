package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout em;
    private TextInputLayout p;
    private static final String TAG ="loginactivity";

    private EditText email;
    private EditText password;

    private FirebaseAuth mauth;
    private DatabaseReference referenceProfile;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        em = findViewById(R.id.outlinedTextFieldEmail_Login);
        p = findViewById(R.id.outlinedTextFieldPassword_Login);

        email = em.getEditText();
        password = p.getEditText();

        mauth = FirebaseAuth.getInstance();

        Button buttonLogin = findViewById(R.id.SignIn_Login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    email.setError("Email is Required");
                    email.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    email.setError("Enter a valid email address");
                    email.requestFocus();
                }else if(TextUtils.isEmpty(textPassword)) {
                    password.setError("Password is Required");
                    password.requestFocus();
                }else{
                    loginUser(textEmail,textPassword);
                }
            }
        });
    }

    private void loginUser(String textEmail, String textPassword) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging you in..");

        progressDialog.show();

        mauth.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    FirebaseUser currentUser = mauth.getCurrentUser();

                    if(currentUser.isEmailVerified()){
                        referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                        String Uid = currentUser.getUid();
                        referenceProfile.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getValue()==null){
                                    Toast.makeText(LoginActivity.this, "You are now Logged In", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,UserProfileActivity.class));
                                    finish();
                                }else{
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        currentUser.sendEmailVerification();
                        mauth.signOut();
                        showAlertDialog();
                    }
                }else{
                    progressDialog.dismiss();
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthInvalidUserException e) {
                        email.setError("User does not exist or no longer valid. Please register again");
                        email.requestFocus();
                    }catch(FirebaseAuthInvalidCredentialsException e) {
                        password.setError("Invalid Credentials entered");
                        password.requestFocus();
                    }catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email before logging in");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}