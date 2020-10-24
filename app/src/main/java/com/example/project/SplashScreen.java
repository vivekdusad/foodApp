package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

         new Handler().postDelayed(new Runnable(){
             @Override
             public void run() {
                 if(user  == null) {
                     Intent intent = new Intent(SplashScreen.this, MainActivity2.class);
                     startActivity(intent);
                     finish();
                 }
                 else{
                     Intent intent1 = new Intent(SplashScreen.this, MainActivity.class);
                     startActivity(intent1);
                     finish();

                 }

             }
         },3500);
    }

}