package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        LottieAnimationView lottieAnimationView = findViewById(R.id.deliveryAnimations);
//        lottieAnimationView.setAnimationFromUrl("https://assets8.lottiefiles.com/private_files/lf30_ej73j8k8.json");
         new Handler().postDelayed(new Runnable(){
             @Override
             public void run() {
                 Intent intent = new Intent(SplashScreen.this,MainActivity2.class);
                 startActivity(intent);
                 finish();
             }
         },3500);
    }

}