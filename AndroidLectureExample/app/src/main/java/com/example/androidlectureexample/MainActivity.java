package com.example.androidlectureexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button _01_linearlayoutBtn = findViewById(R.id._01_linearlayoutBtn);

        _01_linearlayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼이 눌리면 이 Method가 실행된다.
                // 새로운 Activity를 찾아서 실행
                // Activity를 찾는 방식은 Explicit방식과 Implicit방식이 있다.
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example01_LayoutActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _02_widgetBtn = findViewById(R.id._02_widgetBtn);

        _02_widgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example02_WidgetActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _03_EventBtn = findViewById(R.id._03_EventBtn);

        _03_EventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example03_EventActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _04_ActivityEventBtn = findViewById(R.id._04_ActivityEventBtn);

        _04_ActivityEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example04_TouchEventActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _05_SwipeEventBtn = findViewById(R.id._05_SwipeEventBtn);

        _05_SwipeEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example05_SwipeEventActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _06_SendMessageBtn = findViewById(R.id._06_SendMessageBtn);

        _06_SendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alert창(Dialog)을 이용해서 문자열을 입력받고
                // 입력받은 문자열을 다음 Activity로 전달

                // 사용자가 문자열을 입력할 수 있는 Widget이 필요
                edittext = new EditText(MainActivity.this);

                // AlertDialog를 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Activity 데이터 전달");
                builder.setMessage("다음 Activity에 전달할 내용을 입력하세요.");
                builder.setView(edittext);
                builder.setPositiveButton("전달",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 전달을 눌렀을 때 수행되는 이벤트 처리
                                Intent i = new Intent();
                                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                                        "com.example.androidlectureexample.Example06_SendMessageActivity");
                                i.setComponent(cname);
                                // 데이터를 전달해서 Activity를 시작
                                i.putExtra("sendMSG", edittext.getText().toString());
                                startActivity(i);
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 취소를 눌렀을 때 수행되는 이벤트
                                // 특별한 처리를 안하면 Dialog가 종료되고 끝남
                            }
                        });
                builder.show();
            }
        });

        Button _07_DataFromBtn = findViewById(R.id._07_DataFromBtn);

        _07_DataFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example07_DataFromActivity");
                i.setComponent(cname);

                // 새로 생성되는 Activity로부터 데이터를 받아오기 위한 용도
                // 두 번째 Activity가 finish되는 순간 데이터를 받음
                startActivityForResult(i, 3000);
                // 어떤 Activity로부터 데이터를 받는지 알기 위해 requestCode가 필요
                // requestCode는 어떤값이든 다른 requestCode와 겹치지 않는 Unique한 값이면 됨.
            }
        });

        Button _08_ANRBtn = findViewById(R.id._08_ANRBtn);

        _08_ANRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example08_ANRActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _09_CounterLogBtn = findViewById(R.id._09_CounterLogBtn);

        _09_CounterLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example09_CounterLogActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _10_CounterLogProgressBtn = findViewById(R.id._10_CounterLogProgressBtn);

        _10_CounterLogProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example10_CounterLogProgressActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _11_CounterLogHandlerBtn = findViewById(R.id._11_CounterLogHandlerBtn);

        _11_CounterLogHandlerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example11_CounterLogHandlerActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _12_SimpleBookSearchBtn = findViewById(R.id._12_SimpleBookSearchBtn);

        _12_SimpleBookSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example12_SimpleBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _13_ExpandBookSearchBtn = findViewById(R.id._13_ExpandBookSearchBtn);

        _13_ExpandBookSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example13_ExpandBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _15_ImplicitIntentBtn = findViewById(R.id._15_ImplicitIntentBtn);

        _15_ImplicitIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example15_ImplicitIntentActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        /*
        App이 실행되었다고 해서 항상 Activity가 보이지 않음
        대표적인 경우 카톡, 멜론 등 Activity가 보이지 않아도 기능이 수행되는 경우가 있음
        Service는 화면이 없는 Activity라고 생각할 수 있음
        Activity의 Life Cycle은 다음과 같은 반면
            onCreate() -> onStart() -> onResume() -> onPause() -> onStop() -> onDestroy()
        Service의 Life Cycle은 다음과 같음 (Activity보다 짧음)
            onCreate() -> onStartCommand() -> onDestroy()
        눈에 보이지 않기 때문에 Background에서 로직처리하는데 이용
        */
        Button _16_ServiceLifecycleBtn = findViewById(R.id._16_ServiceLifecycleBtn);

        _16_ServiceLifecycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example16_ServiceLifecycleActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _17_ServiceDataTransferBtn = findViewById(R.id._17_ServiceDataTransferBtn);

        _17_ServiceDataTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example17_ServiceDataTransferActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button _18_KakaoBookSearchBtn = findViewById(R.id._18_KakaoBookSearchBtn);

        _18_KakaoBookSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example18_KakaoBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
    } // end of onCreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3000 && resultCode == 7000) {
            String msg = (String)data.getExtras().get("resultValue");
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
