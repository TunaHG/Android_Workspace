package com.example.androidlectureexample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class Example24Sub_PersonContentProvider extends ContentProvider {
    // URI 형식(Content Provider를 찾기위한 특수한 문자열)
    // content://Authority/BASE_PATH(Table Name)/숫자(Record Number)
    // ex) content://com.exam.person.provider/person/1

    private SQLiteDatabase database;

    public Example24Sub_PersonContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("DBTest", "CP insert() Callback");

        // Database Table에 insert하는 방법중 하나, SQL문장을 이용하지 않음
        // ContentValues에 key, value형태로 입력할 데이터를 묘사
        database.insert("person", null, values);

        return uri;
    }

    @Override
    public boolean onCreate() {
        Log.i("DBTest", "CP onCreate() Callback");
        // Database 생성 및 Table생성에 관한 코드
        // Database Reference를 획득하는 코드
        PersonDBHelper helper = new PersonDBHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
