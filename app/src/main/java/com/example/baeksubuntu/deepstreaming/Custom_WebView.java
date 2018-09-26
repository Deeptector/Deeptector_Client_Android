package com.example.baeksubuntu.deepstreaming;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


// 커스텀 웹뷰를 설정하여 Fragment_Main, Fragment_File 에서 사용
public class Custom_WebView extends WebView {

    public Custom_WebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.getSettings().setJavaScriptEnabled(true);
        this.setWebViewClient(new WebViewClient());
        this.getSettings().setAppCachePath(context.getApplicationContext().getCacheDir().getAbsolutePath());
        this.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        this.getSettings().setDatabaseEnabled(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setLoadWithOverviewMode(true);
        this.addJavascriptInterface(new JavaScriptInterface(context.getApplicationContext()), "Android");
        this.getSettings().setPluginState(WebSettings.PluginState.ON);

        this.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                String get_st = JavaScriptInterface.getBase64StringFromBlobUrl(url);
                loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url));

            }
        });
    }
}



