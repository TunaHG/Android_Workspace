package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
* Android에서는 Broadcast라는 signal이 존재
* 이 신호(Signal)은 Android System 자체에서 발생할 수도 있고
* 사용자 App에서 임의로 발생시킬 수도 있다.
*
* 여러개의 일반적으로 정해져있는 Broadcast Message를 받고 싶다면
* Broadcast Receiver를 만들어서 등록해야 한다.
*
* 등록하려면 크게 다음의 2가지 방식을 사용
* 1. AndroidManifest.xml File에 명시
* 2. 코드상에서 Receiver를 만들어서 등
 */
public class Example19_BroadcastReceiverActivity extends AppCompatActivity {
    private BroadcastReceiver bReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example19_broadcast_receiver);

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Broadcast Receiver 등록버튼 Click시 호출
                // Broadcast Receiver 객체를 만들어서 IntentFilter와 함께 System에 등록
                // 1. IntentFilter 등록
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("MY_BROADCAST_SIGNAL");

                // 2. Broadcast Receiver객체 생성
                bReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // 이 Receiver가 신호를 받게되면 이 부분이 호출
                        // 로직처리 진행
                        if(intent.getAction().equals("MY_BROADCAST_SIGNAL")) {
                            Toast.makeText(getApplicationContext(),
                                    "Receive Signal", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                // 3. Filter와 함께 Broadcast Receiver를 등록
                registerReceiver(bReceiver, intentFilter);
            }
        });

        // 등록해제 Button Click Event
        Button unRegisterBtn = findViewById(R.id.unRegisterBtn);
        unRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(bReceiver);
            }
        });
    }
}
