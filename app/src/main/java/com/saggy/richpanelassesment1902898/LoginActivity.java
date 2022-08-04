package com.saggy.richpanelassesment1902898;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText etlEmail, etlPassword;
    TextView signUp;
    AppCompatButton loginButton;
    CheckBox checkBox;
    String email, password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login);
        etlEmail = findViewById(R.id.emailEdit);
        etlPassword = findViewById(R.id.passEdit);
        signUp = findViewById(R.id.signup);
        checkBox = findViewById(R.id.checkbox);

        signUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, Registration_Activity.class));
            finish();
        });

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(view -> {
            email = etlEmail.getText().toString();
            password = etlPassword.getText().toString();

            if (email.isEmpty()) {
                etlEmail.setError("Please Enter Email Id");
                etlEmail.requestFocus();
            }
            else if (password.isEmpty()) {
                etlPassword.setError("Please Enter Your Password");
                etlPassword.requestFocus();
            }
            else{
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                    if (!task.isSuccessful())
                        Toast.makeText(LoginActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                    else {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        updateUI(firebaseUser);
                    }
                });
            }
        });
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {

            progressDialog.show();

            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String uid = firebaseUser.getUid();
                    if(snapshot.child("Subscriber").child(uid).exists()){
                        String plan = snapshot.child("Subscriber").child(uid).child("plan").getValue(String.class);
                        String planD = snapshot.child("Subscriber").child(uid).child("planDesc").getValue(String.class);
                        String planCycle = snapshot.child("Subscriber").child(uid).child("planCycle").getValue(String.class);
                        int amount = snapshot.child("Subscriber").child(uid).child("amount").getValue(Integer.class);
                        int status = snapshot.child("Subscriber").child(uid).child("status").getValue(Integer.class);
                        int cycle = 1;
                        if(planCycle.charAt(0) == 'M')cycle = 0;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("status", status);
                        intent.putExtra("planName", plan);
                        intent.putExtra("planDesc", planD);
                        intent.putExtra("amount", amount);
                        intent.putExtra("cycle", cycle);
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intohome = new Intent(LoginActivity.this, Subscription_Activity.class);
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(intohome);
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        else
            Toast.makeText(this, "SignIn to continue", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
    }
}