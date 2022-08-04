package com.saggy.richpanelassesment1902898;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardMultilineWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Payment_Activity extends AppCompatActivity {
    TextView planCycle, planName, planAmount;
    CardMultilineWidget cardInputWidget;
    AppCompatButton payButton;
    PaymentMethodCreateParams params;
    FirebaseUser firebaseUser;


    private static final String TAG = "Payment_Activity";
    private static final String BACKEND_URL = "https://richpanel-stripe-project.herokuapp.com/";

    private String paymentIntentClientSecret;
    //declare stripe
    private Stripe stripe;

    Double amountDouble=null;
    int cycle,amount;
    String plan,planC, planDesc;

    private OkHttpClient httpClient;

    static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        plan = getIntent().getExtras().getString("Plan");
        cycle = getIntent().getExtras().getInt("Cycle");
        amount = getIntent().getExtras().getInt("Amount");
        planDesc = getIntent().getExtras().getString("planDesc");

        amountDouble = Double.valueOf(String.valueOf(amount));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        planCycle = findViewById(R.id.planCycle);
        planName = findViewById(R.id.planName);
        planAmount = findViewById(R.id.planPrice);
        cardInputWidget = findViewById(R.id.cardInput);
        payButton = findViewById(R.id.payButton);

        cardInputWidget.setCardNumber("4242424242424242");
        cardInputWidget.setCvcCode("424");
        cardInputWidget.setExpiryDate(4,24);
        cardInputWidget.setPostalCodeRequired(false);
        planName.setText(plan);
        if(cycle == 0){
            planC = "Monthly";
            planCycle.setText("Monthly");
            planAmount.setText(getResources().getString(R.string.Rs) + " "+ amount + "/month");
        }
        else{
            planC = "Yearly";
            planCycle.setText("Yearly");
            planAmount.setText(getResources().getString(R.string.Rs) + " "+ amount + "/year");
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Transaction in progress");
        progressDialog.setCancelable(false);
        httpClient = new OkHttpClient();

        //Initialize
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51LSlq4SJpNc22Ln5IodgCD0fMkiIBwEMX0lQqJyoy79SY6VZgmdBjiKGYstSjYTDjm1Bc71GI4e5PTTA0aGrDqeM00hzG74ICi")
        );

        payButton.setOnClickListener(view -> {
            progressDialog.show();
            params = cardInputWidget.getPaymentMethodCreateParams();
//            Credit Card: 4242 4242 4242 4242
//            Expiration: 04/24
//            CVV: 424
            startCheckout();
        });
    }

    private void showAlert(String title, @Nullable String message){
        runOnUiThread(()->{
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK",null)
                    .create();
            dialog.show();
        });
    }

    private void showToast(String message){
        runOnUiThread(()-> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }


    private void startCheckout() {
        {
            // Create a PaymentIntent by calling the server's endpoint.
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
            double amount=amountDouble*100;
            Map<String,Object> payMap=new HashMap<>();
            Map<String,Object> itemMap=new HashMap<>();
            List<Map<String,Object>> itemList =new ArrayList<>();
            payMap.put("currency","INR");
            itemMap.put("id","photo_subscription");
            itemMap.put("amount",amount);
            itemList.add(itemMap);
            payMap.put("items",itemList);
            String json = new Gson().toJson(payMap);
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(BACKEND_URL + "create-payment-intent")
                    .post(body)
                    .build();
            httpClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            progressDialog.dismiss();
                            showAlert("Failed to load data","Error: "+ e.toString());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(!response.isSuccessful()){
                                progressDialog.dismiss();
                                showAlert("Failed to load data","Error: "+ response.toString());
                            }
                            else{
                                Gson gson = new Gson();
                                Type type = new TypeToken<Map<String, String>>(){}.getType();
                                Map<String, String> responseMap = gson.fromJson(
                                        Objects.requireNonNull(response.body()).string(),
                                        type
                                );
                                paymentIntentClientSecret = responseMap.get("clientSecret");

                                //once you get the payment client secret start transaction
                                //get card detail
                                if (params != null) {
                                    //now use paymentIntentClientSecret to start transaction
                                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                                    //start payment
                                    stripe.confirmPayment(Payment_Activity.this, confirmParams);
                                }
                                Log.i("TAG", "onPaymentSuccess: "+paymentIntentClientSecret);
                                progressDialog.dismiss();
                            }
                        }
                    });
                    //.enqueue(new PayCallback(this));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new ApiResultCallback<PaymentIntentResult>() {
            @Override
            public void onSuccess(PaymentIntentResult paymentIntentResult) {
                progressDialog.dismiss();
                PaymentIntent paymentIntent = paymentIntentResult.getIntent();
                PaymentIntent.Status status = paymentIntent.getStatus();
                if (status == PaymentIntent.Status.Succeeded) {
                    // Payment completed successfully
                    //Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    //showAlert("success", paymentIntent.toString());
                    String uid = firebaseUser.getUid();
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("status").setValue(1);
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("paymentMethod").setValue(paymentIntent.getPaymentMethodTypes().toString());
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("paymentID").setValue(paymentIntent.getId());
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("created").setValue(paymentIntent.getCreated());
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("clientSecret").setValue(paymentIntent.getClientSecret());
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("amount").setValue(amount);
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("plan").setValue(plan);
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("planCycle").setValue(planC);
                    FirebaseDatabase.getInstance().getReference().child("Subscriber").child(uid).child("planDesc").setValue(planDesc);
                    showToast("Payment Success");
                    Intent intent = new Intent(Payment_Activity.this, MainActivity.class);
                    intent.putExtra("status", 1);
                    intent.putExtra("planName", plan);
                    intent.putExtra("planDesc", planDesc);
                    intent.putExtra("amount", amount);
                    intent.putExtra("cycle", cycle);
                    startActivity(intent);
                    finish();
                }
                else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                    showAlert(
                            "Payment failed",
                            Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                    );
                }
            }
            @Override
            public void onError(@NonNull Exception e) {
                progressDialog.dismiss();
                showAlert("Error", e.toString());
            }
        });
    }
}