package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button _01_linearlayoutBtn;
    private Button _02_widgetBtn;
    private Button _03_EventBtn;
    private Button _04_ActivityEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _01_linearlayoutBtn = findViewById(R.id._01_linearlayoutBtn);

        _01_linearlayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼이 눌리면 이 Method가 실행된다.
                // 새로운 Activity를 찾아서 실행
                // Activity를 찾는 방식은 Explicit방식과 Implicit방식이 있다.
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example01_LayoutActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        _02_widgetBtn = findViewById(R.id._02_widgetBtn);

        _02_widgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example02_Widget");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        _03_EventBtn = findViewById(R.id._03_EventBtn);

        _03_EventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example03_Event");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        _04_ActivityEventBtn = findViewById(R.id._04_ActivityEventBtn);

        _04_ActivityEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example04_TouchEventActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
    }
}
