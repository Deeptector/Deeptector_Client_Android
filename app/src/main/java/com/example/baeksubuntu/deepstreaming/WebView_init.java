package com.example.baeksubuntu.deepstreaming;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ŸÈÅº on 2018-09-04.
 */

public class WebView_init extends WebView {


    public WebView_init(Context context, AttributeSet attrs) {
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
                // JavaScriptInterface.convertBase64StringTopdfAndStoreIt(get_st);
                loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url));

                // url = url.replace("blob : ",");

                /*
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://192.168.0.21:80/50a94062-689c-b164"));
                request.allowScanningByMediaScanner();
                 */

            }
        });


        //this.loadUrl("http://113.198.81.58:80");
        // this.loadUrl("http://113.198.81.58:80/#/List");
    }


}