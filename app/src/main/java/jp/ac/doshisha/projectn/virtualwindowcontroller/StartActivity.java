package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.app.Activity;
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
    static Handler UIHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Socketの設定
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SocketConnection.setIpAddress(sp.getString("pref_ip", ""));
        SocketConnection.setPORT(sp.getString("pref_port", ""));

        // Connectionの確認
        new SocketConnection(this).execute("ECHO", "Check for Status!");

        applicationContext = getApplicationContext();
    }

    static Context getAppContext() {
        return applicationContext;
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.button_live:
                new SocketConnection(this).execute("LIVE");
                break;
            case R.id.button_image:
                new SocketConnection(this).execute("IMAGE");
                break;
            case R.id.button_video:
                new SocketConnection(this).execute("VIDEO");
                break;
            case R.id.button_blank:
                new SocketConnection(this).execute("BLANK");
                break;
            case R.id.button_home:
                new SocketConnection(this).execute("HOME");
                break;
            case R.id.button_next:
                new SocketConnection(this).execute("NEXT");
                break;
            case R.id.button_previous:
                new SocketConnection(this).execute("PREVIOUS");
                break;
            case R.id.button_debug:
                new SocketConnection(this).execute("GET_IMAGE_THUMBS");
                break;
            case R.id.button_setting:
                Intent intent = new Intent(StartActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
