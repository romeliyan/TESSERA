package com.sag.tessera.tessera;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mSubmit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get firebase TOKEN
        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.login_email);
        mPassword = (EditText) findViewById(R.id.login_password);
        mSubmit = (Button) findViewById(R.id.login_submitBtn);

        //TODO: Validations Implemetation block

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTEXT = mEmail.getText().toString();
                String passwordTEXT = mPassword.getText().toString();

                signInUser(emailTEXT,passwordTEXT);
            }
        });


    }

    public void signInUser(String email,String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    Intent reDirectToMainMenu1 = new Intent(getApplicationContext(),main_menu.class);
                    startActivity(reDirectToMainMenu1);
                    finish();

                }else{
                    Toast.makeText(login.this, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
