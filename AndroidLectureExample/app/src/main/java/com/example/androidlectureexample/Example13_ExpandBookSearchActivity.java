package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example13_ExpandBookSearchActivity extends AppCompatActivity {
    BookVO[] booklist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example13_expand_book_search);

        // 사용하는 Widget의 Reference 획득
        Button expandSearchBtn = findViewById(R.id.expandSearchBtn);
        final EditText expandSearchTitle = findViewById(R.id.expandSearchTitle);
        final ListView expandSearchList = findViewById(R.id.expandSearchList);

        // Web Application과 연동해야 하는 Android App
        // Network 기능을 이용해서 Web Application에서 데이터를 받아와야 함
        // Network연결은 Activity에서 작업하면 안되고 별도의 Thread를 만들어서 작업해야 함
        // Activity와 Thread가 데이터를 주고받기 위해서 Handler가 필요
        // Thread가 handler를 통해서 Activity에게 데이터를 전달(sendMessage)
        // Activity가 데이터를 받으면 Handler내의 handleMessage가 호출
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();

                booklist = (BookVO[])bundle.getSerializable("BOOKLIST");

                // ArrayAdapter를 만들어서 ListView에 책 제목만 출력
                // 책 제목에 대한 String[]이 필요하므로 BookVO[]에서 String[]를 뽑아내야한다.
                String[] titles = new String[booklist.length];
                // ArrayList 안의 VO들을 반복하면서 제목만 추출하여 String[]에 저장
                int i = 0;
                for(BookVO vo : booklist){
                    titles[i++] = vo.getBtitle();
                }

                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_list_item_1, titles);

                expandSearchList.setAdapter(adapter);
            }
        };

        // Button을 클릭하면 Thread를 생성해서 Network연결
        expandSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread를 생성
                MyBookInfoRunnable runnable = new MyBookInfoRunnable(handler, expandSearchTitle.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });

        expandSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("BookSearch", "선택한 위치는 : " + position);
                Log.i("BookSearch", "선택한 책 제목은 : " + booklist[position].getBtitle());
                Log.i("BookSearch", "선택한 책 ISBN은 : " + booklist[position].getBisbn());
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example14_DetailBookSearchActivity");
                i.setComponent(cname);
                i.putExtra("BookISBN", booklist[position].getBisbn());
                startActivity(i);
            }
        });
    }
}

class MyBookInfoRunnable implements Runnable {
    private Handler handler;
    private String keyword;

    MyBookInfoRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        // Thread가 시작되면 수행하는 Network 작업
        // Web Application을 호출해서 JSON결과를 가져와서 Activity에게 전달
        String url = "http://192.168.10.5:8080/bookSearch/bookinfo?keyword=" + keyword;
        try {
            // URL 객체를 생성 (Java의 URL Class)
            URL obj = new URL(url); // URL 객체의 Method를 사용하기 위해 객체로 만든다.
            // URL 객체를 이용해서 접속을 시도
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            // Web Application의 호출방식을 설정 (GET, POST)
            con.setRequestMethod("GET");
            // 정상적으로 접속이 되었는지 확인
            int responseCode = con.getResponseCode();
            Log.i("BookSearch", "서버로부터 전달된 Code: " + responseCode);


            // 연결성공 후 데이터를 받아오기 위한 통로 생성
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
            BookVO[] resultArr = mapper.readValue(responseTxt.toString(), BookVO[].class);
            // 얻어온 JSON문자열 데이터를 Java의 String 배열로 변환

            // 최종 결과 데이터를 Activity에게 전달 (UI Thread에게 전달하여 화면제어)
            // 1. Bundle에 전달할 데이터 부착
            Bundle bundle = new Bundle();
            bundle.putSerializable("BOOKLIST", resultArr);
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

@SuppressWarnings("serial")
class BookVO implements Serializable {
    private String bisbn;
    private String btitle;
    private String bdata;
    private int bpage;
    private int bprice;
    private String bauthor;
    private String btranslator;
    private String bsupplement;
    private String bpublisher;
    private String bimgurl;
    private String bimagbase64;

    public BookVO() {
        super();
    }

    public String getBisbn() {
        return bisbn;
    }

    public void setBisbn(String bisbn) {
        this.bisbn = bisbn;
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public String getBdata() {
        return bdata;
    }

    public void setBdata(String bdata) {
        this.bdata = bdata;
    }

    public int getBpage() {
        return bpage;
    }

    public void setBpage(int bpage) {
        this.bpage = bpage;
    }

    public int getBprice() {
        return bprice;
    }

    public void setBprice(int bprice) {
        this.bprice = bprice;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getBtranslator() {
        return btranslator;
    }

    public void setBtranslator(String btranslator) {
        this.btranslator = btranslator;
    }

    public String getBsupplement() {
        return bsupplement;
    }

    public void setBsupplement(String bsupplement) {
        this.bsupplement = bsupplement;
    }

    public String getBpublisher() {
        return bpublisher;
    }

    public void setBpublisher(String bpublisher) {
        this.bpublisher = bpublisher;
    }

    public String getBimgurl() {
        return bimgurl;
    }

    public void setBimgurl(String bimgurl) {
        this.bimgurl = bimgurl;
    }

    public String getBimagbase64() {
        return bimagbase64;
    }

    public void setBimagbase64(String bimagbase64) {
        this.bimagbase64 = bimagbase64;
    }
}
