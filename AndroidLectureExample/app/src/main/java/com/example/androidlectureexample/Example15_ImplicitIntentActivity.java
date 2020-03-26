package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Example15_ImplicitIntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example15_implicit_intent);

        Button implicitIntentBtn = findViewById(R.id.implicitIntentBtn);
        implicitIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Implicit", "Button Click");
                // Button을 클릭했을 때 새로운 Activity를 실행(묵시적 Intent 이용)
                // Implicit Intent(묵시적 인텐트)
                // Sub로 생성한 Activity 호출
                Intent i = new Intent();
                i.setAction("MY_ACTION");
                i.addCategory("INTENT_TEST");
                i.putExtra("Send Data", "Hello!");
                startActivity(i);
            }
        });

        Button dialBtn = findViewById(R.id.dialBtn);
        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전화걸기 Activity를 호출하는 2가지 방법
                // 1. 클래스명을 사용하여 호출(Explicit Intent)
                //    => 클래스명을 모르기 때문에 사용불가능
                // 2. 묵시적 Intent를 이용해서 알려져있는 Action을 사용해서 호출
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                // Intent.ACTION_DIAL => 전화가 걸리는 것이 아닌, 전화번호를 입력하는 과정까지 진행
                i.setData(Uri.parse("tel:01096117555"));
                startActivity(i);
            }
        });

        Button browserBtn = findViewById(R.id.browserBtn);
        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 특정 URL을 이용해서 Browser를 실행
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.naver.com"));
                startActivity(i);
            }
        });

        /*
        먼저 전화 걸기 기능을 사용하려면 AndroidManifest.xml 파일에서 권한설정을 해야함
        안드로이드 6.0(마쉬멜로우) 이상에서는 Manifest파일에 기술하는거 이외에 사용자에게 권한을 요청한다.
        권한자체가 크게 2가지로 분류
        - 일반 권한(Normal Permission)
        - 위험 권한(Dangerous Permission)
          개인정보에 해당하는 데이터(위치, 전화걸기, 카메라, 마이크, 문자, 일정, 주소록, 센서 등)
        특정 앱을 사용할 때 앱이 사용자에게 권한을 요구하고 사용자가 권한을 허용하면 기능을 이용할 수 있다.
        한번 허용한 권한은 앱이 계속 사용할 수 있다.
        해제하기 위해선 설정 > 애플리케이션 > 특정 앱 > 권한에서 해제해야한다.
        */
        Button callBtn = findViewById(R.id.callBtn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// 사용자가 사용하는 Android 버전이 6.0(M - Mashmallow)이상인지 확인
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    // 사용자의 Android Version이 6.0 이상인 경우
                    // 사용자 권한 중 전화 걸기 권한이 설정되어 있는지 확인
                    int permissionResult =
                            ActivityCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.CALL_PHONE);
                    // 권한을 이미 허용했는지 그렇지 않은지를 비교하여 다르게 처리
                    if(permissionResult == PackageManager.PERMISSION_DENIED){
                        // 권한이 없는 경우
                        // 권한 설정을 거부한 적이 있는지 없는지에 대해 확인
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                            // 권한을 거부한 적이 있는 경우
                            // 임의로 App의 권한을 끄거나 혹은 권한 요청 화면에서 거절을 클릭했을 경우
                            // 경고창을 띄워 정말로 거절할 것인지 물어본다.
                            AlertDialog.Builder dialog =
                                    new AlertDialog.Builder(Example15_ImplicitIntentActivity.this);
                            dialog.setTitle("권한 요청에 대한 Dialog");
                            dialog.setMessage("전화걸기 기능 필요");
                            dialog.setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                                    1000);
                                        }
                                    });
                            dialog.setNegativeButton("아니오",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 할일이 없다
                                        }
                                    });
                            dialog.show();
                        } else {
                            // 권한을 거부한 적이 없는 경우 (처음 접속한 경우)
                            // 권한 설정창이 전화, 카메라 등등 여러개를 한번에 받도록 String[]로 가능하다.
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                    1000);
                            // 두 번째 인자인 requestCode는 Permission의 결과를 확인하기 위해 작성
                        }
                    } else {
                        // 권한이 있는 경우
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:01096117555"));
                        startActivity(i);
                    }
                } else {
                    // 사용자의 Android Version이 6.0 미만인 경우
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:01096117555"));
                    startActivity(i);
                }
            }
        });
    } // end of onCreate()

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // onCreate() 내에서 수행한 권한요청에 대한 응답인지를 확인
        if(requestCode == 1000){
            // 결과가 grantResults에 담긴다. int[] 타입이며 권한을 여러 개 받을 수 있으니 배열이다.
            // 권한 요청의 응답개수가 1개 이상이고,
            // 지금 상황에서 전화 걸기 권한 1개만 요청했으므로 첫번째 배열에 결과가 담겨서 도착한다.
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허용을 눌렀으면 Implicit Intent사용
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:01096117555"));
                startActivity(i);
            }
        }
    }
}
