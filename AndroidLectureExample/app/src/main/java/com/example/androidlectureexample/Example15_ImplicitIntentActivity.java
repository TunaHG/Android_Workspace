package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Example15_ImplicitIntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example15_implicit_intent);

        Button implicitIntentBtn = findViewById(R.id.implicitIntentBtn);
        implicitIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Implicit", "Button Click");
                // Button을 클릭했을 때 새로운 Activity를 실행(묵시적 Intent 이용)
                // Implicit Intent(묵시적 인텐트)
                // Sub로 생성한 Activity 호출
                Intent i = new Intent();
                i.setAction("MY_ACTION");
                i.addCategory("INTENT_TEST");
                i.putExtra("Send Data", "Hello!");
                startActivity(i);
            }
        });
    }
}
