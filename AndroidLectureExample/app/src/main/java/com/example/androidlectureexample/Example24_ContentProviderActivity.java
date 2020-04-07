package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*
 * Content Provider
 * => 하나의 App에서 관리하는 Data(SQLite DB)를 다른 App이 접근할 수 있게 해주는 기능
 *    Android 구성요소중의 하나로 Android System에 의해서 관리
 *    AndroidManifest.xml 파일에 등록해서 사용
 *
 * CP가 필요한 이유는 보안규정때문,
 * Android App은 오직 자신의 App안에서 생성한 Data만 사용할 수 있음
 * 다른 App이 가지고 있는 Data의 접근권한이 없음
 * CP를 이용하면 CP가 CRUD를 기반으로 하고 있기 때문에
 * 일반적으로 Database에 접근하는 방식을 이용하여 다른 App의 Data에 접근할 수 있음
 *
 * 1. Database(SQLite)를 이용하기 때문에 SQLiteOpenHelper Class를 이용해서 DB 이용
 *
 */

// SQLiteOpenHelper 이용
// PersonDBHelper 객체를 만들면 DB가 생성되고 Table이 생성됨
class PersonDBHelper extends SQLiteOpenHelper {
    PersonDBHelper(Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    // 초기에 데이터베이스가 생성되는 시점에 Table도 같이 생성
        String sql = "CREATE TABLE IF NOT EXISTS " +
            "person( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, age INTEGER, mobile TEXT)";
        db.execSQL(sql);
        Log.i("DBTest", "Table Created");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 일단 제외
    }
}

public class Example24_ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example24_content_provider);

        Button CPEmpInsertBtn = findViewById(R.id.CPEmpInsertBtn);
        CPEmpInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 원래는 입력된 데이터를 가지고와서 CP를 이용해서 입력처리
                // CP를 찾아야함
                String uriString = "content://com.exam.person.provider/person";

                Uri uri = new Uri.Builder().build().parse(uriString);

                // HashMap형태로 데이터베이스에 입력할 데이터를 저장
                ContentValues values = new ContentValues();
                values.put("name", "홍길동");
                values.put("age", 20);
                values.put("mobile", "010-1111-5555");

                getContentResolver().insert(uri, values);
                Log.i("DBTest", "Input Data Success");
            }
        });
    }
}
