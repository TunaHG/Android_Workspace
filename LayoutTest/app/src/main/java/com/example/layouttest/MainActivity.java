package com.example.layouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// Activity
// Activity는 App을 구성하고 있는 화면을 지칭
// 화면을 표현하고 관리하기 위한 Class

// 화면을 표현할 때는 Activity 하나와 하나 이상의 XML파일이 필요
// Activity 내에서 Java Code로 화면 구성을 다 할 수는 있으나 권장되지 않음
//   => 표현과 구현이 분리되지 않기 때문
// 우리의 App은 일반적으로 여러 개의 Activity로 구성

// XML에 여러가지 Widget을 넣어서 사용자 Component를 표현
// 이런 Widget을 내가 원하는 크기로 원하는 위치에 표현하기 위해서
// Widget을 관리하는 Component가 따로 필요 ==> LayoutManager
// LinearLayout, GridLayout

// LinearLayout
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
