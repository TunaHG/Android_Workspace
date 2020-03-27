package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Map;

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

    // Inner Class
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

                // 정상적으로 처리가 되었다면, 접속성공
                // 접속이 성공하면 결과 데이터를 JSON으로 받아와야함
                // 데이터 연결통로(Stream)으로 데이터를 읽어와야 함
                // 기본적은 Stream을 먼저 열고, 이를 더 좋은 통로로 변경해서 사용
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer sb = new StringBuffer();

                // 반복적으로 서버가 보내준 데이터를 읽는다.
                String line;
                while( (line = br.readLine()) != null ) {
                    sb.append(line);
                }

                // 제대로 들어오는지 확인
                Log.i("KAKAO", sb.toString());
                // documents : [ {책 1권}, {책 1권}, ... ] 로 구성되어있는것 확인

                // JSON을 처리해서 documents라고 되어있는 key값에 대해 value값을 객체화해서 가져온다
                // JACKSON Library를 이용해서 처리
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(sb.toString(),
                        new TypeReference<Map<String, Object>>() {});
                // JSON 문자열을 (key, value)형태로 읽어들이려 할때,
                // Map<String, Object> 형태로 만들기 위해서 TypeReference를 사용
                // TypeReference<>에서 Generic형태에 원하는 형태인 Map<String, Object>를 넣는것.
                // 원래는 Overriding하는 Method가 존재하나, 작성하지 않으므로 삭제
                // 이는 JACKSON Library에서 지원하는 기능이다.

                Object jsonObject = map.get("documents");
                // jsonObject => [ {책 1권}, {책 1권}, ... ] 가 된다.

                String jsonString = mapper.writeValueAsString(jsonObject);
                // jsonObject에 들어있는 값을 String으로 변환하여 jsonString에 저장

                // ArrayList안에 책 객체가 들어있는 형태로 변경
                ArrayList<KAKAOBookVO> searchBooks = mapper.readValue(jsonString,
                        new TypeReference<ArrayList<KAKAOBookVO>>() { });

                // 출력해서 제대로 나누어졌는지 확인
                for(KAKAOBookVO vo : searchBooks){
                    Log.i("KAKAO", vo.getTitle());
                }

                // 책 제목만 들어있는 ArrayList를 만들어서 Activity에게 전달
                ArrayList<String> resultData = new ArrayList<>();
                for(KAKAOBookVO vo : searchBooks){
                    resultData.add(vo.getTitle());
                }

                Intent resultIntent = new Intent(getApplicationContext(),
                        Example18_KakaoBookSearchActivity.class);
                resultIntent.putExtra("BOOKRESULT", resultData);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(resultIntent);
            } catch (Exception e){
                Log.i("KAKAO", e.toString());
            }
        }
    }
}


// 책 객체를 의미하는 VO Class정의
class KAKAOBookVO {
    private ArrayList<String> authors;
    private String contents;
    private String datetime;
    private String isbn;
    private String price; // int지만, String으로 처리하는것이 편함
    private String publisher;
    private String sale_price;
    private String status;
    private String thumbnail;
    private String title;
    private ArrayList<String> translators;
    private String url;

    public KAKAOBookVO() { }

    public KAKAOBookVO(ArrayList<String> authors, String contents, String datetime, String isbn, String price, String publisher, String sale_price, String status, String thumbnail, String title, ArrayList<String> translators, String url) {
        this.authors = authors;
        this.contents = contents;
        this.datetime = datetime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.sale_price = sale_price;
        this.status = status;
        this.thumbnail = thumbnail;
        this.title = title;
        this.translators = translators;
        this.url = url;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}