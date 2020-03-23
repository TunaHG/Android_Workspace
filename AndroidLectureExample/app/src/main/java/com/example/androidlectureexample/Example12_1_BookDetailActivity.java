package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example12_1_BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example12_book_detail);

        Intent i = getIntent();
        final String title = (String)i.getExtras().get("bookTitle");

        final ImageView iv = findViewById(R.id.bookImage);
        final TextView titleTV = findViewById(R.id.bookTitle);
        final TextView authorTV = findViewById(R.id.bookAuthor);
        final TextView priceTV = findViewById(R.id.bookPrice);
        final TextView isbnTV = findViewById(R.id.bookISNB);

        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                String[] booklist = (String[])bundle.get("BOOKDETAIL");

                iv.setImageURI(Uri.parse(booklist[0]));
                titleTV.setText(booklist[1]);
                authorTV.setText(booklist[2]);
                priceTV.setText(booklist[3]);
                isbnTV.setText(booklist[4]);
            }
        };

        BookDetailRunnable runnable = new BookDetailRunnable(handler, title);
        Thread t = new Thread(runnable);
        t.start();
    }
}

class BookDetailRunnable implements Runnable {
    private Handler handler;
    private String title;

    BookDetailRunnable(Handler handler, String title) {
        this.handler = handler;
        this.title = title;
    }

    @Override
    public void run() {
        String url = "http://192.168.10.5:8080/bookSearch/searchDetail?title=" + title;

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Log.i("BookDetail", "서버로부터 전달된 Code: " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String readLine = "";
            StringBuffer responseTxt = new StringBuffer();
            while((readLine = br.readLine()) != null){
                responseTxt.append(readLine);
            }
            br.close();

            Log.i("BookDetail", "얻어온 내용은 : " + responseTxt.toString());

            JSONObject json = new JSONObject(responseTxt.toString());
            Log.i("BOOKDetail", "JSON?");
            String[] resultArr = {json.getString("img"), json.getString("title"), json.getString("author"),
            json.getString("price"), json.getString("bisbn")};


            Bundle bundle = new Bundle();
            bundle.putStringArray("BOOKDETAIL", resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);
        } catch (Exception e) {
            Log.i("BookDetail", e.toString());
        }
    }
}