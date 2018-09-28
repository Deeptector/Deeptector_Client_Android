package com.example.baeksubuntu.deepstreaming;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoView_Activity extends AppCompatActivity {

    Intent get_video_intent;
    VideoView videoView;
    String video_fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        get_video_intent = getIntent();
        video_fileName = get_video_intent.getStringExtra("video");

        videoView = (VideoView)findViewById(R.id.rtsp_videoView_id);

        videoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deeptector/" + video_fileName);

        final MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);         // 화면 하단아래 재생,일시정지 기능 등의 컨트롤러 기능을 붙임

        videoView.start();                 // 영상 자동시작

    }
}

