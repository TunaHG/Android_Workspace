package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Example03_EventActivity extends AppCompatActivity {
    private Button btn;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example03_event);

        iv = findViewById(R.id.iv);

        // Button에 대한 Event 처리를 해야한다.
        btn = findViewById(R.id.ImageChangeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.drawable.cat2);
            }
        });
    }
}
