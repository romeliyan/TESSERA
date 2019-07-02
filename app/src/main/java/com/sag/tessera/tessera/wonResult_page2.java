package com.sag.tessera.tessera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class wonResult_page2 extends AppCompatActivity {


    private Button nextLevelButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_result_page2);



        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highscoreLabel = (TextView) findViewById(R.id.highScoreLabel);

        nextLevelButton = (Button)findViewById(R.id.nextLevelBut);


        int score = getIntent().getIntExtra("SCORE" , 0);
        scoreLabel.setText(score + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA" , Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);

        if (score > highScore) {
            highscoreLabel.setText("High Score : " + score);

            //save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE" , score);
            editor.commit();

        } else {
            highscoreLabel.setText("High Score :" + highScore);
        }


        //   try1Button.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //   public void onClick(View view) {
        //     Intent try1Intent = new Intent(result.this,level1.class);
        //   startActivity(try1Intent);
        // finish();
        // }
        // });


    }

    public void nextLevel(View view) {
        startActivity(new Intent(getApplicationContext(),level3_splash.class)) ;
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
