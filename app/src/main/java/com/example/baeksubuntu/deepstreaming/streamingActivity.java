package com.example.baeksubuntu.deepstreaming;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class streamingActivity extends AppCompatActivity {
    private RelativeLayout container;
    private RtspPlayView playView;

    //AT
    private TextView ip_txt;
    private TextView port_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);


        // AT
        ip_txt = (TextView)findViewById(R.id.ip);
        port_txt = (TextView)findViewById(R.id.port);



        playView = new RtspPlayView(getApplicationContext(), "rtsp://192.168.0.19:8089/rtsp");
        final RelativeLayout relativeLayout = findViewById(R.id.videoStream);
        relativeLayout.addView(playView);
        container = findViewById(R.id.videoStream);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent data = new Intent(getApplicationContext(), MainActivity.class);
                switch (item.getItemId()) {
                    case R.id.main:
                        data.putExtra("id", "1");
                        Log.e("get", "main tab home");
                        setResult(1, data);
                        finish();
                        break;
                    case R.id.list:
                        data.putExtra("id", "2");
                        data.putExtra("ip", ip_txt.getText().toString());
                        data.putExtra("port", port_txt.getText().toString());

                        Log.e("get", "main tab list");
                        setResult(2, data);
                        finish();
                        break;
                    case R.id.cctv:
                        Log.e("get", "main tab cctv");
                        break;
                }
                return false;
            }
        });

        ImageButton capButton = findViewById(R.id.capButton);

        /*capButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bitmap screenBitmap = Bitmap.createBitmap(playView.drawBitmap());

                String folder = "capture";

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date currentTime = new Date();
                String dataString = format.format(currentTime);

                File sdCardPath = Environment.getExternalStorageDirectory();
                File dirs = new File(Environment.getExternalStorageDirectory(),folder);

                if(!dirs.exists()){
                    dirs.mkdir();
                    Log.d("TEST","Directory Create");
                }


                FileOutputStream fos;
                String save;
                save = sdCardPath.getPath()+"/"+folder+"/"+dataString+".jpg";
                try{
                    fos = new FileOutputStream(save);
                    screenBitmap.compress(Bitmap.CompressFormat.JPEG, 100,fos);
                    fos.flush();
                    fos.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),dataString+".jpg is Captured!",Toast.LENGTH_LONG).show();

            }
        });*/

    }


}
