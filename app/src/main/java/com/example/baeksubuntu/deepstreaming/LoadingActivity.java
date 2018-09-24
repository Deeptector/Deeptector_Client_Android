package com.example.baeksubuntu.deepstreaming;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;

public class LoadingActivity extends FragmentActivity {

    private Intent start_main_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);


        startLoading();

    }

    private void startLoading() {

        // 디렉터리(폴더) 확인 -> 없다면 Deectector 폴더를 새로 만들어 줌
        String path = Environment.getExternalStorageState();
        if(path.equals(Environment.MEDIA_MOUNTED)){
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deeptector";
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs();
            }
        }


        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                return false;
            }
        });

        // 핸들러를 통해 스플래쉬(애니메이션) 효과를 만듦 -> 1초가 지나면 MainActivity 시작
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start_main_intent = new Intent(getBaseContext(), MainActivity.class);
                checkPermission(Permission.permission);
            }
        }, 1300);

    }

    // 권한 체크 (Api 23이상)
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(String[] permission){
        requestPermissions(permission, 100);
    }

    // 권한 체크 여부를 리퀘스트 받아 처리하는 곳
    @Override
    public void onRequestPermissionsResult(int requestCdoe, String permissions[], int[] grantResults){
        switch(requestCdoe){
            case 100 :
                for(int i=0; i<grantResults.length; i++){
                    Log.e("grantResult"+ i, ": " + grantResults[i]);
                }

                // grantReuslts[13] -> 13인 이유는 Permission(클래스)에서 "외부 저장" 권한이 13번째에 있기 때문임
                if(grantResults.length>0 && grantResults[13] == PackageManager.PERMISSION_GRANTED){
                    startActivity(start_main_intent);
                    finish();
                    break;
                }
                else{
                    Toast.makeText(getApplicationContext(),"권한을 허락해주셔야 합니다", Toast.LENGTH_SHORT).show();
                    // 권한이 거부되었다면 종료
                    closeNow();
                    break;
                }
            default:
                finish();
                break;
        }
    }

    // 권한이 거부 되었을때 종료
    private void closeNow(){
        // finsihAffinitiy()는 Root Activity까지 찾아가서 알아서 종료시켜주는 함수이다. (API-16이상 버전에서만 쓸수있다)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            finishAffinity();

        }
        // API-16이하 버전일때 현재 Activity를 종료
        else{
            finish();
        }
    }

}
