package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
 * 1. 우리 App이 휴대단말로 온 문자메시지를 처리
 *    문자메시지 처리는 상당히 개인적인 정보 => 보안처리가 필요
 *    - AndroidManifest.xml파일에 기본 보안에 대한 설정이 필요
 *
 * 2. Broadcast Receiver Component를 생성
 *    코드상에서 생성하는게 아니라 AndroidManifest.xml에 등록하여 생성
 *    외부 Component로 따로 생성되기 때문에 AndroidManifest.xml 에 자동등록
 *    => 생성된 후 intent-filter를 이용해서 어떤 Broadcast Signal을 수신할지 설정
 *    SMS문자가 오면 Broadcast Receiver가 이 문자를 받아서 Activity에 전달
 *
 * 3. Activity가 실행되면 보안설정 진행
 *    마쉬멜로우 버전이후 부터는 강화된 보안정책을 따라야함
 *    보안처리코드가 나와야함
 */
public class Example20_BRSMSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example20_br_sms);
    }
}
