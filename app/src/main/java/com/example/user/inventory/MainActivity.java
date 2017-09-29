package com.example.user.inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onInventory (View v){
        Intent intent = new Intent(this,InventonyActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
    public void onUpdata (View v){
        Intent intent = new Intent(this,UpDataActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}
