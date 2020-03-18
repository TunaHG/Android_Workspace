package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button _01_linearlayoutBtn;

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
    }
}
