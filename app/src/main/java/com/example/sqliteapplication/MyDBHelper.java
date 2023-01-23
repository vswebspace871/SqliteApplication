package com.example.sqliteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ContactsDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "contactTable";
    private static final String KEY_ID = "id";
    private static final String KEY_Name = "name";
    private static final String KEY_Ph_Number = "phone_number";


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Database table creation

        db.execSQL("CREATE TABLE "+TABLE+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_Name+
                " TEXT, "+KEY_Ph_Number+" TEXT "+")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //on DB UPGRADE

        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);

    }


    // DB CRUD METHOD BELOW
    //Insert Method
    public void insertContact(String name, String phone_no){
        SQLiteDatabase db = this.getWritableDatabase();
        //content value is like bundle
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Name, name);
        contentValues.put(KEY_Ph_Number, phone_no);

        db.insert(TABLE, null, contentValues);
        db.close();
    }

    public ArrayList<Model> viewContact(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Model> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE, null);

        while (cursor.moveToNext()){
            Model model = new Model();
            model.id = cursor.getInt(0);
            model.name = cursor.getString(1);
            model.phone_number = cursor.getString(2);

            list.add(model);
        }

        return list;
    }

    public void updateContact(Model model){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_Ph_Number, model.phone_number);
        cv.put(KEY_Name, model.name);

        db.update(TABLE, cv, KEY_ID+" = "+model.id, null );
    }

    public void deleteContact(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, KEY_ID+" = ? ", new String[]{String.valueOf(id)});

        db.close();

    }
}
