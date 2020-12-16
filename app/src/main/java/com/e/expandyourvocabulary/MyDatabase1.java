package com.e.expandyourvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase1 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_TABLE_NAME = "mydatabase1";
    private static final String PKEY = "pkey";
    private static final String COL1 = "col1";
    private static final String COL2 = "col2";

    MyDatabase1(Context context) {
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
    }

    public boolean deleteRow(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DATABASE_TABLE_NAME, COL1 + "='" + name +"' ;", null) > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_TABLE_CREATE =
                "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
                        PKEY + " INTEGER PRIMARY KEY," +
                        COL1 + " TEXT," +
                        COL2 + " TEXT);";
        db.execSQL(DATABASE_TABLE_CREATE);
        Log.i("Quentin", "Database created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) { // J'ai gardé l'upgrade pas très fin pour l'instant
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
            onCreate(db);
        }
    }

    public void insertData(String s1, String s2)
    {
        Log.i("Quentin"," Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COL1, s1);
        values.put(COL2, s2);
        db.insertOrThrow(DATABASE_TABLE_NAME,null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List readData()
    {
        List itemWords = new ArrayList<>();

        Log.i("Quentin", "Reading database...");
        String select = new String("SELECT * from " + DATABASE_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.i("Quentin", "Number of entries: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String[] listWord = {};
            do {
                listWord = new String[]{cursor.getString(cursor.getColumnIndex(COL1)), cursor.getString(cursor.getColumnIndex(COL2))};
                itemWords.add(listWord);
            } while (cursor.moveToNext());
        }
        return itemWords;
    }

}
