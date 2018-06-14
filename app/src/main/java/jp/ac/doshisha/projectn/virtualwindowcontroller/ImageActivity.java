package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ImageActivity extends AppCompatActivity {
    Handler UIHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Send fetch thumbs command
        new SocketConnection(this).execute("GET_IMAGE_THUMBS");
    }

    public void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    public void addThumbnailButton(Bitmap bmp) {
        LinearLayout linearLayout = findViewById(R.id.buttonArea);
        ImageButton btn = new ImageButton(this);
        btn.setImageBitmap(bmp);

        linearLayout.addView(btn);
    }


}
