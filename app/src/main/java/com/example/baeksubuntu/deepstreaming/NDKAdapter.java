package com.example.baeksubuntu.deepstreaming;


import android.content.Context;
import android.util.Log;

/**
 * Created by 안탄 on 2018-09-05.
 */

public class NDKAdapter {

    static Context context;



    static {

        System.loadLibrary("VideoPlayer");

    }

    public static native void setDataSource(String uri);
    public static native int play(Object surface);

    public NDKAdapter(Context getContext){
        this.context = getContext;
    }

}

