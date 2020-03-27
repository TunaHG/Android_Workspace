package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

// Service가 실행될 때,
// Service가 존재하지 않는다면, Service를 생성
//   1. Service를 생성하기 위해 생성자가 호출
//   2. onCreate() 호출
//   3. onStartCommand() 호출 -> 로직처리 시작
// Service 객체가 존재한다면,
//   1. onStartCommand() 호출 -> 로직처리 시작
public class Example17Sub_DataTransferService extends Service {
    public Example17Sub_DataTransferService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 로직처리
        // Activity로부터 전달된 intent가 이 method의 인자로 전달됨
        String data = intent.getExtras().getString("DATA");

        // 전달받은 데이터를 이용해서 일반적인 로직처리를 진행
        // 로직처리가 길어지면, Service가 끝날때까지 Activity가 Block 됨
        // 이런 경우를 방지하기 위해 일반적으로 Thread를 활용해서 로직처리를 진행

        String resultData = data + " 를 받음";
        // 이 결과 데이터를 Activity에게 전달하고 Activity는 화면에
        // 결과데이터를 TextView로 출력
        Intent resultIntent = new Intent(getApplicationContext(),
                Example17_ServiceDataTransferActivity.class);
        // 결과데이터를 Intent에 부착
        resultIntent.putExtra("RESULT", resultData);

        // Service에서 Activity를 호출
        // 화면이 없는 Service에서 화면을 가지고 있는 Activity를 호출하기 위해
        // TASK가 필요
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Activity를 새로 생성하는게 아니라
        // 메모리에 존재하는 이전 Activity를 찾아서 실행 => Flag 두개 추가
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(resultIntent);
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
}
