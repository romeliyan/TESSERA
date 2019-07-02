package com.sag.tessera.tessera;


import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import java.util.Timer;
import java.util.TimerTask;


public class level1 extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;


    //Size
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    //Position
    private int boxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;

    //Speed
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;

    private int score = 0;
    //score


    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    //status check
    private boolean action_flg = false;
    private boolean start_flg = false;

    settings_page sp = new settings_page();

    public SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }






        setContentView(R.layout.activity_level1);



        sound = new SoundPlayer(this);

        final SharedPreferences preferences = getSharedPreferences("SOUND_STATE",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        sp.soundState = preferences.getBoolean("isChecked", false);


        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        box = (ImageView) findViewById(R.id.box);
        orange = (ImageView) findViewById(R.id.orange);
        pink = (ImageView) findViewById(R.id.pink);
        black = (ImageView) findViewById(R.id.black);

        //Get the screen size
        WindowManager wn = getWindowManager();
        Display disp  = wn.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //Now
        //Nexus4 width:768 height:1184
        //Speed box:20 Orange:12 Pink:20 Black:16

        boxSpeed = Math.round(screenHeight / 60F);     //1184 / 60 = 19.7333.. approximately 20
        orangeSpeed = Math.round(screenWidth / 60F);   //768 / 60 = 12.8 approximately 13
        pinkSpeed = Math.round(screenWidth / 50F);    //768 / 36 = 21.333..  approximately 21
        blackSpeed = Math.round(screenWidth / 45F);   //768 / 60 = 17.06 approximately 17

        //  Log.v("SPEED_BOX", boxSpeed+ "");
        //  Log.v("SPEED_ORANGE", orangeSpeed+ "");
        //  Log.v("SPEED_PINK", pinkSpeed+ "");
        //  Log.v("SPEED_BLACK", blackSpeed+ "");

        // Move to out of screen.
        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);

        scoreLabel.setText("Score : 0");
    }





    public void changePos() {

        hitCheck();


        //orange
        orangeX -= orangeSpeed;
        if(orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //Black
        blackX -= blackSpeed;

        if(blackX<0){
            blackX = screenWidth+10;
            blackY = (int ) Math.floor(Math.random() * (frameHeight - black.getHeight()));

        }
        black.setX(blackX);
        black.setY(blackY);

        //Pink
        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (int) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        //Move Box
        if (action_flg == true){
            //Touching
            boxY -= boxSpeed;
        }else{
            //Releasing
            boxY += boxSpeed;
        }
        //Check box positions
        if(boxY <0) boxY = 0;
        if(boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;
        box.setY(boxY);

        scoreLabel.setText("Score " +score );
    }

    public void hitCheck() {
        // if the center of the ball  is in the box,it counts as a hit.

        //orange
        int orangeCenterX = orangeX + orange.getWidth() /2 ;
        int orangeCenterY = orangeY + orange.getHeight() / 2;

        // 0 <= orangeCenterX <= boxWidth
        // boxY <= orangeCenterY <= boxY + boxHeight

        if( 0<= orangeCenterX && orangeCenterX <= boxSize && boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {

            score += 10;
            orangeX = -10;

            if(sp.soundState == true)

            sound.playHitSound();
        }

        //pink
        int pinkCenterX = pinkX + pink.getWidth() /2 ;
        int pinkCenterY = pinkY + pink.getHeight() / 2;

        // 0 <= pinkCenterX <= boxWidth
        // boxY <= pinkCenterY <= boxY + boxHeight

        if( 0<= pinkCenterX && pinkCenterX <= boxSize && boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {

            score += 30;
            pinkX = -10;
            if(sp.soundState == true)
            sound.playHitSound();
        }

        //Black
        int blackCenterX = blackX + black.getWidth() /2 ;
        int blackCenterY = blackY + black.getHeight() / 2;

        // 0 <= blackCenterX <= boxWidth
        // boxY <= blackCenterY <= boxY + boxHeight

        if( 0<= blackCenterX && blackCenterX <= boxSize && boxY <= blackCenterY && blackCenterY <= boxY + boxSize && score > 50) {

            //add score to database
            DBHandler scoreDB = new DBHandler(getApplicationContext());
            scoreDB.addScore("",""+score);

            //stop timer
            timer.cancel();

            if(sp.soundState == true)
            sound.playOverSound();

            //show result
            Intent intent = new Intent(getApplicationContext(), wonResult_page.class);
            intent.putExtra("SCORE" , score);
            startActivity(intent);

        }


        else if(0<= blackCenterX && blackCenterX <= boxSize && boxY <= blackCenterY && blackCenterY <= boxY + boxSize ){

            //add score to database
            DBHandler scoreDB = new DBHandler(getApplicationContext());
            scoreDB.addScore("",""+score);


            //stop timer
            timer.cancel();

            if(sp.soundState == true)
            sound.playOverSound();
            

            //show won result page
            Intent intent2 = new Intent(getApplicationContext(), result.class);
            intent2.putExtra("SCORE" , score);
            startActivity(intent2);

        }

    }


    public boolean onTouchEvent(MotionEvent me){


        if(start_flg == false){


            start_flg = true;

            //Why get frame height and box height here?
            //Because the UI has been set on the screen in onCreate()!!

            FrameLayout frame_= (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame_.getHeight();

            boxY = (int)box.getY();

            //The box is a square
            boxSize = box.getHeight();
            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run(){
                            changePos();
                        }
                    });
                }
            },0,20);




        }else{
            if(me.getAction() == MotionEvent.ACTION_DOWN){

                action_flg = true;


            }else if (me.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }
        return true;
    }

    // disable back button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK :
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);



    }
}
