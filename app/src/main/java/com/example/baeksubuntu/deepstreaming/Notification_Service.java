package com.example.baeksubuntu.deepstreaming;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by 안탄 on 2018-09-03.
 */

public class Notification_Service extends Service {

    // 파일 다운로드 관련
    private Thread file_read_thread;
    private String file_read_in_data;

    private static String ip ="192.168.0.6";
    private static int port = 3003;

    private static Socket file_socket;
    private static DataInputStream file_dis;
    private static InputStream file_is;
    private static DataOutputStream file_dos;
    private static OutputStream file_os;
    private static FileOutputStream file_fos;


    // 폭력감지시 사용 되는 알림 관련
    private Thread push_read_thread;
    private String push_read_in_data;

    private static String push_ip = "192.168.0.6";
    private static int push_port = 3004;

    private static Socket push_socket;
    private static DataInputStream push_dis;
    private static InputStream push_is;


    // 알림 역할을 해주는 Notification 레퍼런스 선언
    protected Push_Notification push_notification;

    public Notification_Service() {

    }


    // Service를 실행시킨 Activity와 메세지를 주고받는 부분 ( Activity로부터 binding 된 메세지 )
    public final Messenger mMessenger = new Messenger(new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            return false;
        }
    }));


    @Override
    public void onCreate() {
        // 서비스에서 가장 먼저 호출 됨(최초 1회만)
        super.onCreate();

        file_read_in_data = "data_wait";
        push_read_in_data = "push_data_wait";

        push_notification = new Push_Notification();

        // 서버 각 소켓에 접근
        file_connectServer();
        Log.d("ConnectServer", "실행");
        push_connectServer();
        Log.d("PushConnectServer", "실행");

    }

    @Override
    public void onDestroy() {
        // 서비스가 종료될 때 실행
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Service객체와 통신 (데이터를 전달 할 필요가 없으면 return null)
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출 될 때마다 실행됨
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name){
        // 서비스가 할일이 끝나면 이걸로 알려줘야함
        return super.stopService(name);
    }

    // 폭력 감지 관련 서버소켓, 스트림 초기화
    protected void push_connectServer(){
        new Thread(){
            @Override
            public void run(){

                try {

                    push_socket = new Socket(push_ip, push_port);
                    Log.e("push_socket", "push_socket OK!");
                    push_is = push_socket.getInputStream();
                    push_dis = new DataInputStream(push_is);


                    push_Read_data();
                    // 서버로부터 처음데이터(?) 받는곳
                    push_read_thread.start();


                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("push_client","push_connectServer() Error");
                    return;
                }
            }
        }.start();
    }

    // 서버로부터 폭력 감지 데이터를 받아 폭력감지알림을 보내는 곳
    protected void push_Read_data(){
        push_read_thread = new Thread(){
            @Override
            public void run() {
                Log.d("push_socket", "Wait");

                while (true) {

                    try {
                        push_read_in_data.trim();
                        Log.d("push_readUTF", "before:" + push_read_in_data);
                        push_read_in_data = push_dis.readUTF();
                        Log.d("push_readUTF", "after:" + push_read_in_data);
                        push_read_in_data.trim();
                        Log.d("push_readUTF_data: ", push_read_in_data.toString());

                        // 서버로부터 push라는 값이 넘어오면 알림(노티피케이션)을 띄움
                        if (push_read_in_data.equals("push")) {
                            push_notification.onPush("Detection", "now Detecting !!");
                        }

                    } catch (Exception e) {
                        Log.e("push_Error : ", "inputError");
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };
    }


    // 파일 다운로드 관련 서버소켓, 스트림 초기화
    protected void file_connectServer(){
        new Thread(){
            @Override
            public void run(){

                try {

                    file_socket = new Socket(ip, port);
                    Log.e("socket", "socket OK!");
                    file_is = file_socket.getInputStream();
                    file_dis = new DataInputStream(file_is);

                    file_os = file_socket.getOutputStream();
                    file_dos = new DataOutputStream(file_os);

                    // 서버로부터 처음데이터 받는곳
                    file_read_Data();
                    file_read_thread.start();

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("client","connectServer() Error");
                    return;
                }
            }
        }.start();
    }

    // 서버로부터의 파일 다운로드 하는곳
    protected void file_read_Data(){
        file_read_thread = new Thread(){
            @Override
            public void run() {
                Log.d("socket", "Wait");

                while (true) {
                    try {
                        file_read_in_data.trim();
                        Log.d("readUTF", "before");
                        file_read_in_data = file_dis.readUTF();
                        Log.d("readUTF", "after");
                        file_read_in_data.trim();

                        // 서버로부터 video라는 값이 넘어오면 소켓연결을 시도
                        if (file_read_in_data.equals("video")) {
                            if (file_socket.isConnected()) {

                                // 파일 다운로드 시작
                                downloading_Data();

                                Log.d("before get_file", "Socket Connected");
                            } else {
                                Log.d("before get_file", "Socket DisConnected");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };
    }

    protected void downloading_Data(){

        int loading_count = 0;
        int recv_data_count = 0;
        byte[] buffer = new byte[1024];


        try{
            Log.d("get_file", "ready");

            // 파일 길이를 읽어드림
            int file_length = file_dis.readInt();
            Log.d("get_file_length:", Integer.toString(file_length));

            // 파일 이름을 읽어드림
            String file_name = file_dis.readUTF();
            Log.d("get_file_name:", file_name);

            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deeptector", file_name);
            file_fos = new FileOutputStream(f);

            // 파일 다운 -> 지정된 버퍼양 만큼 while문을 돌면서 파일에 씀
            while(loading_count < file_length){
                recv_data_count = file_dis.read(buffer);

                file_fos.write(buffer, 0, recv_data_count);

                loading_count += recv_data_count;
            }
            file_fos.flush();

            // (다운로드가 끝나면) 노티피케이션을 통해 Push 알림 발송
            Push_Notification finish_down_alarm = new Push_Notification();
            finish_down_alarm.onPush(file_name + " : Download OK", "Download to Deeptector Folder");

            file_dos.writeUTF("receiveOK");
            file_dos.flush();

        }catch (Exception e){
            Log.d("get_file_Server:", "Error");
            e.printStackTrace();
            return;
        }
        finally {
            try{
                file_fos.close();
                file_fos = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }



    // 파일을 다운받게되면 푸시를 통해 알려줌
    class Push_Notification {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onPush(String Title, String subtitle){
            NotificationManager notificationManager = (NotificationManager)Notification_Service.this.getSystemService(Notification_Service.this.NOTIFICATION_SERVICE);

            Intent send_push_intent = new Intent(Notification_Service.this.getApplicationContext(), MainActivity.class);

            Notification.Builder builder = new Notification.Builder(getApplicationContext());

            send_push_intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(Notification_Service.this, 0, send_push_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // 폭력 감지가 되었을 시 폭력 알림
            if(Title.equals("Detection")){
                builder.setSmallIcon(R.drawable.logo)
                        .setTicker("HETT")
                        .setWhen(System.currentTimeMillis())
                        .setNumber(1)
                        .setContentTitle(Title)
                        .setContentText(subtitle)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.warning ))
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setOngoing(true);
                notificationManager.notify(1, builder.build()); // Notification send
            }

            // 동영상(파일) 다운로드가 완료 됐을 시 알림
            else {
                builder.setSmallIcon(R.drawable.logo)
                        .setTicker("HETT")
                        .setWhen(System.currentTimeMillis())
                        .setNumber(1)
                        .setContentTitle(Title)
                        .setContentText(subtitle)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setOngoing(true);
                notificationManager.notify(2, builder.build()); // Notification send
            }

            // 메세지 알림시 진동 추가 (1초)
            Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
        }
    }
}