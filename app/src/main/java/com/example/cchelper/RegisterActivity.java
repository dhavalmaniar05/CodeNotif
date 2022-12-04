package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout em;
    private TextInputLayout p;
    private TextInputLayout pc;

    private EditText email;
    private EditText pwd;
    private EditText pwdConfirm;

    private FirebaseAuth auth;

    private static final String TAG = "RegisterActivity";

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toast.makeText(this, "Enter Your Details and Click on Continue", Toast.LENGTH_LONG).show();

        em = findViewById(R.id.outlinedTextFieldEmail_Register);
        p = findViewById(R.id.outlinedTextFieldPassword_Register);
        pc = findViewById(R.id.outlinedTextFieldPasswordConfirm_Register);

        email = em.getEditText();
        pwd = p.getEditText();
        pwdConfirm = pc.getEditText();

        auth = FirebaseAuth.getInstance();

        Button register = findViewById(R.id.buttonContinue);

        Button login = findViewById(R.id.textButtonLogin_Register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = email.getText().toString();
                String password = pwd.getText().toString();
                String confirmPassword = pwdConfirm.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    email.setError("Email is Required");
                    email.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    email.setError("Enter a valid email address");
                    email.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    pwd.setError("Password is Required");
                    pwd.requestFocus();
                }else if(TextUtils.isEmpty(confirmPassword)){
                    pwdConfirm.setError("Password Confirmation is Required");
                    pwdConfirm.requestFocus();
                }else if(password.length()<6){
                    pwd.setError("Password should be minimum 6 characters long");
                    pwd.requestFocus();
                }else if(!password.equals(confirmPassword)){
                    pwdConfirm.setError("Passwords do not match");
                    pwdConfirm.requestFocus();

                    pwd.clearComposingText();
                    pwdConfirm.clearComposingText();
                }else{
                    registerUser(textEmail,password);
                }
            }
        });

    }

    private void registerUser(String textEmail, String password) {
        auth.createUserWithEmailAndPassword(textEmail,password).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                            sendVerificationEmail();
                        }else{
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidCredentialsException e){
                                pwdConfirm.setError("Invalid email or already in use. Kindly Re-enter");
                                pwdConfirm.requestFocus();
                            } catch(FirebaseAuthUserCollisionException e){
                                pwdConfirm.setError("User is already registered with this email. Try Logging in or use another email");
                            } catch(Exception e){
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                        else
                        {

                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }
}