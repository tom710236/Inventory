package com.example.user.inventory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2017/9/28.
 */

public class Db extends SQLiteOpenHelper {
    public Db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String create =
                ("CREATE TABLE tblTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "cName TEXT, "
                        + "cQRcode TEXT);");
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
