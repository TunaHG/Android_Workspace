package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    // Activity의 Life Cycle
    // Activity는 사용자에 의해서 Event가 발생되면 그 상태가 변한다.
    // 그에 따라 Callback Method가 호출되는데, 그 Callback에 대해서 알아둬야 한다.


    //  1. Activity는 Class상태로 존재한다.
    //  2. Activity가 화면에 나타나라면 객체화가 되어야 한다. (instance화)
    //  3. onCreate() Method가 Callback된다.
    //     => 화면구성을 주로 한다.
    //  4. onStart() Method가 Callback된다.
    //     => Activity의 초기화 작업들을 한다.
    //  5. Activity가 foreground로 나타나면서 사용자와 Interaction이 가능
    //  6. onResume() Method가 Callback된다.
    //  7. Activity의 상태가 Running 상태가 된다.
    //  8. Activity의 일부분이 보이지 않는 상태가 될 수 있다. (pause 상태)
    //     => 예를 들어, 여러 앱을 살펴보는 기능을 사용한 경우
    //  9. Pause 상태가 되면 onPause() Method가 Callback 된다.
    // 10. Activity의 전체가 완전히 가려져서 보이지 않는 상태가 된다. (Stop 상태)
    // 11. Stop 상태가 되면 onStop() Method가 Callback 된다.
    // 12. Stop 상태에서 다시 Running 상태가 될 수 있다.
    // 13. onRestart() -> onStart() -> onResume() 순서로 다시 Callback 된다.
    // 14. 사용하고 있는 Activity를 종료하게 되면 Killed 상태가 된다.
    // 15. 진입하기 전에 onDestroy() Method가 Callback 된다.


    // onCreate() 특정 시점이 되면 Android System에 의해서 자동적으로 호출됨
    //            ==> Class의 instance가 생성될 때
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 상위 클래스의 Method 호출
        super.onCreate(savedInstanceState);

        // Activity의 화면을 구성하는 방식은 크게 2가지 방식
        // 1. Java Code로 Widget Component를 생성해서 화면에 붙이는 방식
        // 2. XML 파일을 이용해서 화면구성을 xml을 이용해서 처리
        //    xml을 이용하는 방식은 표현(화면구성-UI)과 구현(로직처리)을 분리
        //    유지측면에서 유리하여 이 방식을 사용한다.

        // res 폴더 내의 4가지 폴더에 대해 알아본다.
        // 1. drawable : Application 내에서 사용하는 그림 파일을 저장하는 위치
        // 2. layout : Activity에서 사용할 화면구성을 위한 XML파일을 저장하는 위치
        // 3. mipmap : Launcher Icon과 같은 이미지 자원을 저장하는 위치
        // 4. values : 문자열이나 컬러와 같은 다양한 자원에 대한 정보를 xml파일로 저장하는 위치

        // R.java는 Android에 의해서 자동으로 생성되는 Class
        // activity_main.xml 파일을 이용하여 activity의 View를 설정하라는 Method
        // xml파일의 이름은 무조건 소문자로 해야한다.
        setContentView(R.layout.activity_main);

        // 그 다음에 알아봐야하는 내용은 xml파일의 내용
        // 어떻게 화면을 구성하면 되는지? => 너무 많다.

        // Logcat을 이용해서 Log를 출력
        // IDE 아래의 Tab중 Logcat이 존재
        Log.i("MYTEST", "이것은 소리없는 아우성");

        // View와 ViewGroup
        // View는 통상적으로 눈에 보이는 Component
        // => Button, TextView(label), ImageView(그림), ...
        // ViewGroup은 이런 View의 크기와 위치를 조절해서 설정해주는 것
        // 대표적인 ViewGroup은 Layout
    }

    // Alt + insert => Override Methods (Ctrl + O) => onStart() 선택
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MYTEST", "onStart() 호출");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MYTEST", "onResum() 호출");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MYTEST", "onPause() 호출");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MYTEST", "onStop() 호출");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MYTEST", "onRestart() 호출");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MYTEST", "onDestroy() 호출");
    }
}
