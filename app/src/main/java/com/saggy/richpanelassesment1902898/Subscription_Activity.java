package com.saggy.richpanelassesment1902898;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Subscription_Activity extends AppCompatActivity {
    ImageButton a1,a2,a3,a4;
    AppCompatButton monthlyB, yearlyB;
    AppCompatButton mobilePack, basicPack, standardPack, premiumPack;
    TextView monthlyPT, mobilePrice, basicPrice, standardPrice, premiumPrice;
    TextView mvq, bvq, svq, pvq;
    TextView mr, br, sr, pr;
    TextView mp, bp, sp, pp;
    TextView mt, bt, st, pt;
    TextView mac, bac, sac, pac;
    TextView bc, sc, pc;
    TextView btv, stv, ptv;
    AppCompatButton next;

    int mode = 0;
    int plan = 0;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        a1 = findViewById(R.id.arrow1);
        a2 = findViewById(R.id.arrow2);
        a3 = findViewById(R.id.arrow3);
        a4 = findViewById(R.id.arrow4);

        monthlyB = findViewById(R.id.monthlyButton);
        yearlyB = findViewById(R.id.yearlyButton);

        mobilePack = findViewById(R.id.mobilePack);
        basicPack = findViewById(R.id.basicPack);
        standardPack = findViewById(R.id.standardPack);
        premiumPack = findViewById(R.id.premiumPack);

        mobilePrice = findViewById(R.id.mobilePrice);
        basicPrice = findViewById(R.id.basicPrice);
        standardPrice = findViewById(R.id.standardPrice);
        premiumPrice = findViewById(R.id.premiumPrice);
        monthlyPT = findViewById(R.id.monthlyPriceText);

        mvq = findViewById(R.id.mvq);
        bvq = findViewById(R.id.bvq);
        svq = findViewById(R.id.svq);
        pvq = findViewById(R.id.pvq);
        mr = findViewById(R.id.mr);
        br = findViewById(R.id.br);
        sr = findViewById(R.id.sr);
        pr = findViewById(R.id.pr);
        mp = findViewById(R.id.mp);
        bp = findViewById(R.id.bp);
        sp = findViewById(R.id.sp);
        pp = findViewById(R.id.pp);
        mt = findViewById(R.id.mt);
        bt = findViewById(R.id.bt);
        st = findViewById(R.id.st);
        pt = findViewById(R.id.pt);
        bc = findViewById(R.id.bc);
        sc = findViewById(R.id.sc);
        pc = findViewById(R.id.pc);
        btv = findViewById(R.id.btv);
        stv = findViewById(R.id.stv);
        ptv = findViewById(R.id.ptv);
        next = findViewById(R.id.next);
        mac = findViewById(R.id.mac);
        bac = findViewById(R.id.bac);
        sac = findViewById(R.id.sac);
        pac = findViewById(R.id.pac);

        int lightGrey = getResources().getColor(R.color.lightGrey);
        int blue = getResources().getColor(R.color.blue);
        int lightBlue = getResources().getColor(R.color.lightBlue);
        String rs = getResources().getString(R.string.Rs);

        mobilePrice.setText(rs + " 100");
        basicPrice.setText(rs + " 200");
        standardPrice.setText(rs + " 500");
        premiumPrice.setText(rs + " 700");

        next.setOnClickListener(view -> {
            if(mode == 0){
                //monthly
                if(plan == 0){
                    //mobile
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Mobile");
                    intent.putExtra("Cycle", 0);
                    intent.putExtra("Amount", 100);
                    intent.putExtra("planDesc", "Phone+Tablet");
                    startActivity(intent);
                }
                else if(plan == 1){
                    //basic
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Basic");
                    intent.putExtra("Cycle", 0);
                    intent.putExtra("planDesc", "Phone+Tablet+TV");
                    intent.putExtra("Amount", 200);
                    startActivity(intent);
                }
                else if(plan  == 2){
                    //standard
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Standard");
                    intent.putExtra("Cycle", 0);
                    intent.putExtra("planDesc", "Phone+Tablet+TV");
                    intent.putExtra("Amount", 500);
                    startActivity(intent);
                }
                else{
                    //premium
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Premium");
                    intent.putExtra("Cycle", 0);
                    intent.putExtra("planDesc", "Phone+Tablet+TV");
                    intent.putExtra("Amount", 700);
                    startActivity(intent);
                }
            }
            else{
                //yearly
                if(plan == 0){
                    //mobile
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Mobile");
                    intent.putExtra("Cycle", 1);
                    intent.putExtra("planDesc", "Phone+Tablet");
                    intent.putExtra("Amount", 1000);
                    startActivity(intent);
                }
                else if(plan == 1){
                    //basic
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Basic");
                    intent.putExtra("Cycle", 1);
                    intent.putExtra("planDesc", "Phone+Tablet+TV");
                    intent.putExtra("Amount", 2000);
                    startActivity(intent);
                }
                else if(plan  == 2){
                    //standard
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Standard");
                    intent.putExtra("Cycle", 1);
                    intent.putExtra("planDesc", "Phone+Tablet+TV");
                    intent.putExtra("Amount", 5000);
                    startActivity(intent);
                }
                else{
                    //premium
                    Intent intent = new Intent(Subscription_Activity.this, Payment_Activity.class);
                    intent.putExtra("Plan", "Premium");
                    intent.putExtra("Cycle", 1);
                    intent.putExtra("planDesc", "Phone+Tablet+TV");
                    intent.putExtra("Amount", 7000);
                    startActivity(intent);
                }
            }
        });

        monthlyB.setOnClickListener(view -> {
            monthlyB.setTextColor(blue);
            yearlyB.setTextColor(getResources().getColor(R.color.white));
            monthlyPT.setText(getResources().getString(R.string.monthlyPrice));
            monthlyB.setBackground(getResources().getDrawable(R.drawable.white_round_20));
            yearlyB.setBackgroundColor(getResources().getColor(R.color.transparent));
            mobilePrice.setText(rs + " 100");
            basicPrice.setText(rs + " 200");
            standardPrice.setText(rs + " 500");
            premiumPrice.setText(rs + " 700");
            mode = 0;
        });

        yearlyB.setOnClickListener(view -> {
            yearlyB.setTextColor(blue);
            monthlyB.setTextColor(getResources().getColor(R.color.white));
            monthlyPT.setText(getResources().getString(R.string.yearlyPrice));
            yearlyB.setBackground(getResources().getDrawable(R.drawable.white_round_20));
            monthlyB.setBackgroundColor(getResources().getColor(R.color.transparent));
            mobilePrice.setText(rs + " 1000");
            basicPrice.setText(rs + " 2000");
            standardPrice.setText(rs + " 5000");
            premiumPrice.setText(rs + " 7000");
            mode =1;
        });

        premiumPack.setOnClickListener(view -> {
            a4.setVisibility(View.VISIBLE);
            a4.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
            a2.setVisibility(View.INVISIBLE);
            a3.setVisibility(View.INVISIBLE);
            a1.setVisibility(View.INVISIBLE);

            premiumPrice.setTextColor(blue);
            pvq.setTextColor(blue);
            pr.setTextColor(blue);
            pp.setTextColor(blue);
            pt.setTextColor(blue);
            pac.setTextColor(blue);


            basicPrice.setTextColor(lightGrey);
            bvq.setTextColor(lightGrey);
            bc.setTextColor(lightGrey);
            bp.setTextColor(lightGrey);
            btv.setTextColor(lightGrey);
            bt.setTextColor(lightGrey);
            br.setTextColor(lightGrey);
            bac.setTextColor(lightGrey);

            standardPrice.setTextColor(lightGrey);
            svq.setTextColor(lightGrey);
            sc.setTextColor(lightGrey);
            sp.setTextColor(lightGrey);
            stv.setTextColor(lightGrey);
            st.setTextColor(lightGrey);
            sr.setTextColor(lightGrey);
            sac.setTextColor(lightGrey);

            mobilePrice.setTextColor(lightGrey);
            mvq.setTextColor(lightGrey);
            pc.setTextColor(blue);
            mp.setTextColor(lightGrey);
            ptv.setTextColor(blue);
            mt.setTextColor(lightGrey);
            mr.setTextColor(lightGrey);
            mac.setTextColor(lightGrey);

            mobilePack.setBackgroundColor(lightBlue);
            basicPack.setBackgroundColor(lightBlue);
            standardPack.setBackgroundColor(lightBlue);
            premiumPack.setBackgroundColor(blue);

            plan = 3;
        });

        standardPack.setOnClickListener(view -> {
            a3.setVisibility(View.VISIBLE);
            a3.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
            a2.setVisibility(View.INVISIBLE);
            a1.setVisibility(View.INVISIBLE);
            a4.setVisibility(View.INVISIBLE);

            standardPrice.setTextColor(blue);
            svq.setTextColor(blue);
            sr.setTextColor(blue);
            sp.setTextColor(blue);
            st.setTextColor(blue);
            sac.setTextColor(blue);

            basicPrice.setTextColor(lightGrey);
            bvq.setTextColor(lightGrey);
            bc.setTextColor(lightGrey);
            bp.setTextColor(lightGrey);
            btv.setTextColor(lightGrey);
            bt.setTextColor(lightGrey);
            br.setTextColor(lightGrey);
            bac.setTextColor(lightGrey);

            mobilePrice.setTextColor(lightGrey);
            mvq.setTextColor(lightGrey);
            sc.setTextColor(blue);
            mp.setTextColor(lightGrey);
            stv.setTextColor(blue);
            mt.setTextColor(lightGrey);
            mr.setTextColor(lightGrey);
            mac.setTextColor(lightGrey);

            premiumPrice.setTextColor(lightGrey);
            pvq.setTextColor(lightGrey);
            pc.setTextColor(lightGrey);
            pp.setTextColor(lightGrey);
            ptv.setTextColor(lightGrey);
            pt.setTextColor(lightGrey);
            pr.setTextColor(lightGrey);
            pac.setTextColor(lightGrey);

            mobilePack.setBackgroundColor(lightBlue);
            basicPack.setBackgroundColor(lightBlue);
            standardPack.setBackgroundColor(blue);
            premiumPack.setBackgroundColor(lightBlue);

            plan = 2;
        });

        basicPack.setOnClickListener(view -> {
            a2.setVisibility(View.VISIBLE);
            a2.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
            a1.setVisibility(View.INVISIBLE);
            a3.setVisibility(View.INVISIBLE);
            a4.setVisibility(View.INVISIBLE);

            basicPrice.setTextColor(blue);
            bvq.setTextColor(blue);
            br.setTextColor(blue);
            bp.setTextColor(blue);
            bt.setTextColor(blue);
            bac.setTextColor(blue);

            mobilePrice.setTextColor(lightGrey);
            mvq.setTextColor(lightGrey);
            bc.setTextColor(blue);
            mp.setTextColor(lightGrey);
            btv.setTextColor(blue);
            mt.setTextColor(lightGrey);
            mr.setTextColor(lightGrey);
            mac.setTextColor(lightGrey);


            standardPrice.setTextColor(lightGrey);
            svq.setTextColor(lightGrey);
            sc.setTextColor(lightGrey);
            sp.setTextColor(lightGrey);
            stv.setTextColor(lightGrey);
            st.setTextColor(lightGrey);
            sr.setTextColor(lightGrey);
            sac.setTextColor(lightGrey);

            premiumPrice.setTextColor(lightGrey);
            pvq.setTextColor(lightGrey);
            pc.setTextColor(lightGrey);
            pp.setTextColor(lightGrey);
            ptv.setTextColor(lightGrey);
            pt.setTextColor(lightGrey);
            pr.setTextColor(lightGrey);
            pac.setTextColor(lightGrey);

            mobilePack.setBackgroundColor(lightBlue);
            basicPack.setBackgroundColor(blue);
            standardPack.setBackgroundColor(lightBlue);
            premiumPack.setBackgroundColor(lightBlue);

            plan = 1;
        });

        mobilePack.setOnClickListener(view -> {
            a1.setVisibility(View.VISIBLE);
            a1.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
            a2.setVisibility(View.INVISIBLE);
            a3.setVisibility(View.INVISIBLE);
            a4.setVisibility(View.INVISIBLE);

            mobilePrice.setTextColor(blue);
            mvq.setTextColor(blue);
            mr.setTextColor(blue);
            mp.setTextColor(blue);
            mt.setTextColor(blue);
            mac.setTextColor(blue);

            basicPrice.setTextColor(lightGrey);
            bvq.setTextColor(lightGrey);
            bc.setTextColor(lightGrey);
            bp.setTextColor(lightGrey);
            btv.setTextColor(lightGrey);
            bt.setTextColor(lightGrey);
            br.setTextColor(lightGrey);
            bac.setTextColor(lightGrey);

            standardPrice.setTextColor(lightGrey);
            svq.setTextColor(lightGrey);
            sc.setTextColor(lightGrey);
            sp.setTextColor(lightGrey);
            stv.setTextColor(lightGrey);
            st.setTextColor(lightGrey);
            sr.setTextColor(lightGrey);
            sac.setTextColor(lightGrey);

            premiumPrice.setTextColor(lightGrey);
            pvq.setTextColor(lightGrey);
            pc.setTextColor(lightGrey);
            pp.setTextColor(lightGrey);
            ptv.setTextColor(lightGrey);
            pt.setTextColor(lightGrey);
            pr.setTextColor(lightGrey);
            pac.setTextColor(lightGrey);

            mobilePack.setBackgroundColor(blue);
            basicPack.setBackgroundColor(lightBlue);
            standardPack.setBackgroundColor(lightBlue);
            premiumPack.setBackgroundColor(lightBlue);

            plan = 0;
        });
    }

}