package com.lec.android.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imgview;
    Button btnRest, btnmap, btnend;

    int[] imagefood ={R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};

    Handler mHandler2;
    int value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgview = findViewById(R.id.imgview);
        btnRest = findViewById(R.id.restaurant);
        btnmap = findViewById(R.id.map);
        btnend = findViewById(R.id.end);

        //화면 이미지 바꾸기
        mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgview.setImageResource(imagefood[value%5]);
                mHandler2.postDelayed(this, 3000);
                value++;
            }
        }, 1000);

        //식당 버튼 누르면 식당들이 있는 리스트로 가기
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),rest.class);
                startActivity(intent);
                finish();
            }
        });




    }//end onCreate()


}//end Acitvity


