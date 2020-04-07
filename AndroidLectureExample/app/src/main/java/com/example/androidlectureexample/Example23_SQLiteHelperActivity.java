package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// Database Open Helper를 이용하기 위한 Class
class MyDBHelper extends SQLiteOpenHelper {
    // Default Constructor
    // 직접 코드를 명시하지 않으면 Java Compiler가 삽입
    MyDBHelper(Context context, String dbName, int version) {
        // 상위 Class의 생성자
        // SQLiteOpenHelper는 Default Constructor가 없음.
        // super(); => Error
        super(context, dbName, null, version);

        // 만약 dbName이라는 DB가 없다면, onCreate() 호출 => onOpen() 호출
        //   onCreate()로 DB를 생성할 때 Version정보를 같이 명시
        // dbName이라는 DB가 있다면, opOpen() 호출
        //   Version값이 만들어진 DB의 Version값과 다르면 onUpgrade() 호출
        // onUpgrade()
        // => 기존 데이터베이스의 스키마를 변경하는 작업 진행
        //    초창기에 앱을 만들어서 배포할 때 DB 스키마를 생성
        //    앱을 업그레이드해서 다시 배포할 때 DB 스키마를 다시 생성하기위해 사용
        //    이전 DB를 drop시키고 새로운 DB를 만드는 작업을 진행
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // 데이터베이스가 Open될 때 자동으로 호출
        Log.i("DBTest", "onOpen() Callback");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 존재하지 않아서 생성할 때 호출
        Log.i("DBTest", "onCreate() Callback");
        // 데이터베이스 생성하는 코드
        String sql = "CREATE TABLE IF NOT EXISTS " +
                "person( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, age INTEGER, mobile TEXT)";

        db.execSQL(sql);
        Log.i("DBTest", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 버전이 바뀌어서 데이터베이스를 수정할 때 호출
        Log.i("DBTest", "onUpgrade() Callback");
    }
}
public class Example23_SQLiteHelperActivity extends AppCompatActivity {
    private TextView helperResultTV;
    private EditText helperDBNameET;
    private EditText helperTableNameET;
    private EditText helperEmpNameET, helperEmpAgeET, helperEmpMobileET;

    // 사용할 DB에 대한 Reference
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example23_sqlite_helper);

        helperDBNameET = findViewById(R.id.helperDBNameET);

        Button helperDBCreateBtn = findViewById(R.id.helperDBCreateBtn);
        helperDBCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 Database 이름을 가져옴
                String dbName = helperDBNameET.getText().toString();

                // DB 생성후 Open
                // 만약 DB가 존재한다면, Open

                // Helper Class를 이용하여 DB생성 및 Open
                MyDBHelper helper = new MyDBHelper(Example23_SQLiteHelperActivity.this,
                        dbName, 1);

                // Helper를 통해서 Database Reference를 획득
                database = helper.getWritableDatabase();

                // 1. 새로운 DB를 생성
                // Helper가 생성되며 DB가 생성되고 Open됨
                // onCreate() -> onOpen()
            }
        });
    }
}
