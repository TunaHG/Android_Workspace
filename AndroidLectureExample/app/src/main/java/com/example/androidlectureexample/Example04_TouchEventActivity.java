package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class Example04_TouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example04_touch_event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Toast Message를 이용해본다.
        Toast.makeText(this, "소리없는 아우성", Toast.LENGTH_SHORT).show();
        // Context는 Interface지만
        // 해당 interface를 Activity가 구현하고 있기 때문에 is a관계에 의해서 우리 Activity는 Context가 된다.
        Log.i("MYTEST", "Touch!");

        return super.onTouchEvent(event);
    }
}
