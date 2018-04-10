package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    SocketConnection socketConnection = new SocketConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Socketの設定
        socketConnection.setIpAddress("172.20.11.96");
        socketConnection.setPORT("50005");
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.button_live:
                Log.d("button", "live");
                socketConnection.execute("LIVE");
                break;
            case R.id.button_image:
                Log.d("button", "image");
                break;
            case R.id.button_video:
                Log.d("button", "video");
                break;
            case R.id.button_blank:
                Log.d("button", "blank");
                socketConnection.execute("BLANK");
                break;
        }
    }
}
