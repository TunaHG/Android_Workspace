package com.example.layouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    TextView myTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        // 0. TextView 객체에 대한 Reference 가져오기
        myTV = findViewById(R.id.myTV);
        // 1. Event Source 객체 가져오기 (ID를 이용)
        Button myBtn = findViewById(R.id.eventBtn);

        // 2. Event Handler 객체 생성
//        MyEventHandler handler = new MyEventHandler(myTV);
        // 3. Event Source에 Handler 부착
//        myBtn.setOnClickListener(handler);
        myBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // myTV가 Member Variable이기 때문에 Error
                myTV.setText("버튼이 클릭됐다!!!");
            }
        });
    }
}

// 우리가 원하는건 일반 Class의 객체가 아니라
// 이벤트를 처리할 수 있는 특수한 능력을 가지고 있는 Listener 객체가 필요
// 다시 말하면, 우리가 작성하는 Class는 특수한 Interface를 구현한 Class가 되어야 한다.
// 특수한 Interface는 여러 개가 존재한다. (이벤트 종류에 따라서 여러개 존재)
//class MyEventHandler implements View.OnClickListener {
//
//    private TextView tv;
//
//    MyEventHandler() {
//
//    }
//
//    MyEventHandler(TextView tv) {
//        this.tv = tv;
//    }
//
//    @Override
//    public void onClick(View v) {
//        // 이벤트 처리코드를 작성
////        Log.i("MYEVENT", "Button Click");
//        // TextView의 내용을 변경
//        tv.setText("Button Clicked");
//    }
//}

// 열심히 Event Handler Class를 만들었는데,
// 실제 구현에서는 Anonymous inner class(익명의 이너클래스)를 이용하기 때문에
// 위와 같은 외부 Class는 사용하지 않는다!