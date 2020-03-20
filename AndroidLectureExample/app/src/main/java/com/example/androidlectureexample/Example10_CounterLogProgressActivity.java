package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Example10_CounterLogProgressActivity extends AppCompatActivity {
    private TextView tv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example10_counter_log_progress);

        tv = findViewById(R.id.progressSumTV);
        pb = findViewById(R.id.progressBar);

        Button progressStartBtn = findViewById(R.id.progressStartBtn);
        progressStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread를 만들어서 처리
                ProgressRunnable runnable = new ProgressRunnable();
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }

    // Android UI Component(Widget)는 Thread Safe하지 않음
    // Android UI Component는 오직 UI Thread에 의해서만 제어되어야 한다.
    // 화면제어(UI Component-Widget)는 오직 UI Thread에 의해서만(Activity) 제어될 수 있다.
    // 아래의 Code는 실행되지만 올바르지 않은 코드이다.
    // 외부 Thread(UI Thread가 아닌)에서 UI Component를 직접 제어할 수 없다.
    // Handler를 이용해서 이 문제를 해결할 수 있다.
    class ProgressRunnable implements Runnable {
        @Override
        public void run() {
            // 숫자를 더해가면서 progressbar를 진행시킨다.
            long sum = 0;
            for(long i = 0; i < 10000000000L; i++){
                sum += i;
                if(i % 100000000 == 0){
                    long loop = i % 100000000;
                    pb.setProgress((int)loop);
                }
            }
            tv.setText("합계는 : " + sum);
        }
    }
}
