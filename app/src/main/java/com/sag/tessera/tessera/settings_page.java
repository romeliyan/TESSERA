package com.sag.tessera.tessera;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class settings_page extends AppCompatActivity {

    private RadioGroup sounds;
    public RadioButton soundOn;
    public RadioButton soundOff;
    protected SoundPlayer sound;
    private Button backButton;
    private Button apply_button;
    private Button signOut;
    boolean soundState;
    RadioButton myRadioBtn;
    public SharedPreferences pref;
    public boolean isSoundState() {
        return soundState;
    }

    public void setSoundState(boolean soundState) {
        this.soundState = soundState;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }



        setContentView(R.layout.activity_settings_page);


        final SharedPreferences preferences = getSharedPreferences("SOUND_STATE", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        soundState = preferences.getBoolean("isChecked", false);
        sound = new SoundPlayer(this);

        // setSoundState(true);
        sounds = (RadioGroup)findViewById(R.id.soundSettings);
        soundOn = (RadioButton)findViewById(R.id.soundOn);
        soundOff = (RadioButton)findViewById(R.id.soundOff);

        apply_button = (Button)findViewById(R.id.applyButton);

        // checkSoundStateAtOnCreate();

        if(isSoundState()==true){
            soundOn.setChecked(true);
            soundOff.setChecked(false);



        }else {
            soundOff.setChecked(true);
            soundOn.setChecked(false);
            setSoundState(false);
        }

        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soundOn.isChecked()){

                    editor.putBoolean("isChecked", true);
                    editor.apply();
                    setSoundState(true);

                }
                else{
                    editor.putBoolean("isChecked", false);
                    editor.apply();
                    setSoundState(false);

                }
            }
        });

        backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMainIntent = new Intent(settings_page.this,main_menu.class);
                startActivity(viewMainIntent);
                finish();
            }
        });





    }
    public void soundsOn(View view){
        Toast.makeText(getApplicationContext(), "Sounds On", Toast.LENGTH_SHORT).show();
        setSoundState(true);

    }

    public void soundsOff(View view){
        Toast.makeText(getApplicationContext(), "Sound Off", Toast.LENGTH_SHORT).show();
        setSoundState(false);




    }
}
