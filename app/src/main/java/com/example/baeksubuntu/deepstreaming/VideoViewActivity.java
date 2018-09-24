package com.example.baeksubuntu.deepstreaming;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent get_intent = getIntent();
        String fileName = get_intent.getStringExtra("video");

        videoView = (VideoView)findViewById(R.id.videoview);

        videoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deeptector/" + fileName);

        final MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        videoView.start();

    }
}
