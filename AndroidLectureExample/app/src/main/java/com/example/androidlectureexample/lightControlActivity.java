package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class lightControlActivity extends AppCompatActivity {
    private TextView lightValueTV;
    private SeekBar lightControlSB;
    private BufferedReader br;
    private PrintWriter pw;
    private Socket socket;

    class SharedObject {
        private Object MONITOR = new Object();
        private LinkedList<Integer> list = new LinkedList<>();

        SharedObject() {}

        public void put(int msg){
            synchronized (MONITOR) {
                list.addLast(msg);
                MONITOR.notify();
                Log.i("ArduinoTest", "공용객체에 데이터 입력");
            }
        }

        public int pop(){
            int result = 0;
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
            lightValueTV.setText(msg.getData().getString("LED"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_control);

        lightValueTV = findViewById(R.id.lightValueTV);
        final SharedObject shared = new SharedObject();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    socket = new Socket("70.12.226.160", 9998);
//                    socket = new Socket("70.12.229.25", 9997);

                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pw = new PrintWriter(socket.getOutputStream());
                    Log.i("ArduinoTest", "Server Connected");

                    while(true){
                        int msg = shared.pop();
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

        lightControlSB = findViewById(R.id.lightControlSB);
        lightControlSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("ArduinoTest", "수치 : " + progress);
                shared.put(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
