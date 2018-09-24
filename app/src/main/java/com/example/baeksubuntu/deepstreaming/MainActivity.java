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

    DrawerLayout drawerLayout;
    TabLayout tabLayout;
    ViewPager viewPager;

    Fragment mainFragment;
    Fragment mainFragment2;
    Fragment mainFragment3;

    Intent serverIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();
        server_init();
        tablayout_init();

    }

    // 서비스 종료를 알려줘야 함, 서비스 종료 시
    @Override
    protected void onDestroy(){
        //stopService(serverIntent);
        super.onDestroy();
    }


    // 초기화
    private void init(){
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        tabLayout = (TabLayout)findViewById(R.id.main_tablayout);
        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        mainFragment = new BlankFragment();
        mainFragment2 = new BlankFragment2();
        mainFragment3 = new BlankFragment3();

        String path = Environment.getExternalStorageState();
        if(path.equals(Environment.MEDIA_MOUNTED)){
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deeptector";
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs();

            }
        }
    }



    public void server_init(){
        serverIntent = new Intent(getApplicationContext(), pushService.class);
        startService(serverIntent);
        Log.d("ServiceStart:", " server_init()");

        //bindService(ServerIntent, mConnection, Context.BIND_AUTO_CREATE); // Œ­ºñœº¿Í ¿¬°á¿¡ ŽëÇÑ Á€ÀÇ
        //mIsBound = true;
    }

    // 탭 레이아웃 초기화
    private void tablayout_init() {
        tabLayout.addTab(tabLayout.newTab().setText("HOME").setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setText("LIST").setIcon(R.drawable.list1));
        tabLayout.addTab(tabLayout.newTab().setText("CCTV").setIcon(R.drawable.player));

        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("포지션 : " + tab.getPosition());

                if(tab.getPosition()==2){
                    Bundle bundle = new Bundle();
                    bundle.putString("rtsp", "rtsp");
                    Message msg = new Message();
                    msg.setData(bundle);
                    BlankFragment3.rtsp_handler.sendMessage(msg);
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


    // 어댑터에 프래그먼트들을 주입 시킴
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
                case 0 : return mainFragment;
                case 1 : return mainFragment2;
                case 2 : return mainFragment3;
            }
            return null;
        }
    }
}