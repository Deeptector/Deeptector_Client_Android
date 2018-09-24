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

    Intent start_main_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        startLoading();

    }

    private void startLoading() {

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
            public boolean handleMessage(Message qqwmessage) {
                return false;
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start_main_intent = new Intent(getBaseContext(), MainActivity.class);
                checkPermission(Permission.permission);
            }
        }, 1000);

    }

    // ±ÇÇÑ ÃŒÅ© (Api 23ÀÌ»ó)
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(String[] permission){
        requestPermissions(permission, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCdoe, String permissions[], int[] grantResults){
        switch(requestCdoe){
            case 100 :
                for(int i=0; i<grantResults.length; i++){
                    Log.e("grantResult"+ i, ": " + grantResults[i]);
                }

                // grantReuslts[13] -> 13ÀÎ ÀÌÀ¯ŽÂ Permission(Å¬·¡œº)¿¡Œ­ "¿ÜºÎ ÀúÀå" ±ÇÇÑÀÌ 13¹øÂ°¿¡ ÀÖ±â ¶§¹®ÀÓ
                if(grantResults.length>0 && grantResults[13] == PackageManager.PERMISSION_GRANTED){
                    startActivity(start_main_intent);
                    finish();
                    break;
                }
                else{
                    Toast.makeText(getApplicationContext(),"±ÇÇÑÀ» Çã¶ôÇØÁÖŒÅŸß ÇÕŽÏŽÙ", Toast.LENGTH_SHORT).show();
                    closeNow();
                    break;
                }
            default:
                finish();
                break;
        }
    }

    // ±ÇÇÑÀÌ °ÅºÎ µÇŸúÀ»¶§ ÁŸ·á
    private void closeNow(){
        // finsihAffinitiy()ŽÂ Root Activity±îÁö Ã£ŸÆ°¡Œ­ ŸËŸÆŒ­ ÁŸ·áœÃÄÑÁÖŽÂ ÇÔŒöÀÌŽÙ. (API-16ÀÌ»ó ¹öÀü¿¡Œ­žž ŸµŒöÀÖŽÙ)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            finishAffinity();
        }
        // API-16ÀÌÇÏ ¹öÀüÀÏ¶§ ÇöÀç ActivityžŠ ÁŸ·á
        else{
            finish();
        }
    }

}