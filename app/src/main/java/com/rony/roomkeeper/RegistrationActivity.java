package com.rony.roomkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    Button buttonsignup;
    TextView textsignin;
    EditText edname,edemail,edpassword;
    FirebaseAuth mAuth;

    boolean passwordVisible;

    FirebaseDatabase database;
    public static String name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if(mAuth.getCurrentUser()!=null){
            Intent myintent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(myintent);
            finish();
        }

        buttonsignup = findViewById(R.id.buttonsignup);
        textsignin = findViewById(R.id.textsignin);
        edname = findViewById(R.id.edname);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);

        edpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final  int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>= edpassword.getRight()-edpassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edpassword.getSelectionEnd();
                        if(passwordVisible){

                            edpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);

                            edpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;

                        }else{
                            edpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24,0);

                            edpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;

                        }
                        edpassword.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });


        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 name = edname.getText().toString();
                 email = edemail.getText().toString();
                 password = edpassword.getText().toString();

                if(TextUtils.isEmpty(name)){
                    edname.setError("Enter your name");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    edemail.setError("Enter your name");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    edpassword.setError("Enter your name");
                    return;
                }
               if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                   Toast.makeText(RegistrationActivity.this,"Enter a valid email!",Toast.LENGTH_SHORT).show();
                   return;
               }
                if(password.length()<6){
                    Toast.makeText(RegistrationActivity.this,"Password too short, Enter minimum 6 characters!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent myintent = new Intent(RegistrationActivity.this, OTP_number.class);
                startActivity(myintent);
                finish();
               // finish();


            }
        });
        textsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(myintent);
                finish();
            }
        });
    }
}