package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class StartActivity extends AppCompatActivity {
    private static Context applicationContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Socketの設定
        SocketConnection.setIpAddress("192.168.0.12");
        SocketConnection.setPORT("50005");

        applicationContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return applicationContext;
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.button_live:
                Log.d("button", "live");
                new SocketConnection().execute("LIVE");
                break;
            case R.id.button_image:
                Log.d("button", "image");
                new SocketConnection().execute("IMAGE");
                break;
            case R.id.button_video:
                Log.d("button", "video");
                new SocketConnection().execute("VIDEO");
                break;
            case R.id.button_blank:
                Log.d("button", "blank");
                new SocketConnection().execute("BLANK");
                break;
            case R.id.button_setting:
                Intent intent = new Intent(StartActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
