package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/*
 * 1. 우리 App이 휴대단말로 온 문자메시지를 처리
 *    문자메시지 처리는 상당히 개인적인 정보 => 보안처리가 필요
 *    - AndroidManifest.xml파일에 기본 보안에 대한 설정이 필요
 *
 * 2. Activity가 실행되면 보안설정 진행
 *    마쉬멜로우 버전이후 부터는 강화된 보안정책을 따라야함
 *    보안처리코드가 나와야함
 *
 * 3. Broadcast Receiver Component를 생성
 *    코드상에서 생성하는게 아니라 AndroidManifest.xml에 등록하여 생성
 *    외부 Component로 따로 생성되기 때문에 AndroidManifest.xml 에 자동등록
 *    => 생성된 후 intent-filter를 이용해서 어떤 Broadcast Signal을 수신할지 설정
 *    SMS문자가 오면 Broadcast Receiver가 이 문자를 받아서 Activity에 전달
 *
 * 
 */
public class Example20_BRSMSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example20_br_sms);

        // 1. 사용자의 단말기 OS(Android Version)가 마쉬멜로우(6) 이후의 버전인지 check
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 사용하는 기기의 버전이 M 이상이면
            // 사용자 권한 중 SMS 받기 권한이 설정되어 있는지 Check
            int permissionResult = ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.RECEIVE_SMS);
            if(permissionResult == PackageManager.PERMISSION_DENIED) {
                // 권한이 없으면
                // 1. App을 처음 실행해서 물어본적이 없는 경우
                // 2. 권한 허용에 대해서 사용자에게 물어봤지만 사용자가 거절한 경우
                if(shouldShowRequestPermissionRationale(
                        Manifest.permission.RECEIVE_SMS)) {
                    // true => 권한을 거부한적이 있는 경우
                    // 일반적으로 dialog같은걸 이용해서 다시 요청
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Example20_BRSMSActivity.this);
                    dialog.setTitle("Need Permission");
                    dialog.setMessage("Need Receive SMS Permssion, Grant?");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 100);
                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 권한설정을 하지 않는다는 의미이므로
                            // 아무런 작업도 할 필요없음
                        }
                    });
                } else {
                    // false => 한번도 물어본적이 없는 경우
                    requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 100);
                }
            } else {
                // 권한이 있으면
                Log.i("SMSTest", "보안설정 통과");
            }
        } else {
            // 사용하는 기기의 버전이 M 미만이면
            Log.i("SMSTest", "보안설정 통과");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 사용자가 권한을 설정하게 되면 이 부분이 마지막으로 호출됨
        // 사용자가 권한설정을 하거나 그렇지 않은 두가지 경우 모두 이 Callback Method가 호출
        if(requestCode == 100) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 권한 허용을 눌렀을 경우
                Log.i("SMSTest", "보안설정 통과");
            }
        }
    }
}
