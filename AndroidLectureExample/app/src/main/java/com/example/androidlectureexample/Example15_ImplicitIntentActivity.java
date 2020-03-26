package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

        Button dialBtn = findViewById(R.id.dialBtn);
        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전화걸기 Activity를 호출하는 2가지 방법
                // 1. 클래스명을 사용하여 호출(Explicit Intent)
                //    => 클래스명을 모르기 때문에 사용불가능
                // 2. 묵시적 Intent를 이용해서 알려져있는 Action을 사용해서 호출
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                // Intent.ACTION_DIAL => 전화가 걸리는 것이 아닌, 전화번호를 입력하는 과정까지 진행
                i.setData(Uri.parse("tel:01096117555"));
                startActivity(i);
            }
        });

        Button browserBtn = findViewById(R.id.browserBtn);
        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 특정 URL을 이용해서 Browser를 실행
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.naver.com"));
                startActivity(i);
            }
        });
    }
}
