package com.example.user.inventory;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UpDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_data);
    }
    public void onBack (View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        UpDataActivity.this.finish();
    }
    public void onDel (View v){
        Db db = new Db (this,"tblTable",null,1);
        SQLiteDatabase Sd = db.getWritableDatabase();
        Sd.delete("tblTable", null, null);
        app.file.delete();
    }
}
