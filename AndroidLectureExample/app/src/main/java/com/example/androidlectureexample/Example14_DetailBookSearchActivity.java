package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example14_DetailBookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example14_detail_book_search);

        Intent i = getIntent();
        String bisbn = (String)i.getExtras().get("BookISBN");

        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();

                BookVO vo = (BookVO)bundle.getSerializable("BookDetail");
                byte[] img = bundle.getByteArray("Bitmap");
                Log.i("BookDetail", "img의 Byte[] : " + img.toString());
                Bitmap b = BitmapFactory.decodeByteArray(img, 0, img.length);
                Log.i("BookDetail", "Bitmap");

                ImageView iv = findViewById(R.id.detailBookIV);

                iv.setImageBitmap(b);

                TextView isbnTV = findViewById(R.id.detailBookISBN);
                TextView titleTV = findViewById(R.id.detailBookTitle);
                TextView authorTV = findViewById(R.id.detailBookAuthor);
                TextView priceTV = findViewById(R.id.detailBookPrice);

                isbnTV.setText(vo.getBisbn());
                titleTV.setText(vo.getBtitle());
                authorTV.setText(vo.getBauthor());
                priceTV.setText(String.valueOf(vo.getBprice()));
            }
        };

        MyBookDetailRunnable runnable = new MyBookDetailRunnable(handler, bisbn);
        Thread t = new Thread(runnable);
        t.start();
    }
}

class MyBookDetailRunnable implements Runnable {
    private Handler handler;
    private String bisbn;

    MyBookDetailRunnable(Handler handler, String bisbn) {
        this.handler = handler;
        this.bisbn = bisbn;
    }

    @Override
    public void run() {
        String url = "http://192.168.10.5:8080/bookSearch/bookDetail?bisbn=" + bisbn;
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

            ObjectMapper mapper = new ObjectMapper();
            final BookVO result = mapper.readValue(responseTxt.toString(), BookVO.class);

            String bimgurl = result.getBimgurl();

            URL imgurl = new URL(bimgurl);
            HttpURLConnection conn = (HttpURLConnection)imgurl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);

            Bundle bundle = new Bundle();
            bundle.putSerializable("BookDetail", result);
            bundle.putByteArray("Bitmap", bs.toByteArray());

            Message msg = new Message();
            msg.setData(bundle);

            handler.sendMessage(msg);
        } catch (Exception e) {
            Log.i("BookDetail", e.toString());
        }
    }
}
