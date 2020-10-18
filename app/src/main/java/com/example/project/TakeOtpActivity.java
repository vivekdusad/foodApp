package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TakeOtpActivity extends AppCompatActivity implements View.OnClickListener {
    Button verifyButon;
    PinView pinView;
    String verificationId2;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_otp);
        pinView = findViewById(R.id.firstPinView);
        pinView.setAnimationEnable(true);
        verifyButon = findViewById(R.id.veriftyButton);
        verifyButon.setOnClickListener(this);
        verificationId2 = getIntent().getStringExtra("Verification_id");
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.veriftyButton){
            String otpText = pinView.getText().toString();
            if(!otpText.isEmpty() && otpText.length() == 6){
                Toast.makeText(this, otpText, Toast.LENGTH_SHORT).show();
                verifOtp(otpText);
            }
            else{
                pinView.setError("Invalid code");
            }
        }
    }

    private void verifOtp(String otpText) {
        verificationId2 = getIntent().getStringExtra("Verification");
        if (!verificationId2.isEmpty()) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId2, otpText);
            mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        final DocumentReference documentReference = mStore.collection("users").document(mAuth.getCurrentUser().getUid());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    startActivity(new Intent(TakeOtpActivity.this, MainActivity.class));
                                }
                                else{
                                    startActivity(new Intent(TakeOtpActivity.this, DetailsActivity.class));
                                }
                            }
                        });


//
                    } else {
                        Toast.makeText(TakeOtpActivity.this, "Not Verified", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}