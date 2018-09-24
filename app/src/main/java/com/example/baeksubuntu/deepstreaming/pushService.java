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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ŸÈÅº on 2018-09-03.
 */

public class pushService extends Service {

    String read_in_data;
    String push_read_in_data;

    public static String ip ="192.168.0.6";
    public static int port = 3003;

    public static Socket socket;
    public static DataInputStream dis;
    public static BufferedInputStream bis;
    public static InputStream is;
    public static DataOutputStream dos;
    public static OutputStream os;
    public static FileOutputStream fos;


    public static String push_ip = "192.168.0.6";
    public static int push_port = 3004;

    public static Socket push_socket;
    public static DataInputStream push_dis;
    public static InputStream push_is;
    public static OutputStream push_os;
    public static DataOutputStream push_dos;

    public Push_Notification push_notification;

    private Thread read_thread;
    private Thread push_read_thread;

    public pushService() {

    }

    // 서비스를 실행시킨 Activity와의 메세지를 주고 받음 (Activity로부터 binding된 메세지)
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
        // 서비스에서 가장 먼저 호출 됨
        super.onCreate();

        read_in_data = "data_wait";
        push_read_in_data = "push_data_wait";

        push_notification = new Push_Notification();


        // 서버 소켓에 접근
        connectServer();
        Log.d("ConnectServer", "œÇÇà");
        push_connectServer();
        Log.d("PushConnectServer", "œÇÇà");




    }

    @Override
    public void onDestroy() {
        //
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 서비스 객체와 통신 (데이터를 전달 할 필요가 없으면 return null)
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출 될 때마다 실행됨
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name){
        // 서비스가 할 일이 끝나면 이걸로 알려 줌
        return super.stopService(name);
    }


    public void push_connectServer(){
        new Thread(){
            @Override
            public void run(){

                try {

                    push_socket = new Socket(push_ip, push_port);
                    Log.e("push_socket", "push_socket OK!");
                    push_is = push_socket.getInputStream();
                    //bis = new BufferedInputStream(is);
                    push_dis = new DataInputStream(push_is);

                    System.out.println("push_Æ®¶óÀÌŽÂ µéŸî¿È");

                    push_read_data();
                    // 서버로부터 처음데이터 받는곳
                    push_read_thread.start();

                    //Send_Msg("[LOGIN]" + "::" + "+821041199582"); // ŸÆÀÌµð¶ûžŠ ºž³œŽÙ

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("push_client","push_connectServer() Error");
                    return;
                }
            }
        }.start();
    }

    public void push_read_data(){
        push_read_thread = new Thread(){
            @Override
            public void run() {
                Log.d("socket", "Wait");

                while (true) {

                    try {
                        push_read_in_data.trim();
                        Log.d("readUTF", "before:"+read_in_data);
                        push_read_in_data = push_dis.readUTF();
                        Log.d("readUTF", "after:"+read_in_data);
                        push_read_in_data.trim();
                        Log.d("readUTF_data: ", push_read_in_data.toString());

                        // 서버로부터 push라는 값이 넘어오면 알림 (노티피케이션)을 띄움
                        if (push_read_in_data.equals("push")) {
                            push_notification.onPush("Fighting", "nowFighting");
                        }
                        // 서버로부터 video라는 값이 넘어오면 소켓연결을 시도
                        else if (push_read_in_data.equals("video")) {
                            if (socket.isConnected()) {


                                Log.d("downloading_before", "before");
                                // 파일 다운로드 시작
                                downloading_data();

                                Log.d("before get_file", "Socket Connected");
                            } else {
                                Log.d("before get_file", "Socket DisConnected");
                            }
                        }

                    } catch (Exception e) {
                        Log.e("inputError", "inputError");
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };
    }



    public void connectServer(){
        new Thread(){
            @Override
            public void run(){

                try {

                    socket = new Socket(ip, port);
                    Log.e("socket", "socket OK!");
                    is = socket.getInputStream();
                    //bis = new BufferedInputStream(is);
                    dis = new DataInputStream(is);

                    os = socket.getOutputStream();
                    dos = new DataOutputStream(os);

                    System.out.println("Æ®¶óÀÌŽÂ µéŸî¿È");

                    // Œ­¹ö·ÎºÎÅÍ Ã³Àœµ¥ÀÌÅÍ(?) ¹ÞŽÂ°÷
                    read_data();
                    read_thread.start();

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("client","connectServer() Error");
                    return;
                }
            }
        }.start();
    }

    //
    public void read_data(){
        read_thread = new Thread(){
            @Override
            public void run() {
                Log.d("socket", "Wait");


                while (true) {
                    try {
                        read_in_data.trim();
                        Log.d("readUTF", "before");
                        read_in_data = dis.readUTF();
                        Log.d("readUTF", "after");
                        read_in_data.trim();

                        // Œ­¹ö·ÎºÎÅÍ push¶óŽÂ °ªÀÌ ³ÑŸî¿Àžé ŸËž²(³ëÆŒÇÇÄÉÀÌŒÇ)À» ¶ç¿ò
                        if (read_in_data.equals("push")) {
                            push_notification.onPush("Fighting", "nowFighting");
                        }
                        // Œ­¹ö·ÎºÎÅÍ video¶óŽÂ °ªÀÌ ³ÑŸî¿Àžé ŒÒÄÏ¿¬°áÀ» œÃµµ
                        else if (read_in_data.equals("video")) {
                            if (socket.isConnected()) {

                                // ÆÄÀÏ ŽÙ¿î·Îµå œÃÀÛ->¿©±âŒ­ žÖÆŒŸ²·¹µå ÇüÅÂ·Î º°°³ÀÇ œºÆ®ž²µéÀ» žžµéŸîÁàŸßÇÒµí
                                downloading_data();

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

    public void downloading_data(){

        int loading_count = 0;
        int recv_data_count = 0;
        byte[] buffer = new byte[1024];


        try{
            Log.d("get_file", "ready");

            int file_length = dis.readInt();
            Log.d("get_file_length:", Integer.toString(file_length));

            String file_name = dis.readUTF();
            Log.d("get_file_name:", file_name);

            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deeptector", file_name);
            fos = new FileOutputStream(f);

            while(loading_count < file_length){
                recv_data_count = dis.read(buffer);
                //Log.d("get_file_readBytes : ", Integer.toString(recv_data_count) );

                fos.write(buffer, 0, recv_data_count);

                loading_count += recv_data_count;
            }
            fos.flush();

            Push_Notification finish_down_alarm = new Push_Notification();
            finish_down_alarm.onPush(file_name + " : Download OK", "Download at Deeptector Folder");
            //new Push_Notification().onPush(file_name + " : Download OK", "Download at Deeptector Folder");
            Log.d("Directory:", f.getPath().toString());

            dos.writeUTF("receiveOK");
            dos.flush();

        }catch (Exception e){
            Log.d("get_file_Server:", "Error");
            e.printStackTrace();
            return;
        }
        finally {
            try{
                fos.close();
                fos = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    // 푸시를 통해 알림을 알림
    class Push_Notification {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onPush(String Title, String subtitle){
            NotificationManager notificationManager = (NotificationManager)pushService.this.getSystemService(pushService.this.NOTIFICATION_SERVICE);

            Intent send_push_intent = new Intent(pushService.this.getApplicationContext(), MainActivity.class);

            Notification.Builder builder = new Notification.Builder(getApplicationContext());

            send_push_intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(pushService.this, 0, send_push_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(Title.equals("Fighting")){
                builder.setSmallIcon(R.drawable.cctv)
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

                notificationManager.notify(1, builder.build()); // Fighting Notification
            }

            else {
                builder.setSmallIcon(R.drawable.cctv)
                        .setTicker("HETT")
                        .setWhen(System.currentTimeMillis())
                        .setNumber(1)
                        .setContentTitle(Title)
                        .setContentText(subtitle)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setOngoing(true);

                notificationManager.notify(2, builder.build()); // Down complete Notification send
            }
            Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
        }
    }
}