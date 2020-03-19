package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Example05_SwipeEventActivity extends AppCompatActivity {
    double x1, x2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example05_swipe_event);

        LinearLayout ll = findViewById(R.id.myLinearlayout);
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String msg = "";

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    x1 = event.getX(); // 누르는 Touch일 경우, 그때의 X좌표
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    x2 = event.getX(); // 떼는 Touch일 경우, 그 때의 X좌표
                    if(x1 < x2){
                        msg = "Right Swipe";
                    } else {
                        msg = "Left Swipe";
                    }
                    Toast.makeText(Example05_SwipeEventActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
