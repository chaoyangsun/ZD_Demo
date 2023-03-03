package com.example.zdtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    final String CREATE_USER_SQL =
            "create table user(_id integer primary " + "key autoincrement , useremail, username, password)";

    private static DBHelper mInstance;

    public static synchronized DBHelper getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context, "ZD_DB.db", null, 1);
        }
        return mInstance;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
