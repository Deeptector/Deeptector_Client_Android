package com.example.baeksubuntu.deepstreaming;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.melnykov.fab.FloatingActionButton;


public class Fragment_File extends Fragment {

    private FloatingActionButton file_folder_btn;

    private File_info_search file_info_search;
    private WebView fragment_file_webview;

    private Intent video_play_intent;
    private View current_file_view;

    private Uri directory_uri;
    private Intent directory_intent;


    public Fragment_File() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        file_info_search = new File_info_search();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        current_file_view = inflater.inflate(R.layout.fragment_file, container, false);
        fragment_file_webview = (Custom_WebView)current_file_view.findViewById(R.id.fragment_file_webview_id);
        file_folder_btn = (FloatingActionButton)current_file_view.findViewById(R.id.fragment_file_folder_btn_id);

        return current_file_view;
    }



    // 최종적으로 액티비티에 붙여주는 곳
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment_file_webview.loadUrl("http://192.168.0.23:3000/list");

        file_folder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 비디오 타입형태로 폴더(파일)를 여는 곳
                directory_uri = Uri.parse("content://media/external/images/media");
                directory_intent = new Intent(Intent.ACTION_VIEW, directory_uri);
                directory_intent.setAction(Intent.ACTION_GET_CONTENT);
                directory_intent.setType("video/*");

                startActivityForResult(directory_intent, 1);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            Uri file_uri = data.getData();
            String file_getPath = file_info_search.get_Info(file_uri, 0, getActivity());        // 파일 실제 경로
            String file_getName = file_info_search.get_Info(file_uri, 1, getActivity());        // 파일 실제 이름
            String file_getUri_id = file_info_search.get_Info(file_uri, 2, getActivity());      // 파일 실제 아이디값

            Log.d("선택파일정보 : ", "실제경로 : " + file_getPath + "// 파일명 : " + file_getName + "// 아이디 : " + file_getUri_id);

            video_play_intent = new Intent(getContext(), Rtsp_VideoView_Activity.class);
            video_play_intent.putExtra("video", file_getName);
            startActivity(video_play_intent);

        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

}
