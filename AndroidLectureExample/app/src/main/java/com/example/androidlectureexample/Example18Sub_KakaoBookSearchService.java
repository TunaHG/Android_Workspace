package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class Example18Sub_KakaoBookSearchService extends Service {
    public Example18Sub_KakaoBookSearchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Service에서 사용하는 3개의 Method Overriding
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Activity로 부터 전달된 intent를 이용해서 keyword를 얻음
        String keyword = intent.getExtras().getString("KEYWORD");

        // Network 연결을 통해 OPEN API를 호출하는 시간이 걸리는 작업을 수행
        // Thread를 이용해서 처리
        KaKaoBookSearchRunnable runnable = new KaKaoBookSearchRunnable(keyword);
        Thread thread = new Thread(runnable);
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

class KaKaoBookSearchRunnable implements Runnable {
    private String keyword;

    KaKaoBookSearchRunnable() {
    }

    KaKaoBookSearchRunnable(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void run() {
        // Network 접속을 통해 OPEN API를 호출
        // KAKAO Developer 사이트에서 OPEN API 호출방식에 대해 알아봐야 함
        String url = "https://dapi.kakao.com/v3/search/book?target=title";
        url += ("&query=" + keyword);

        // 주소를 완성했으면 이 주소를 이용해서 Network연결
        // Java에서 Network 연결은 예외상황이 발생할 여지가 있음 => Exception Handling해야함
        try {
            // 1. HTTP접속을 위해 URL객체 생성
            URL obj = new URL(url);
            // 2. Open URL Connection
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            // 3. 연결에 대한 방식 설정
            // 호출방식(GET, POST), 인증에대한 처리
            con.setRequestMethod("GET"); // KAKAO API에서 GET이라고 명시함
            con.setRequestProperty("Authorization", "KakaoAK 729499a1427f59d0c93717569b90ead1");

            // 접속성공
        } catch (Exception e){
            Log.i("KAKAO", e.toString());
        }
    }
}