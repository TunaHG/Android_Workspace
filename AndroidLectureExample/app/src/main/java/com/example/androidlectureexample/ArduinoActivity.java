package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class ArduinoActivity extends AppCompatActivity {
    private TextView statusTV;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;

    class SharedObject {
        private Object MONITOR = new Object();
        private LinkedList<String> list = new LinkedList<>();

        SharedObject() {}

        public void put(String msg){
            synchronized (MONITOR) {
                list.addLast(msg);
                MONITOR.notify();
                Log.i("ArduinoTest", "공용개체에 데이터 입력");
            }
        }

        public String pop(){
            String result = "";
            synchronized (MONITOR) {
                if (list.isEmpty()) {
                    try {
                        MONITOR.wait();
                        result = list.removeFirst();
                    } catch (Exception e) {
                        Log.i("ArduinoTest", e.toString());
                    }
                } else {
                    result = list.removeFirst();
                    Log.i("ArduinoTest", "공용객체에서 데이터 추출");
                }
            }
            return result;
        }
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            statusTV.setText(msg.getData().getString("LED"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino);

        statusTV = findViewById(R.id.statusTV);
        final SharedObject shared = new SharedObject();

        // Java Network Server에 접속
        // Activity(UI Thread)에서 Network 처리코드를 사용할 수 없다.
        // 별도의 Thread를 이용해서 처리
        // 1. Runnable 객체를 생성
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    socket = new Socket("70.12.226.160", 9998);

                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pw = new PrintWriter(socket.getOutputStream());
                    Log.i("ArduinoTest", "Server Connected");

                    while(true){
                        String msg = shared.pop();
                        pw.println(msg);
                        pw.flush();
                        String tmp;
                        if((tmp = br.readLine()) != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("LED", tmp);
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                } catch (IOException e){
                    Log.i("ArduinoTest", e.toString());
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();

        Button ledOnBtn = findViewById(R.id.ledOnBtn);
        ledOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread가 사용하는 공용객체를 이용해서 메시지를 전송
                // 공용객체에 데이터를 입력
                shared.put("LED_ON");
            }
        });

        Button ledOffBtn = findViewById(R.id.ledOffBtn);
        ledOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.put("LED_OFF");
            }
        });
    }
}
