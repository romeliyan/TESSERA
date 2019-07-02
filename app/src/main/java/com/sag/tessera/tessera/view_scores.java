package com.sag.tessera.tessera;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_scores extends AppCompatActivity {


    private Button cancel1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);


        cancel1Button = (Button)findViewById(R.id.cancelBut);


        cancel1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewSettingsIntent = new Intent(view_scores.this,main_menu.class);
                startActivity(viewSettingsIntent);
                finish();
            }
        });



        //Get Database Reference
        DBHandler db_handler = new DBHandler(view_scores.this);


        ListView listView = (ListView)findViewById(R.id.score_list);

        ArrayList<String> scoreArray = new ArrayList<>();
        Cursor data = db_handler.getListContents();

        if(data.getCount()==0){
            Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();
        }else{
            while (data.moveToNext()){
                scoreArray.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,scoreArray);
                listView.setAdapter(listAdapter);
            }
        }

    }
}
