package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SerialCommunicationActivity extends AppCompatActivity {
    private Button serverConnectBtn, onBtn, offBtn;
    private OutputStream out;
    private int num = 1;
    private boolean flag = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_communication);

        serverConnectBtn = findViewById(R.id.serverConnectBtn);
        serverConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SerialRunnable runnable = new SerialRunnable();
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

        onBtn = findViewById(R.id.onBtn);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SERIAL", "On Button Clicked");
                flag = true;
                num = 1;
            }
        });

        offBtn = findViewById(R.id.offBtn);
        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SERIAL", "Off Button Clicked");
                flag = true;
                num = 0;
            }
        });
    }

    class SerialRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket("70.12.229.25", 9998);
                out = socket.getOutputStream();

                Log.i("SERIAL", "Server Connected");
                while(true){
                    if(flag){
                        Log.i("SERIAL", "Out Stream");
                        out.write(num);
                        out.flush();
                        flag = false;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}