package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SerialCommunicationService extends Service {
    public SerialCommunicationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class SerialCommunicationRunnable implements Runnable {
        private OutputStream out;

        @Override
        public void run() {
            try {
                Socket socket = new Socket("localhost", 9998);
                out = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
