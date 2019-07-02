package com.sag.tessera.tessera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Tessera_DB.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_create_entries = "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" + UsersMaster.Users._ID + "" +
                "INTEGER PRIMARY KEY," + UsersMaster.Users.COLUMN_NAME_FIRST_NAME + " TEXT ," + UsersMaster.Users.COLUMN_NAME_LAST_NAME + " " +
                "TEXT ," + UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT, "+ UsersMaster.Users.COLUMN_NAME_FIREBASE_UID+" TEXT)";

        db.execSQL(sql_create_entries);

        String sql_create_score = "CREATE TABLE "+UsersMaster.Users.TABLE_SCORE+"("+UsersMaster.Users.COLUMN_NAME_FIREBASE_UID+" TEXT , "+UsersMaster.Users.COLUMN_NAME_SCORE+" TEXT)";
        db.execSQL(sql_create_score);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addInformation(String firstname,String lastname,String username,String firebase_uid){

        SQLiteDatabase db =getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_FIRST_NAME,firstname);
        values.put(UsersMaster.Users.COLUMN_NAME_LAST_NAME,lastname);
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME,username);
        values.put(UsersMaster.Users.COLUMN_NAME_FIREBASE_UID,firebase_uid);

        boolean isSuccess = db.insert(UsersMaster.Users.TABLE_NAME,null,values)>0;
        db.close();
        return  isSuccess;


    }

    public boolean addScore(String firebaseUID, String score){


        SQLiteDatabase db_score = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_FIREBASE_UID,firebaseUID);
        values.put(UsersMaster.Users.COLUMN_NAME_SCORE,score);

        boolean isSuccuess  = db_score.insert(UsersMaster.Users.TABLE_SCORE,null,values)>0;
        db_score.close();
        return  isSuccuess;
    }

    public Cursor getListContents(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor data = database.rawQuery("SELECT * FROM "+UsersMaster.Users.TABLE_SCORE+"",null);

        return data;
    }
}
