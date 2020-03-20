package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Example11_CounterLogHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example11_counter_log_handler);

        // TextView, ProgressBar에 대한 Reference를 가져와서 final로 처리
        final TextView tv = findViewById(R.id.handlerSumTV);
        final ProgressBar pb = findViewById(R.id.handlerProgressBar);

        // 데이터를 주고받는 역할을 수행하는 Handler(android.os.Handler) 객체 생성
        // Handler 객체는 메시지를 전달하는 Method와
        // 메시지를 전달받아서 로직처리하는 Method 2개를 주로사용.
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // Handler가 Message를 받으면 호출되는 Method
                super.handleMessage(msg);
                // 받은 Message를 이용해서 화면처리
                Bundle bundle = msg.getData();
                String count = bundle.getString("count");
                pb.setProgress(Integer.parseInt(count));
            }
        };

        // 연산시작이라는 버튼을 클릭했을 때 로직처리하는 Thread를 생성하여 실행
        Button handlerStartBtn = findViewById(R.id.handlerStartBtn);
        handlerStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread가 Handler를 통해 데이터를 넘겨주면 Activity가 Handler를 통해 데이터를 받는다.
                // Activty와 데이터 통신을 할 수 있는 Handler를 전달받은 Thread를 생성해야 함
                MySumThread runnable = new MySumThread(handler);
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }
}

// Activity Class에 field도 생성하지 않았고,
// Thread Class를 Inner Class가 아닌 외부 Class로 생성
// 연산을 담당하는 Thread를 위한 Runnable Interface를 상속받는 Class
class MySumThread implements Runnable {
    private Handler handler;

    MySumThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        // 숫자를 더해가면서 progressbar를 진행시킨다.
        long sum = 0;
        for(long i = 0; i < 10000000000L; i++){
            sum += i;
            if(i % 100000000 == 0){
                long loop = i % 100000000;
                // Message를 만들어서 handler를 이용해 Activity에게 Message 전달
                // pb.setProgress((int)loop);
                // Bundle 객체 생성
                Bundle bundle = new Bundle();
                bundle.putString("count", String.valueOf(loop));

                // 이 Bundle을 가질 수 있는 Message 객체 생성
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }
        // tv.setText("합계는 : " + sum);
    }
}