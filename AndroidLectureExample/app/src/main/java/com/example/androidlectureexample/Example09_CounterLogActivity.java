package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Example09_CounterLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example09_counter_log);

        Button counterStartBtn = findViewById(R.id.counterStartBtn);
        counterStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread를 만들어서 실행
                MyRunnable runnable = new MyRunnable();
                Thread t = new Thread(runnable);
                t.start();
            }
        });

        // Toast Message를 출력하는 Button
        Button counterSecondBtn = findViewById(R.id.counterSecondBtn);
        counterSecondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Example09_CounterLogActivity.this,
                        "Click Button!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            long sum = 0;
            for(long i = 0; i < 10000000000L; i++){
                sum += i;
            }
            Log.i("SumTest", "총합은 " + sum);
        }
    }
}
