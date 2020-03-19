package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Example07_DataFromActivity extends AppCompatActivity {
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example07_data_from);

        // Spinner 안에 표현될 데이터를 생성
        // 여기서는 Spinner 데이터가 문자열이라고 가정
        final ArrayList<String> list = new ArrayList<String>();
        list.add("포도");
        list.add("딸기");
        list.add("바나나");
        list.add("사과");

        // Spinner의 Reference 획득
        Spinner spinner = findViewById(R.id.mySpinner);

        // Adapter 생성(Adapter는 종류가 다양하다)
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, list);
        // Data를 어떻게 보여줄 것인지를 가지고 있다.

        // Adapter를 Spinner에게 부착
        // Data를 Dropdown형식으로 표현하는 Spinner가 됨
        spinner.setAdapter(adapter);

        // Widget에서 Spinner 중 하나를 클릭하면 Item Select Event가 발생함
        // Spinner의 Event처리가 필요
        // setOnItem 이후의 Click은 누르는 순간, LongClick은 길게 누르는 순간, Selected는 떼는 순간을 의미
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // position은 여러 개의 item중에 내가 선택한 item이 몇 번째인지 의미
                result = list.get(position);
                Log.i("SELECTED", result + " Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
