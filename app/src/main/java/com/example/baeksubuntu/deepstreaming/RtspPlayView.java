package com.example.baeksubuntu.deepstreaming;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RtspPlayView extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = "RtspPlayView";

    private SurfaceHolder mHolder;
    private NDKAdapter mPlayerNdkAdapter;

    public RtspPlayView(Context context, String uri) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);

        // JNI에 있는 라이브러리를 통해서 작업한다.
        mPlayerNdkAdapter = new NDKAdapter();
        mPlayerNdkAdapter.setDataSource(uri);
    }


    /** -----------------------------
     * SurfaceHolder.Callback Implementation
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // 비디오 플레이는 시간이 많이 걸리는 작업이여서 작업도중 메인 UI 쓰레드를 차단하지 않기 위해서, 별도의 쓰레드를 통해서 재생한다.
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPlayerNdkAdapter.play(mHolder.getSurface());
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }



    public Bitmap drawBitmap() {

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /*public static Bitmap drawBitmap(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if(view instanceof SurfaceView){
            SurfaceView surfaceView = (SurfaceView)view;
            surfaceView.setZOrderOnTop(true);
            surfaceView.draw(canvas);
            surfaceView.setZOrderOnTop(false);
            return bitmap;
        }else{
            view.draw(canvas);
            view.drawbi
            return bitmap;
        }

    }*/


}