package com.example.baeksubuntu.deepstreaming;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.melnykov.fab.FloatingActionButton;


public class BlankFragment2 extends Fragment {


    public FloatingActionButton folder_btn;

    WebView fragment2_webview;
    View view;


    public BlankFragment2() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        fragment2_webview = (WebView_init)view.findViewById(R.id.fragment2_webview);
        folder_btn = (FloatingActionButton)view.findViewById(R.id.folder_btn2);

        return view;
    }



    // ÃÖÁŸÀûÀž·Î Ÿ×ÆŒºñÆŒ¿¡ ºÙ¿©ÁÖŽÂ °÷
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment2_webview.loadUrl("http://192.168.0.23:3000/list");

        folder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("content://media/external/images/media");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");

                startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


            try{
                Uri uri = data.getData();
                String getName = getName(uri);


                Intent video_play_intent = new Intent(getContext(), VideoViewActivity.class);
                video_play_intent.putExtra("video", getName);
                startActivity(video_play_intent);
            }catch (Exception e){

            }

    }


    public String getName(Uri uri){
        String[] projection = {MediaStore.Video.VideoColumns.DISPLAY_NAME };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);



    }

}