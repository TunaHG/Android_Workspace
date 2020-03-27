package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Example18_KakaoBookSearchActivity extends AppCompatActivity {
    EditText kakaoET;
    ListView kakaoBookList;
    Button kakaoSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example18_kakao_book_search);

        // Widget들의 Reference 획득
        kakaoET = findViewById(R.id.kakaoET);
        kakaoSearchBtn = findViewById(R.id.kakaoSearchBtn);
        kakaoBookList = findViewById(R.id.kakaoBookList);

        // Button Event처리
        kakaoSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        Example18Sub_KakaoBookSearchService.class);
                intent.putExtra("KEYWORD", kakaoET.getText().toString());
                startService(intent);
            }
        });
    }
}
