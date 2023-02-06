package com.osamo.expensetrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private TextView signupRedirect;
    private Button loginButton;
    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        loginEmail = findViewById(R.id.login_email);
        loginButton = findViewById(R.id.login_btn);
        signupRedirect = findViewById(R.id.signup_redirect);
        loginPassword = findViewById(R.id.login_password);

       if(mAuth.getCurrentUser() != null){

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = loginEmail.getText().toString().trim();
                String pass = loginPassword.getText().toString().trim();

                mDialog.setMessage("Logging in, Please wait...");
                mDialog.show();


               /* if (TextUtils.isEmpty(email)) {
                    loginEmail.setError("Email is Required");
                }

                if (TextUtils.isEmpty(pass)) {
                    loginPassword.setError("Password is Required");
                }*/
                mDialog.setMessage("Signing up, Please wait...");


               if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(!pass.isEmpty()){

                mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this,"The credentials aren't familiar to us!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    } else {
                        loginPassword.setError("Password is Required");
                        mDialog.dismiss();
                    }
                } else if(email.isEmpty()){
                    loginEmail.setError("Email is Required");
                   mDialog.dismiss();
                }
                else {
                    loginEmail.setError("Please enter valid email");
                   mDialog.dismiss();
                }
            }
        });

        signupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
}