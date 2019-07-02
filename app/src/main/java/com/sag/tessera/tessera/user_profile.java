package com.sag.tessera.tessera;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class user_profile extends AppCompatActivity {

    private TextView userProfileTitle;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mEmail;
    private TextView mGamingName;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFireDatabase;
    private Button msignOut;
    ProgressDialog mProgressDialog;

    private Button cancel1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        cancel1Button = (Button)findViewById(R.id.cancelBut);


        cancel1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMainIntent = new Intent(user_profile.this,main_menu.class);
                startActivity(viewMainIntent);
                finish();
            }
        });





        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Fetching Data Please Wait..");
        mProgressDialog.show();

        //Get access token
        mAuth = FirebaseAuth.getInstance();

        //get firebase database token
        mFireDatabase = FirebaseDatabase.getInstance();

        //Getbase REFERENCE
        DatabaseReference dbRef = mFireDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());

        //XML reference object link
        userProfileTitle =(TextView)findViewById(R.id.user_profileHead);
        mFirstName = (TextView)findViewById(R.id.usr_firstname);
        mLastName = (TextView)findViewById(R.id.usr_lastname);
        mEmail = (TextView)findViewById(R.id.usr_email);
        mGamingName=(TextView)findViewById(R.id.use_gamingname);
        msignOut=(Button)findViewById(R.id.signOut);

        //visibility off
        userProfileTitle.setVisibility(View.GONE);
        mFirstName.setVisibility(View.GONE);
        mLastName.setVisibility(View.GONE);
        mEmail.setVisibility(View.GONE);
        mGamingName.setVisibility(View.GONE);
        msignOut.setVisibility(View.GONE);


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String firstName = dataSnapshot.child("FirstName").getValue().toString();
                String lastName = dataSnapshot.child("LastName").getValue().toString();
                String gamingName = dataSnapshot.child("UserName").getValue().toString();



                mProgressDialog.dismiss();
                userProfileTitle.setText("Hello , "+firstName+" "+lastName+" !");
                mFirstName.setText("First Name\t : \t"+firstName);
                mLastName.setText("Last Name\t : \t "+lastName);
                mGamingName.setText("Gaming Name\t : \t"+gamingName);
                mEmail.setText("Email\t : "+mAuth.getCurrentUser().getEmail());
                userProfileTitle.setVisibility(View.VISIBLE);
                mFirstName.setVisibility(View.VISIBLE);
                mLastName.setVisibility(View.VISIBLE);
                mEmail.setVisibility(View.VISIBLE);
                mGamingName.setVisibility(View.VISIBLE);
                msignOut.setVisibility(View.VISIBLE);

                Log.d("TESSERA_BUG",firstName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        msignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent signoutUser = new Intent(getApplicationContext(),register_login.class);
                startActivity(signoutUser);
                finish();
            }
        });
    }
}
