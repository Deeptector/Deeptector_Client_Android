package com.example.baeksubuntu.deepstreaming;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class Fragment_CCTV extends Fragment {

    private RelativeLayout fragment_cctv_Layout;
    private RtspPlayView rtspPlayView;
    private View current_cctv_view;

    private int flag = 0;

    public static Handler main_receive_Handler;

    public Fragment_CCTV() {

        // CCTV Fragment가 선택되었을때 Main에서 메세지를 보내 rtsp가 실행되게 함. 최초 한번만 실행시켜 어플이 종료되지않는이상 계속 유지
        main_receive_Handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.getData().getString("video").equals("playing")){
                    if(flag==0) {

                        try {
                            rtsp_Playing();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("비디오 체크", "rtspError");
                            return;
                        }

                        flag++;
                    }
                }
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // 생성자 다음으로 불리는 곳, 데이터 처리
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        current_cctv_view = inflater.inflate(R.layout.fragment_cctv, container, false);

        // Inflate the layout for this fragment
        return current_cctv_view;
    }


    // rtsp서버에 접근, rtsp에서받은 데이터를 가지고 해당 layout에서 볼 수 있게함
    public void rtsp_Playing(){
        rtspPlayView = new RtspPlayView(getContext().getApplicationContext(), "rtsp://192.168.0.19:8089/rtsp");
        fragment_cctv_Layout = current_cctv_view.findViewById(R.id.fragment_cctv_videoStream_id);
        fragment_cctv_Layout.addView(rtspPlayView);
    }


}
