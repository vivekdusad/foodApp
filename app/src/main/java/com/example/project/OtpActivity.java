package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
    CountryCodePicker ccp;
    EditText phoneEditText;
    FirebaseAuth mAuth;
    EditText getPhoneEditText;
    Button sendButton;
    TextView resendTextView;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    String otpText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        phoneEditText = findViewById(R.id.editTextPhone);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        mAuth = FirebaseAuth.getInstance();
        getPhoneEditText = findViewById(R.id.editTextPhone);
        sendButton = findViewById(R.id.sendButton);
        resendTextView = findViewById(R.id.resendTextView);
        resendTextView.setOnClickListener(this);
        sendButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.sendButton){
            String phone = getPhoneEditText.getText().toString();
            String country_code = ccp.getSelectedCountryCode();
            String full_phone = "+" + country_code+phone;
            if(!phone.isEmpty() && phone.length() == 10){
                requestOtp(full_phone);
            }
            else{
                getPhoneEditText.setError("Invalid Phone NO.");
            }
        }
    }

    private void requestOtp(String phone_num) {
        Toast.makeText(this, phone_num, Toast.LENGTH_SHORT).show();


        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_num, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken ) {
                super.onCodeSent(s, forceResendingToken);
                Intent takeOTP1 = new Intent(OtpActivity.this,TakeOtpActivity.class);
                takeOTP1.putExtra("Verification", verificationId);
                takeOTP1.putExtra("token",token);
                startActivity(takeOTP1);
                verificationId = s;
                token = forceResendingToken;
//                otpText = getIntent().getStringExtra("otp_code");
//                verifOtp(otpText);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

//                signInWithPhoneAuthCredential(phoneAuthCredential);

                
                
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("Failed", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // ...
                }

            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(OtpActivity.this, "verified", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}