package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {
    private static Context applicationContext = null;
    public static ImageView imageView;
    public static Handler UIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Socketの設定
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SocketConnection.setIpAddress(sp.getString("pref_ip", ""));
        SocketConnection.setPORT(sp.getString("pref_port", ""));

        // Connectionの確認

        imageView = findViewById(R.id.debugImageView);

        applicationContext = getApplicationContext();
    }

    static Context getAppContext() {
        return applicationContext;
    }

    static
    {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.button_live:
                new SocketConnection().execute("LIVE");
                break;
            case R.id.button_image:
                new SocketConnection().execute("IMAGE");
                break;
            case R.id.button_video:
                new SocketConnection().execute("VIDEO");
                break;
            case R.id.button_blank:
                new SocketConnection().execute("BLANK");
                break;
            case R.id.button_next:
                new SocketConnection().execute("NEXT");
                break;
            case R.id.button_previous:
                new SocketConnection().execute("PREVIOUS");
                break;
            case R.id.button_debug:
                new SocketConnection().execute("GET_IMAGE_THUMBS");
                break;
            case R.id.button_setting:
                Intent intent = new Intent(StartActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
