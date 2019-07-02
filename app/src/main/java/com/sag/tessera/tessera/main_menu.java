package com.sag.tessera.tessera;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class main_menu extends AppCompatActivity {


    private Button  goToSettings ;
    private Button goToLevel_1 ;
    private Button goToProfilePage ;
    private Button goToScorePage ;

    private FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent invalidAccessToCore = new Intent(getApplicationContext(), register_login.class);
            startActivity(invalidAccessToCore);
            finish();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }

        //Get an Firebase Instance to mAuth
        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_main_menu);


        goToSettings = (Button)findViewById(R.id.setBut);

        goToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewSettingsIntent = new Intent(main_menu.this,settings_page.class);
                startActivity(viewSettingsIntent);
                finish();
            }
        });




        goToProfilePage = (Button)findViewById(R.id.profileBut);

        goToProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewProfileIntent = new Intent(main_menu.this,user_profile.class);
                startActivity(viewProfileIntent);
                finish();
            }
        });


        goToLevel_1 = (Button)findViewById(R.id.playBut);

        goToLevel_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewLevel_1_Intent = new Intent(main_menu.this,level1_splash.class);
                startActivity(viewLevel_1_Intent);
                finish();
            }
        });


//        goToScorePage = (Button)findViewById(R.id.obBut);
//
//        goToScorePage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent viewScoreIntent = new Intent(main_menu.this,view_scores.class);
//                startActivity(viewScoreIntent);
//                finish();
//            }
//        });


        goToScorePage = (Button)findViewById(R.id.score_page);

        goToScorePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scorePageIntent = new Intent(getApplicationContext(),view_scores.class);
                startActivity(scorePageIntent);
                finish();
            }
        });





    }
}
