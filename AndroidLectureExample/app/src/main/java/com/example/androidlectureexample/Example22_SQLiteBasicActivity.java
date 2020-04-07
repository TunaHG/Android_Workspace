package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Example22_SQLiteBasicActivity extends AppCompatActivity {
    private EditText dbNameET;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example22_sqlite_basic);

        dbNameET = findViewById(R.id.dbNameET);

        Button dbCreateBtn = findViewById(R.id.dbCreateBtn);

        dbCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Database 생성
                // "dbName.db"라는 파일로 생성
                String dbName = dbNameET.getText().toString();

                // MODE_PRIVATE : 0값, 일반적인 형태(읽고쓰기가 가능한)의 DB를 생성하거나 Open
                sqLiteDatabase = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                Log.i("DBTest", "Database Created");
            }
        });
    }
}
