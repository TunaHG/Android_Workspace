package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Example12_SimpleBookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example12_simple_book_search);

        // 검색버튼에 대한 Reference 획득
        Button searchBtn = findViewById(R.id.searchBtn);
        // 검색 입력창에 대한 Reference 획득
        final EditText searchTitle = findViewById(R.id.searchTitle);
        // 결과 ListView에 대한 Reference 획득
        final ListView searchList = findViewById(R.id.searchList);

        // Network연결(Web Application 호출)을 해야 하기 때문에
        // UI Thread(Activity)에서 이 작업을 하면 안된다.
        // => Thread로 해결해야 한다.
        // => Thread와 데이터를 주고받기 위해서 Handler를 이용

        // Handler 객체 생성
        @SuppressLint("HandlerLeak") // Memory Leak 방지
        final Handler handler = new Handler(){
            // Thread가 주는 책 데이터를 ListView에 전해주는 역할
            // Thread에 의해서 sendMessage가 호출되는데
            // sendMessage에 의해서 전달된 데이터를 처리하기 위해서
            // handleMessage를 Overriding하면서 instance를 생성
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // Thread가 보내준 데이터로 ListView를 채우는 코드
            }
        };

        // Button에 대한 Event처리
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookSearchRunnable runnable = new BookSearchRunnable();
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }
}

// Thread를 생성하기 위해 Runnable Interface를 구현한 Class를 정의
class BookSearchRunnable implements Runnable {
    @Override
    public void run() {

    }
}