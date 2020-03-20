package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Example08_ANRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example08_anr);

        // TestView에 대한 Reference 획득
        TextView sumTV = findViewById(R.id.sumTV);

        // 첫 번째 버튼에 대한 Reference 획득 & Event 처리
        // 버튼을 누르면 상당히 오랜시간 연산이 수행
        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long sum = 0;
                for(long i = 0; i < 10000000000L; i++){
                    sum += i;
                }
                Log.i("SumTest", "연산된 결과는 " + sum);
            }
        });
        // 결과적으로 사용자와의 interaction이 중지
        // Activity가 Block된 것처럼 보인다. (ANR : Application Not Responding)
        // ANR 현상은 피해야 함
        // Activity의 최우선 작업은 사용자와의 Interaction
        // Activity에서는 시간이 오래 걸리는 작업은 하면 안됨
        // Activity는 Thread로 동작함 (UI Thread)
        // 로직 처리는 Background Thread를 이용해서 처리해야 한다.

        // 두 번째 버튼에 대한 Reference 획득 & Event 처리
        // Toast Message를 출력
        Button secondBtn = findViewById(R.id.secondBtn);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Example08_ANRActivity.this,
                        "Click Button", Toast.LENGTH_LONG).show();
            }
        });
    }
}
