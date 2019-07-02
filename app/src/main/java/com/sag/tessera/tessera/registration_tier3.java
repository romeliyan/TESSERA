package com.sag.tessera.tessera;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registration_tier3 extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mGamingName;
    private Button mSubmit;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fireDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_tier3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        //Get_FIREBASE_TOKEN
        mAuth = FirebaseAuth.getInstance();
        fireDB = FirebaseDatabase.getInstance();

        mFirstName =(EditText)findViewById(R.id.regi_firstName);
        mLastName = (EditText)findViewById(R.id.regi_lastName);
        mGamingName = (EditText)findViewById(R.id.regi_userName);
        mSubmit =(Button) findViewById(R.id.regi_finalizeSubmitBtn);



        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference refDB = fireDB.getReference().child("Users").child(mAuth.getCurrentUser().getUid());

                String firstNameTEXT = mFirstName.getText().toString();
                String lastNameTEXT = mLastName.getText().toString();
                String userNameTEXT =mGamingName.getText().toString();

                Map map = new HashMap();
                map.put("FirstName",firstNameTEXT);
                map.put("LastName",lastNameTEXT);
                map.put("UserName",userNameTEXT);

                refDB.setValue(map);

                try {
                    //Insert Information to Local Database
                    DBHandler dbHandler = new DBHandler(getApplicationContext());
                    boolean succuessInd = dbHandler.addInformation(firstNameTEXT, lastNameTEXT, userNameTEXT,mAuth.getCurrentUser().getUid());
                    Log.d("TESSERA_LOG", "" + succuessInd);
                    Intent reDirectToMainMenu = new Intent(getApplicationContext(),main_menu.class);
                    startActivity(reDirectToMainMenu);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }
}
