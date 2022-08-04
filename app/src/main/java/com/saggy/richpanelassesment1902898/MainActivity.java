package com.saggy.richpanelassesment1902898;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView active, cancel, planName, planCycle, planDesc, planPrice, desc;
    AppCompatButton changePlan;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        active = findViewById(R.id.active);
        cancel = findViewById(R.id.cancel);
        planName = findViewById(R.id.planName);
        planDesc = findViewById(R.id.planDes);
        planCycle = findViewById(R.id.planCycle);
        planPrice = findViewById(R.id.planPrice);
        changePlan = findViewById(R.id.changePlan);
        desc = findViewById(R.id.desc);


        int plan_status = getIntent().getExtras().getInt("status");
        String plan = getIntent().getExtras().getString("planName");
        String planD = getIntent().getExtras().getString("planDesc");
        int amount = getIntent().getExtras().getInt("amount");
        int cycle = getIntent().getExtras().getInt("cycle");
        planName.setText(plan);
        planDesc.setText(planD);
        planPrice.setText(getResources().getString(R.string.Rs) + " "+ amount);

        if(cycle == 0)planCycle.setText("/mon");
        else planCycle.setText("/yr");

        if(plan_status == 0){
            //cancel
            cancel.setVisibility(View.GONE);
            active.setText("Cancelled");
            active.setTextColor(getResources().getColor(R.color.lightRed));
            active.setBackground(getResources().getDrawable(R.drawable.cancel_round));
            changePlan.setText("Choose Plan");
            desc.setText("Your subscription was cancelled and you will loose access to service on Aug 6th, 2022.");
        }
        else{
            //active
            cancel.setVisibility(View.VISIBLE);
            active.setText("Active");
            active.setTextColor(getResources().getColor(R.color.darkBlue));
            active.setBackground(getResources().getDrawable(R.drawable.active_round));
            changePlan.setText("Change Plan");
            desc.setText("Your subscription has started on Aug 4th, 2022 and will auto renew on Aug 5th, 2023");
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String uid = firebaseUser.getUid();


        changePlan.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Subscription_Activity.class);
            startActivity(intent);
        });

        cancel.setOnClickListener(view -> {
            FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("status").setValue(0);
            cancel.setVisibility(View.GONE);
            active.setText("Cancelled");
            active.setTextColor(getResources().getColor(R.color.lightRed));
            active.setBackground(getResources().getDrawable(R.drawable.cancel_round));
            changePlan.setText("Choose Plan");
            desc.setText("Your subscription was cancelled and you will loose access to service on Aug 6th, 2022.");
        });

    }
}

