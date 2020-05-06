package com.lec.android.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class test_Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent); //화면 전환
                finish(); //intro 화면은 종료
            }
        };
        mHandler.sendEmptyMessageDelayed(1,3000);
    }
}
