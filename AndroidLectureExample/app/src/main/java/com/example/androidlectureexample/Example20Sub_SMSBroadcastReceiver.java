package com.example.androidlectureexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * 보안설정이 잘 되어 있으면,
 * 특정 Signal(Broadcast가 전파되면)이 발생하면
 * 해당 Broadcast를 받을 수 있음
 */
public class Example20Sub_SMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Broadcast를 받으면, 이 Method가 호출
        // (SMS가 도착하면 해당 Method 호출)
        Log.i("SMSTest", "Receive SMS");
        // 만약 SMS Signal을 받을 수 있으면
        // 전화번호, 문자내용을 Activity에게 전달
    }
}
