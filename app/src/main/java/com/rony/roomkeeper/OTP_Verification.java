package com.rony.roomkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {

    EditText input1,input2,input3,input4,input5,input6;
    TextView textView;
    TextView resend;
    String getotpbackend;
    FirebaseAuth auth;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifiaction);


        auth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);
        final Button otpveributton= findViewById(R.id.otpgetbutton);
        final ProgressBar progressBar = findViewById(R.id.otpcheek);

        textView = findViewById(R.id.textsendopt);
        textView.setText(String.format("+880-%s",getIntent().getStringExtra("mobile")));
        getotpbackend = getIntent().getStringExtra("backendotp");

        otpveributton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input1.getText().toString().trim().isEmpty() && !input2.getText().toString().trim().isEmpty() && !input3.getText().toString().trim().isEmpty() && !input4.getText().toString().trim().isEmpty() && !input5.getText().toString().trim().isEmpty() && !input6.getText().toString().trim().isEmpty()){

                    String enterotp = input1.getText().toString()+
                            input2.getText().toString()+
                            input3.getText().toString()+
                            input4.getText().toString()+
                            input5.getText().toString()+
                            input6.getText().toString();

                    if(getotpbackend!=null){
                        progressBar.setVisibility(View.VISIBLE);
                        otpveributton.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend,enterotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        otpveributton.setVisibility(View.VISIBLE);

                                        if(task.isSuccessful()){
                                            String email = RegistrationActivity.email;
                                            String password = RegistrationActivity.password;
                                            String name = RegistrationActivity.name;
                                            String num = "+880"+OTP_number.num;


                                            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(OTP_Verification.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(task.isSuccessful()){
                                                        int flag=PreRegistrationActivity.flag;

                                                        UserModel userModel = new UserModel(name,email,password,num);
                                                        String id = task.getResult().getUser().getUid();
                                                        if(flag==1){
                                                            database.getReference().child("Users").child(id).setValue(userModel);
                                                        }
                                                        else if(flag==0){
                                                            database.getReference().child("Workers").child(id).setValue(userModel);
                                                        }

                                                        Toast.makeText(OTP_Verification.this,"Successfully Register",Toast.LENGTH_SHORT).show();
                                                        Intent myintent = new Intent(OTP_Verification.this,MainActivity.class);
                                                        startActivity(myintent);
                                                        finish();
                                                    }
                                                    else{
                                                        Toast.makeText(OTP_Verification.this,"Registration Failed"+task.getException(),Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });


                                        }
                                        else{
                                            Toast.makeText(OTP_Verification.this, "Enter the correct otp", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(OTP_Verification.this, "Please cheek internet connection", Toast.LENGTH_SHORT).show();
                    }

                    //  Toast.makeText(Otp_Verification.this, "Otp verify", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(OTP_Verification.this, "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();

        resend = findViewById(R.id.textsendopt);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+880" + getIntent().getStringExtra("mobile")
                        , 60, TimeUnit.SECONDS, OTP_Verification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OTP_Verification.this, "Error!Please cheek internet connection", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                getotpbackend = backendotp;
                                Toast.makeText(OTP_Verification.this, "otp sended successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );


            }
        });




    }

    private void numberotpmove() {

        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}