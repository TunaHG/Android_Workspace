package com.example.layouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// Event 처리
// Event : 사용자 혹은 시스템에 의해서 발생되는 모든 것

// Event Handling
// Java는 Event Delegation Model을 이용하여 Event를 처리

// Event 처리에 관련된 3가지 객체가 존재
// 1. Event Source : Event가 발생한 객체
// 2. Event Handler(Listener) : Event를 처리하는 객체
// 3. Event : 발생된 Event에 대한 세부 정보를 가지고 있는 객체
// Event Source에 Event Handler를 부착시켜서 Event가 발생되면 부착된 Handler를 통해서 Event를 처리
public class ButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        // 1. Event Source 객체 가져오기 (ID를 이용)
        Button myBtn = findViewById(R.id.eventBtn);
        // 2. Event Handler 객체 생성
        MyEventHandler handler = new MyEventHandler();
        // 3. Event Source에 Handler 부착
        myBtn.setOnClickListener(handler);
    }
}

// 우리가 원하는건 일반 Class의 객체가 아니라
// 이벤트를 처리할 수 있는 특수한 능력을 가지고 있는 Listener 객체가 필요
// 다시 말하면, 우리가 작성하는 Class는 특수한 Interface를 구현한 Class가 되어야 한다.
// 특수한 Interface는 여러 개가 존재한다. (이벤트 종류에 따라서 여러개 존재)
class MyEventHandler implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        // 이벤트 처리코드를 작성
        Log.i("MYEVENT", "Button Click");
    }
}