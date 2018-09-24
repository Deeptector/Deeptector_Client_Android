package com.example.baeksubuntu.deepstreaming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class Fragment_Main extends Fragment {

    private WebView fragment_main_webview;
    private View current_main_view;

    public Fragment_Main() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // 생성자 다음으로 불리는 데이터 처리하는곳
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        current_main_view = inflater.inflate(R.layout.fragment_main, container, false);
        fragment_main_webview = (Custom_WebView)current_main_view.findViewById(R.id.fragment_main_webview_id);

        return current_main_view;
    }

    // 최종적으로 액티비티에 붙여주는 곳
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment_main_webview.loadUrl("http://192.168.0.23:3000");

    }


}
