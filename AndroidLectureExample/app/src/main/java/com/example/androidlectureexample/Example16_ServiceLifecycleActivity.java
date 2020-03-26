package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Example16_ServiceLifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example16_service_lifecycle);

        Button startServiceBtn = findViewById(R.id.startServiceBtn);
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Service Component를 시작
                Intent i = new Intent(getApplicationContext(),
                        Example16Sub_LifecycleService.class);
                i.putExtra("MSG", "Hello I'm Message!");
                startService(i);
                // 만약 Service객체가 없으면 생성하고 수행
                // => onCreate() -> onStartCommand() 순으로 호출
                // 만약 Service객체가 존재하고 있으면
                // onCreate() 호출없이 onStartCommand()만 호출
            }
        });

        Button stopServiceBtn = findViewById(R.id.stopServiceBtn);
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        Example16Sub_LifecycleService.class);
                stopService(i);
            }
        });
    }
}
