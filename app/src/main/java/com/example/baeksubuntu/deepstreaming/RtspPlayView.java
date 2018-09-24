package com.example.baeksubuntu.deepstreaming;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 안탄 on 2018-09-05.
 */

public class RtspPlayView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "RtspPlayView";
    private SurfaceHolder mHolder;
    private NDKAdapter mPlayerNdkAdapter;

    private String lib_uri;

    public RtspPlayView(Context context, String uri) {
        super(context);

        this.lib_uri = uri;

        mHolder = getHolder();
        mHolder.addCallback(this);

        Thread a = new Thread(){
            @Override
            public void run(){
                try {
                    // JNI에 있는 라이브러리를 통해서 작업
                    mPlayerNdkAdapter = new NDKAdapter(getContext());
                    mPlayerNdkAdapter.setDataSource(lib_uri);

                }catch (Exception e){
                    Log.e("NoError", "");
                    return;
                }
            }
        };

        a.start();
    }

    /*
        SurfaceHolder.Callback Implemention
    */



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        // 비디오 플레이는 시간이 많이 걸리는 작업이므로, 작업도중 메인 UI 쓰레드를 차단하지 않기 위해서, 별도의 쓰레드를 통해서 재생한다.
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPlayerNdkAdapter.play(mHolder.getSurface());
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int j, int k){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){

    }

}
