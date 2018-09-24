package com.example.baeksubuntu.deepstreaming;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;


public class BlankFragment3 extends Fragment {

    private RelativeLayout relativeLayout;
    private RtspPlayView playView;
    private EditText ip_txt;
    private EditText port_txt;
    private View view;

    public static Handler rtsp_handler;
    public int hanlder_flag = 0;


    public BlankFragment3() {
        // Required empty public constructor

        rtsp_handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(hanlder_flag==0) {
                    rtsp_playing();
                    hanlder_flag++;
                }
            }
        };

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // »ýŒºÀÚ ŽÙÀœÀž·Î ºÒž®ŽÂ °÷, µ¥ÀÌÅÍ Ã³ž®
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blank_fragment3, container, false);

        //ip_txt = view.findViewById(R.id.ip);
        //port_txt = view.findViewById(R.id.port);


        // rtsp 함수 호출하여 프래그먼트3번째에 연결
        //rtsp_playing();

        // Inflate the layout for this  fragment
        return view;
    }


    // rtsp 함수

    public void rtsp_playing(){
        playView = new RtspPlayView(getContext().getApplicationContext(), "rtsp://192.168.0.19:8089/rtsp");
        relativeLayout = view.findViewById(R.id.fragment3_videoStream);
        relativeLayout.addView(playView);


    }


}