package com.example.baeksubuntu.deepstreaming;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Fragment fragment_main;
    private Fragment fragment_file;
    private Fragment fragment_cctv;

    private Intent server_Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();                 // 뷰에 관련된 부분, 초기화
        server_Init();          // Service (서버관련)쪽을 실행하는 부분
        tablayout_Init();       // 탭 레이아웃 초기화

    }

    // 서비스 종료를 알려줘야함
    @Override
    protected void onDestroy(){

        // 서비스는 백그라운드에서 돌면서 폭력감지나 동영상 다운로드를 해야하므로 stopService를 호출 하면 안됨
        //stopService(serverIntent);
        super.onDestroy();
    }


    // 뷰에 관련된 부분, 초기화
    private void init(){
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout_id);
        tabLayout = (TabLayout)findViewById(R.id.main_tablayout_id);
        viewPager = (ViewPager)findViewById(R.id.main_viewPager_id);

        fragment_main = new Fragment_Main();
        fragment_file = new Fragment_File();
        fragment_cctv = new Fragment_CCTV();

        // 한번 더 디렉터리 여부를 확인함
        String path = Environment.getExternalStorageState();
        if(path.equals(Environment.MEDIA_MOUNTED)){
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deeptector";
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs();

            }
        }
    }


    // Service (서버관련)쪽을 실행하는 부분
    private void server_Init(){
        server_Intent = new Intent(getApplicationContext(), Notification_Service.class);
        startService(server_Intent);
        Log.d("ServiceStart:", " server_init()");

    }

    // 탭 레이아웃 초기화
    private void tablayout_Init() {
        tabLayout.addTab(tabLayout.newTab().setText("HOME").setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setText("LIST").setIcon(R.drawable.list1));
        tabLayout.addTab(tabLayout.newTab().setText("CCTV").setIcon(R.drawable.cctv));

        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                // 미리 rtsp실행을 하는것이 아닌 Rtsp 뷰(프래그먼트)가 띄워지게될 때 rtsp와 실행 관련 된 함수를 호출을 하게 함
                if(tab.getPosition()==2) {
                    Bundle msg_bundle = new Bundle();
                    msg_bundle.putString("video", "playing");
                    Message msg = new Message();
                    msg.setData(msg_bundle);
                    Fragment_CCTV.main_receive_Handler.sendMessage(msg);
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    // 어댑터에 프래그먼트를 붙임
    public class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position){
                case 0 : return fragment_main;
                case 1 : return fragment_file;
                case 2 : return fragment_cctv;
            }
            return null;
        }
    }
}
