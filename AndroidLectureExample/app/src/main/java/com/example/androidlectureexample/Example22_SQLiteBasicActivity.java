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
    private EditText tableNameET;
    private EditText empNameET, empAgeET, empMobileET;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example22_sqlite_basic);

        dbNameET = findViewById(R.id.dbNameET);
        tableNameET = findViewById(R.id.tableNameET);

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

        Button tableCreateBtn = findViewById(R.id.tableCreateBtn);

        tableCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Table Name
                String tableName = tableNameET.getText().toString();

                if(sqLiteDatabase == null) {
                    Log.i("DBTest", "Database부터 생성해야함");
                    return;
                }

                // 현재 Database에 대한 Reference가 존재
                // SQL을 이용해서 Database안에 Table을 생성
                String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, age INTEGER, mobile TEXT)";
                // 완성된 SQL을 어떤 Database에서 실행시킬건지 결정하여 실행
                sqLiteDatabase.execSQL(sql);
                Log.i("DBTest", "Table Created");
            }
        });

        empNameET = findViewById(R.id.empNameET);
        empAgeET = findViewById(R.id.empAgeET);
        empMobileET = findViewById(R.id.empMobileET);

        Button empInsertBtn = findViewById(R.id.empInsertBtn);
        empInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력된 사용자를 Table에 Insert
                String name = empNameET.getText().toString();
                int age = new Integer(empAgeET.getText().toString()).intValue();
                String mobile = empMobileET.getText().toString();

                if(sqLiteDatabase == null) {
                    Log.i("DBTest", "Database부터 생성해야함");
                    return;
                }

                String sql = "INSERT INTO emp(name, age, mobile) VALUES" +
                        "('" + name + "', " + age + ", '" + mobile + "')";

                sqLiteDatabase.execSQL(sql);
                Log.i("DBTest", "Record가 정상적으로 추가됨");
            }
        });
    }
}
