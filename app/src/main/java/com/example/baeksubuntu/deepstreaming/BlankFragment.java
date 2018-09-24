package com.example.baeksubuntu.deepstreaming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class BlankFragment extends Fragment {

    WebView fragment1_webview;
    View view;

    public BlankFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // 생성자 다음으로 불리는 데이터 처리하는 곳
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blank, container, false);
        fragment1_webview = (WebView_init)view.findViewById(R.id.fragment1_webview);

        return view;
    }

    // 최종적으로 액티비티에 붙여주는 곳
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment1_webview.loadUrl("http://192.168.0.23:3000");
        //fragment1_webview.loadUrl("http://113.198.81.58:80");

    }


}