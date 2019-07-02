package com.sag.tessera.tessera;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private Button mSubmit;
    private EditText mRegEmail;
    private EditText mRegPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        try {
            //Reference to Firebase User Object
            mAuth = FirebaseAuth.getInstance();



        setContentView(R.layout.activity_register);


            //Reference XML Objects

            mRegEmail = (EditText) findViewById(R.id.reg_email);
            mRegPassword = (EditText) findViewById(R.id.reg_pass);

            //mRePassword = (EditText) findViewById(R.id.register_retypepassword);
            mSubmit = (Button) findViewById(R.id.register_submit);


            //Register Button Pressed Listener
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //getting user inputed data as String primitive data type
                        final String tyEmail = mRegEmail.getText().toString();
                        final String tyPassword = mRegPassword.getText().toString();

                        User user_register = new User(tyEmail,tyPassword);

                        Log.d("SAG_ERROR", tyEmail + " | " + tyPassword);

                        registerUser(tyEmail, tyPassword);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("SAG_ERROR", e.getLocalizedMessage());
                    }
                }

            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void registerUser(String email,String password){


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    sendVerificationMail();


                }else{
                    Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("SAG_ERROR",task.getException().toString());
                }
            }
        });
    }




    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(register.this, main_menu.class));
        finish();

    }

    public void sendVerificationMail(){
        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(register.this, "Verfication Link jas been sent to "+mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                    Intent registerToRigisterTier02 = new Intent(register.this,registration_tier2.class);

                    startActivity(registerToRigisterTier02);
                }else if(!task.isSuccessful()){
                    Toast.makeText(register.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
