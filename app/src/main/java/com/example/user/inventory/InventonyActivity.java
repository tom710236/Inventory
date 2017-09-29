package com.example.user.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InventonyActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {
    String today,today2;
    String logName=null,logBarcode=null;
    String number;
    int mSoundID ;
    SoundPool mSoundPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventony);
        setmEditText();

        //音效宣告
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        mSoundPool.setOnLoadCompleteListener(InventonyActivity.this);
        mSoundID = mSoundPool.load (this, R.raw.windows_8_notify,1);

    }
    public void onBack (View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        InventonyActivity.this.finish();
    }

    private void setmEditText() {
        final EditText editText , editText2 ;
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(setmCheckQRcode(editText2.getText().toString()) == true ){
                    setmIntoQRcode(editText.getText().toString(),editText2.getText().toString());
                    logName = editText.getText().toString();
                    logBarcode = editText2.getText().toString();
                    Toast.makeText(InventonyActivity.this,"輸入成功",Toast.LENGTH_SHORT).show();
                    extelnalPrivateCreateFoler();
                }else {
                    Toast.makeText(InventonyActivity.this,"輸入失敗",Toast.LENGTH_SHORT).show();
                    Bsound();
                }

                return false;
            }
        });
    }

    private boolean setmCheckQRcode(String QRcode){
        Log.e("QRcode",QRcode);
        Db db = new Db (this,"tblTable",null,1);
        SQLiteDatabase Sd = db.getWritableDatabase();
        Cursor c = Sd.query("tblTable",                            // 資料表名字
                null,                                              // 要取出的欄位資料
                "cQRcode=?",                                    // 查詢條件式(WHERE)
                new String[]{QRcode},          // 查詢條件值字串陣列(若查詢條件式有問號 對應其問號的值)
                null,                                              // Group By字串語法
                null,                                              // Having字串法
                null);                                             // Order By字串語法(排序)

        while (c.moveToNext()) {
            String cQRcode = c.getString(c.getColumnIndex("cQRcode"));
            Log.e("cQRcode", cQRcode);
        }
        int i = c.getCount();
        Log.e("筆數", String.valueOf(i));
        if(i>0){
            return false;
        }
        return true;
    }
    private void setmIntoQRcode(String name ,String QRcode){
        Db db = new Db (this,"tblTable",null,1);
        SQLiteDatabase Sd = db.getWritableDatabase();
        ContentValues addbase = new ContentValues();
        addbase.put("cName",name);
        addbase.put("cQRcode",QRcode);
        Sd.insert("tblTable",null,addbase);
        Sd.close();
    }

    //外部空間建立私有資料夾
    private void extelnalPrivateCreateFoler(){
        time();
        File dir = getExtermalStoragePrivateDir("Log");
        String fileName = today2+".txt";
        File f = new File(dir, fileName);
        app.file = f ;
        String data = "人員編號:"+logName+",條碼:"+logBarcode+"\r\n";

        try {
            //new FileOutputStream(f,true) 多加true 就可以複寫
            FileOutputStream outputStream = new FileOutputStream(f,true);
            outputStream.write(data.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //私用
    private File getExtermalStoragePrivateDir(String albumName) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            //Log.e("", "Directory not created or exist");
        }
        return file;
    }

    //得到現在時間
    private void time() {
        Calendar mCal = Calendar.getInstance();
        String dateformat = "yyyy/MM/dd/ HH:mm:ss";
        String dateformat2=  "yyyyMMdd";
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        SimpleDateFormat df2 = new SimpleDateFormat(dateformat2);
        today = df.format(mCal.getTime());
        today2 = df2.format(mCal.getTime());
    }

    //音效 短
    private void Bsound(){
        mSoundPool.play(mSoundID,1.0F,1.0F,0,0,0.0f);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}
