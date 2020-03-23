package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example12_SimpleBookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example12_simple_book_search);

        // 검색버튼에 대한 Reference 획득
        Button searchBtn = findViewById(R.id.searchBtn);
        // 검색 입력창에 대한 Reference 획득
        final EditText searchTitle = findViewById(R.id.searchTitle);
        // 결과 ListView에 대한 Reference 획득
        final ListView searchList = findViewById(R.id.searchList);

        // Network연결(Web Application 호출)을 해야 하기 때문에
        // UI Thread(Activity)에서 이 작업을 하면 안된다.
        // => Thread로 해결해야 한다.
        // => Thread와 데이터를 주고받기 위해서 Handler를 이용

        // Handler 객체 생성
        @SuppressLint("HandlerLeak") // Memory Leak 방지
        final Handler handler = new Handler(){
            // Thread가 주는 책 데이터를 ListView에 전해주는 역할
            // Thread에 의해서 sendMessage가 호출되는데
            // sendMessage에 의해서 전달된 데이터를 처리하기 위해서
            // handleMessage를 Overriding하면서 instance를 생성
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // Thread가 보내준 데이터로 ListView를 채우는 코드
                // Thread가 보내준 Message에서 Bundle을 꺼낸다.
                Bundle bundle = msg.getData();
                // Bundle에서 key값을 이용하여 Data 추출
                String[] booklist = (String[])bundle.get("BOOKLIST");

                // ListView 사용은 Spinner 사용과 비슷
                // ListView에 데이터를 설정하려면 Adapter가 있어야함
                // Adapter에 데이터를 설정하고 ListView에 부착
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        booklist);
                // ListView에 Adapter 부착
                searchList.setAdapter(adapter);
            }
        };

        // Button에 대한 Event처리
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookSearchRunnable runnable = new BookSearchRunnable(handler, searchTitle.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }
}

// Thread를 생성하기 위해 Runnable Interface를 구현한 Class를 정의
class BookSearchRunnable implements Runnable {
    private Handler handler;
    private String keyword;

    BookSearchRunnable() { }

    BookSearchRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        // Web Application을 호출
        // 결과를 받아와서 그 결과를 예쁘게 만든 후 Handler를 통하여 Activity에게 전달

        // 1. 접속할 URL 준비
        String url = "http://192.168.10.5:8080/bookSearch/searchTitle?keyword=" + keyword;
        // 2. Java Network 기능은 Exception 처리를 기본으로 해야한다.
        try {
            // 3. URL 객체를 생성 (Java의 URL Class)
            URL obj = new URL(url); // URL 객체의 Method를 사용하기 위해 객체로 만든다.
            // 4. URL 객체를 이용해서 접속을 시도
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            // 5. Web Application의 호출방식을 설정 (GET, POST)
            con.setRequestMethod("GET");
            // 6. 정상적으로 접속이 되었는지 확인
            //    HTTP Protocol로 접속할 때 접속 결과에 대한 상태값
            //    202 : 접속성공, 403 : Forbidden, 404 : Not Found, 500 : Internal Server Error
            int responseCode = con.getResponseCode();
            Log.i("BookSearch", "서버로부터 전달된 Code: " + responseCode);
            // 7. 네트워크 보안설정
            //    Android App은 특정 기능을 사용하기 위해 보안을 설정해야 한다.
            //    Android 9(Pie)버전부터는 보안이 강화됨
            //      웹 프로토콜에 대한 기본 Protocol이 HTTP에서 HTTPS로 변경
            //    따라서 HTTP Protocol을 이용하는 경우 AndroidManifest.xml에 설정을 추가해야한다.

            // 8. 서버와의 연결객체를 이용해서 서버와의 데이터 통로를 열어야 한다. (Java Stream)
            //    이 연결통로를 통해 데이터를 읽어들일 수 있다.
            //    기본적인 연결통로(InputStreamReader)를 이용해서 조금 더 효율적인 연결통로(BufferedReader)로 다시 만들어 사용
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            // 서버가 보내주는 데이터를 읽어서 하나의 문자열로 만든다.
            String readLine = "";
            StringBuffer responseTxt = new StringBuffer();
            while((readLine = br.readLine()) != null){
                responseTxt.append(readLine);
            }
            br.close(); // 통로 사용이 끝났으니 해당 Resource 자원을 해제

            // 서버로부터 얻어온 문자열을 Log로 출력
            Log.i("BookSearch", "얻어온 내용은 : " + responseTxt.toString());

            // 가져온 데이터(문자열)를 자료구조화 시켜서 안드로이드 앱에 적용하는 방법
            // 일반적으로 서버쪽 Web Program은 XML이나 JSON으로 결과데이터를 제공
            // JSON parsing library를 이용하여 JSON을 Java의 자료구조로 변경
            // 가장 대표적인 Library중 하나인 JACKSON Library를 이용

            // JSON Library가 있어야 사용할 수 있으니 라이브러리를 설치
            // Gradle Scripts - build.gradle(Module: app) - dependencies

            // JACKSON library 사용
            ObjectMapper mapper = new ObjectMapper();
            String[] resultArr = mapper.readValue(responseTxt.toString(), String[].class);
            // 얻어온 JSON문자열 데이터를 Java의 String 배열로 변환

            // 최종 결과 데이터를 Activity에게 전달 (UI Thread에게 전달하여 화면제어)
            // 1. Bundle에 전달할 데이터 부착
            Bundle bundle = new Bundle();
            bundle.putStringArray("BOOKLIST", resultArr);
            // 2. Message를 만들어서 Bundle을 Message에 부착
            Message msg = new Message();
            msg.setData(bundle);
            // 3. Handler를 이용해서 Message를 Activity에게 전달
            handler.sendMessage(msg);
        } catch (Exception e){
            Log.i("BookSearch", e.toString());
        }
    }
}