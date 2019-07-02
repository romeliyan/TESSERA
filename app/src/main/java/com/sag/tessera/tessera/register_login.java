package com.sag.tessera.tessera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class register_login extends AppCompatActivity {

    private Button mLogin;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);


        mLogin = (Button)findViewById(R.id.loginBtn);
        mRegister = (Button)findViewById(R.id.registerBtn);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent directToRegister = new Intent(getApplicationContext(),register.class);
                startActivity(directToRegister);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent directToLogin = new Intent(getApplicationContext(),login.class);
                startActivity(directToLogin);
            }
        });


    }
}
