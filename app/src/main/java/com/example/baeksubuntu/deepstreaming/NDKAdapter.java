package com.example.baeksubuntu.deepstreaming;

/**
 * Created by user on 12/12/16.
 */

public class NDKAdapter {

    static {
        System.loadLibrary("VideoPlayer");
    }

    public static native void setDataSource(String uri);
    public static native int play(Object surface);

    public NDKAdapter() {

    }
}
