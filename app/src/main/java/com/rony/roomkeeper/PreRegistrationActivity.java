package com.rony.roomkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class PreRegistrationActivity extends AppCompatActivity {
    Button btn,btn1;
    Animation animation;
    public static int flag=-1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_registration);
        btn = findViewById(R.id.get_started_btn);
        btn1 = findViewById(R.id.get_started_btn1);

        sharedPreferences = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("FirstTime",true);
        if(isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("FirstTime",false);
            editor.commit();

            Intent myintent = new Intent(PreRegistrationActivity.this, OnBoardingActivity.class);
            startActivity(myintent);
            finish();
        }

        animation = AnimationUtils.loadAnimation(PreRegistrationActivity.this,R.anim.slide_animation);
        btn.setAnimation(animation);
        btn.setVisibility(View.VISIBLE);
        btn1.setAnimation(animation);
        btn1.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=0;
                Intent myintent = new Intent(PreRegistrationActivity.this, RegistrationActivity.class);
                startActivity(myintent);
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                Intent myintent = new Intent(PreRegistrationActivity.this, RegistrationActivity.class);
                startActivity(myintent);
                finish();
            }
        });
    }
}