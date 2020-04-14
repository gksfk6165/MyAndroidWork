package com.lec.android.a002_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int count = 90;
    Button btn;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turn);


        btn = (Button) findViewById(R.id.btn1);
        img = (ImageView) findViewById(R.id.imageView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setRotation(count);
                count+=10;
            }
        });
    }
}
