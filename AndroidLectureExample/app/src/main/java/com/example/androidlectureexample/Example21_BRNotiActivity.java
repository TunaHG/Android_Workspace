package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Example21_BRNotiActivity extends AppCompatActivity {
    private BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example21_br_noti);

        Button registerNotiBtn = findViewById(R.id.registerNotiBtn);

        registerNotiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Broadcast Receiver를 등록하는 작업을 진행
                // 1. IntentFilter를 생성 (Action을 설정)
                IntentFilter filter = new IntentFilter();
                filter.addAction("MY_NOTI_SIGNAL");

                // 2. Broadcast를 받는 Receiver를 생성
                br = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // Broadcast를 받으면 이 Method가 호출

                        // 1. Notification(알림)을 띄우려면 Notification Manager가 필요
                        //    Android System이 이미 가지고 있는 Manager를 가져온다.
                        NotificationManager nManager =
                                (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

                        // 2. Android Oreo(8) 버전을 기준으로 코드차이가 발생
                        //    Oreo버전 이후에는 Channel을 이용
                        //    Channel이 나온 이뉴 : 알림의 종류가 많기 때문
                        //      중요하지 않은 알림일 때는 단순 소리만 나게끔 처리하거나
                        //      중요한 알림일 때는 진동까지 같이 나게끔 처리하는 경우 등
                        String channelID = "MY_CHANNEL";
                        String channelName = "MY_CHANNEL_NAME";
                        int importance = NotificationManager.IMPORTANCE_HIGH;

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel nChannel =
                                    new NotificationChannel(channelID, channelName, importance);
                            // 이렇게 Notification Channel을 생성한 후 설정 추가
                            nChannel.enableVibration(true);
                            nChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
                            nChannel.enableLights(true);
                            nChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

                            nManager.createNotificationChannel(nChannel);
                        }

                        // Builder를 이용해서 Notification을 생성
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(
                                        context.getApplicationContext(), channelID
                                );

                        // Notification을 보내기 위해 Intent가 필요
                        // Intent의 대상이 어디인지를 표시해야함
                        Intent nIntent = new Intent(getApplicationContext(),
                                Example21_BRNotiActivity.class);

                        // Notification이 Activity위에 표시되야하기 때문에 설정 추가
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // 중복되지 않는 상수값을 얻기위해 사용 (requestID가 중복되지않아야함)
                        int requestID = (int)System.currentTimeMillis();

                        // PendingIntent를 생성해서 사용
                        // 위에서 생성한 Intent를 가지고 PendingIntent를 생성
                        PendingIntent pIntent =
                                PendingIntent.getActivity(getApplicationContext(),
                                        requestID, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        // Builder를 이용해서 최종적으롱 Notification을 생성
                        builder.setContentTitle("Noti Title");
                        builder.setContentText("Noti Text");
                        // 알림의 기본사운드, 기본진동 설정
                        builder.setDefaults(Notification.DEFAULT_ALL);
                        // 알림 터치시 반응 후 삭제
                        builder.setAutoCancel(true);
                        builder.setSound(RingtoneManager.getDefaultUri(
                                RingtoneManager.TYPE_NOTIFICATION
                        ));
                        // 작은 아이콘 설정
                        builder.setSmallIcon(android.R.drawable.btn_star);

                        builder.setContentIntent(pIntent);

                        // 실제로 Notification을 띄우는 코드
                        nManager.notify(0, builder.build());
                    }
                };

                // 3. 생성한 Receiver를 Filter와 함께 등록
                registerReceiver(br, filter);
            }
        });

        Button sendSignalBtn = findViewById(R.id.sendSignalBtn);
        sendSignalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("MY_NOTI_SIGNAL");
                sendBroadcast(i);
            }
        });
    }
}
