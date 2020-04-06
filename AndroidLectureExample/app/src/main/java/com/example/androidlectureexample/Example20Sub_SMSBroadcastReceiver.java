package com.example.androidlectureexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

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
        // 전달받은 Intent안에 보낸사람의 전화번호, 메시지내용, 날짜 등 정보가 들어있음

        // Intent안에 포함되어 있는 정보를 추출
        Bundle bundle = intent.getExtras();

        // Bundle안에 key, value형태로 데이터가 여러 개 저장되어 있는데,
        // SMS의 정보는 "pdus"라는 키값으로 저장되어 있음
        // 거의 시간상 동시에 여러 개의 SMS가 도착할 수 있으므로 Object[]로 처리
        // 객체 1개가 SMS메시지 1개를 의미
        Object[] obj = (Object[])bundle.get("pdus");

        SmsMessage[] message = new SmsMessage[obj.length];

        // 해당 예제에서는 1개의 SMS만 전달된다고 가정하고 진행
        // Object 객체 형태를 SmsMessage 객체 형태로 Converting
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            message[0] = SmsMessage.createFromPdu((byte[])obj[0], format);
        } else {
            message[0] = SmsMessage.createFromPdu((byte[])obj[0]);
        }

        // 보낸사람 전화번호를 SmsMessage객체에서 추출
        String sender = message[0].getOriginatingAddress();
        // SMS 문자내용 추출
        String msg = message[0].getMessageBody();
        // SMS 받은 시간 추출
        String reDate = new Date(message[0].getTimestampMillis()).toString();

        Log.i("SMSTest", "전화번호 : " + sender);
        Log.i("SMSTest", "내용 : " + msg);
        Log.i("SMSTest", "시간 : " + reDate);

        // 데이터를 잘 받아왔으면 해당 Data를 Activity에게 전달
        Intent i = new Intent(context,
                Example20_BRSMSActivity.class);
        // 기존에 이미 생성되어 있는 Activity에게 전달해야 하므로 Flag설정
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Intent에 데이터 저장해서 전달
        i.putExtra("sender", sender);
        i.putExtra("msg", msg);
        i.putExtra("reDate", reDate);

        context.startActivity(i);
    }
}
