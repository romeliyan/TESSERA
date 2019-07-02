package com.sag.tessera.tessera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registration_tier2 extends AppCompatActivity {

    private TextView emailSIDText;
    private FirebaseAuth mAuth;
    private TextView mVerficationboolean;
    private Button checkVerificationRefreshBtn;
    private Button continueBtn;
    private ImageView mVerifiedBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_tier2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }

        //Set firebase Reference
        mAuth = FirebaseAuth.getInstance();
        String userEmai = mAuth.getCurrentUser().getEmail();

        emailSIDText = (TextView)findViewById(R.id.emailSID);
        mVerficationboolean = (TextView)findViewById(R.id.verficationBoolean);
        checkVerificationRefreshBtn = (Button)findViewById(R.id.checkVerificationStatus);
        mVerifiedBadge = (ImageView)findViewById(R.id.verifiedBadge);

        continueBtn = (Button)findViewById(R.id.nextButton);
        //Hide when OnCreate Phase
        continueBtn.setVisibility(View.GONE);
        mVerifiedBadge.setVisibility(View.GONE);

        emailSIDText.setText(mAuth.getCurrentUser().getEmail());


        try{
            //Create an Exception Block
            checkVerificationRefreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Define VIBRATE
                        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(512);

                        refreshVerficationTextUI();
                    }catch (Exception e){

                        Log.d("SAG_ERROR",e.getLocalizedMessage());
                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
            Log.d("SAG_ERROR",e.getLocalizedMessage());
        }
    }

    public void refreshVerficationTextUI(){
        mAuth =FirebaseAuth.getInstance();
        mAuth.getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseUser user = mAuth.getCurrentUser();
                boolean isVerified = user.isEmailVerified();

                if(isVerified) {

                    mVerficationboolean.setTextColor(Color.parseColor("#00FF00"));
                    mVerficationboolean.setText("Verfied");
                    continueBtn.setVisibility(View.VISIBLE);
                    mVerifiedBadge.setVisibility(View.VISIBLE);
                    continueBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent sucessRegister = new Intent(getApplicationContext(),registration_tier3.class);
                            sucessRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            sucessRegister.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(sucessRegister);
                            finish();
                        }
                    });
                }else{
                    mVerficationboolean.setTextColor(Color.parseColor("#FF4000"));
                    mVerficationboolean.setText("Not Verified");


                }

            }
        });



    }
}
