package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Example17_ServiceDataTransferActivity extends AppCompatActivity {
    TextView dataFromServiceTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example17_service_data_transfer);

        // 사용할 Component의 Reference를 획득
        dataFromServiceTV = findViewById(R.id.dataFromServiceTV);
        final EditText datatToServiceET = findViewById(R.id.dataToServiceET);
        Button dataToServiceBtn = findViewById(R.id.dataToServiceBtn);

        // Button에 대한 Click Event 처리
        // Anonymous Inner Class를 이용하여 Event처리
        dataToServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText 안에 사용자가 입력한 데이터를 가지고 Service를 시작
                Intent intent = new Intent(getApplicationContext(),
                        Example17Sub_DataTransferService.class);
                // Service에게 데이터를 전달하려면 intent를 이용해서 전달
                intent.putExtra("DATA", datatToServiceET.getText().toString());
                startService(intent);
            }
        });
    }

    // Service로부터 Intent가 도착하면 Method가 호출
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 여기서 Service가 보내준 결과데이터를 받아서 화면처리
        String result = intent.getExtras().getString("RESULT");
        // 추출한 결과를 TextView에 세팅
        dataFromServiceTV.setText(result);
    }
}
