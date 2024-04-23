package com.rony.roomkeeper;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class LauncherActivity extends AppCompatActivity {

    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Thread td = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                finally {
                    Intent myintent = new Intent(LauncherActivity.this, PreRegistrationActivity.class);
                    startActivity(myintent);
                    finish();
                }
            }
        };td.start();

//        animationView = findViewById(R.id.animationView);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//               Intent myintent = new Intent(LauncherActivity.this, RegistrationActivity.class);
//               startActivity(myintent);
//              // finish();
//            }
//        },4000);
    }
}