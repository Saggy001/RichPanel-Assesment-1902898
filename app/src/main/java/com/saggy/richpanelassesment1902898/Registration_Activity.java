package com.saggy.richpanelassesment1902898;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Registration_Activity extends AppCompatActivity {
    EditText etUsername, etEmail, etPassword;
    FirebaseAuth firebaseAuth;
    int checker = 0;
    AppCompatButton signUpButton;
    TextView signIn;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUsername = findViewById(R.id.nameEdit);
        etEmail = findViewById(R.id.emailEdit);
        etPassword = findViewById(R.id.passEdit);
        signUpButton = findViewById(R.id.signup);
        signIn = findViewById(R.id.login);
        rememberMe = findViewById(R.id.checkbox);

        firebaseAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(v -> {
            startActivity(new Intent(Registration_Activity.this, LoginActivity.class));
            finish();
        });

        signUpButton.setOnClickListener(v -> {
            final String email = etEmail.getText().toString();
            final String pwd = etPassword.getText().toString();
            final String name = etUsername.getText().toString();

            if (name.isEmpty()) {
                etUsername.setError("please Enter your name");
                etUsername.requestFocus();
            }
            else if (name.length() < 3) {
                etUsername.setError("Username must contain at least 3 Character");
                etUsername.requestFocus();
            }
            else if (name.length() >15){
                etUsername.setError("Username is too long");
                etUsername.requestFocus();
            }
            else if (email.isEmpty()) {
                etEmail.setError("Please Enter Email Id");
                etEmail.requestFocus();
            }
            else if (pwd.isEmpty()) {
                etPassword.setError("Please Enter Your Password");
                etPassword.requestFocus();
            }
            else if (pwd.length() < 5) {
                etPassword.setError("Password must contain at least 6 Character");
                etPassword.requestFocus();
            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Registration_Activity.this, task -> {
                    if (!task.isSuccessful())
                        Toast.makeText(Registration_Activity.this, "Registration unsuccessful! please try again", Toast.LENGTH_SHORT).show();
                    else {
                        FirebaseUser mfirebaseuser = firebaseAuth.getCurrentUser();
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        assert mfirebaseuser != null;
                        mfirebaseuser.updateProfile(profileChangeRequest);
//                        String correctemailid = email.replace(".com","");
//                        FirebaseDatabase.getInstance().getReference().child("Users").child(name).child("Username").setValue(name);
//                        FirebaseDatabase.getInstance().getReference().child("Users").child(name).child("EmailId").setValue(correctemailid);
                        updateUI(mfirebaseuser);
                    }
                });
            }
        });
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Toast.makeText(Registration_Activity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Registration_Activity.this, LoginActivity.class));
            finish();
        }
    }
}




