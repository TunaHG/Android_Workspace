package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Example16Sub_LifecycleService extends Service {
    private Thread myThread;

    public Example16Sub_LifecycleService() { }

    // Bind Service는 지금 사용하지 않는다.
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Service 객체가 생성될 때 호출되는 Method
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("ServiceExam", "Call onCreate()");

    }

    // 실제 Service 동작을 수행하는 Method
    // onCreate() -> onStartCommand(), 우리가 호출하지 않아도 바로 따라서 호출된다.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceExam", "Call onStartCommand()");
        // 1초간격으로 1부터 시작해서 10까지 숫자를 Log로 출력
        // Thread가 가지고 있는 run() Method가 호출
        // 언젠가는 run() Method의 실행이 종료됨
        // 종료된 이후에 Thread의 상태가 DEAD상태가 됨
        // => DEAD 상태에서 다시 실행시킬수 있는 방법이 없음
        //    유일하게 다시 실행시키는 방법은 Thread를 다시 생성해서 실행
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Thread가 시작되면 1초동안 쉬었다가 Log를 이용해서 숫자를 출력
                for(int i = 1; i <= 100; i++){
                    try {
                        Thread.sleep(1000); // 1초
                        // sleep을 하려고할 때, interrupt가 걸려있으면 Exception 발생
                        Log.i("ServiceExam", "현재 숫자는 : " + i);
                    } catch (Exception e){
                        Log.i("ServiceExam", e.toString());
                        break; // 가장 가까운 Loop를 벗어남
                    }
                }
            }
        });
        myThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    // stopService()가 호출되면 onDestroy()가 호출
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ServiceExam", "Call onDestroy()");

        if(myThread != null && myThread.isAlive()) {
            // Thread가 존재하고 현재 Thread가 실행중이라면,
            // Thread를 중지
            myThread.interrupt();

            // 예전에 사용하던 방법 (stop()에 밑줄그어져 있으니 deprecated되었으니 쓰지말라는 의미)
//            myThread.stop();
        }
    }
}