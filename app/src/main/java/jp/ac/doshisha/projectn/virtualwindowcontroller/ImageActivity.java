package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ImageActivity extends AppCompatActivity {
    Handler UIHandler = new Handler(Looper.getMainLooper());
    private static int buttonIdCounter = 0;
    private int buttonAreaWidth;
    private int buttonWidth;
    private int stackWidth = 0;
    private final int margin = 5;
    private RelativeLayout buttonArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Send fetch thumbs command
        new SocketConnection(this).execute("GET_IMAGE_THUMBS");

        buttonArea = findViewById(R.id.buttonArea);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        buttonAreaWidth = buttonArea.getWidth();
    }

    public void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.button_home:
                finish();
                break;
        }
    }

    // http://tongari.webcrow.jp/frog/?p=99
    public void addThumbnailButton(Bitmap bmp) {
        // Generate Image button with bitmap sent from server
        ImageButton btn = new ImageButton(this);
        btn.setImageBitmap(bmp);
        btn.setBackground(getDrawable(R.drawable.ripple_button));
        btn.setId(buttonIdCounter++);

        // Get relative layout params of new ImageButton
        RelativeLayout.LayoutParams prm = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        buttonArea.addView(btn, prm);
        btn = buttonArea.findViewById(btn.getId());

        if (btn.getId() != 0) {
            int prevId = btn.getId();
            prevId -= 1;

            // The case next button has to place next line
            if (buttonAreaWidth - (bmp.getWidth() + stackWidth + margin*2) < 0) {
                stackWidth = 0;
                prm.addRule(RelativeLayout.BELOW, prevId);
                prm.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
                prm.setMargins(5 , 5, 5, 5);
            }
            // The case next button can be placed next to previous button
            else {
                prm.addRule(RelativeLayout.ALIGN_TOP, prevId);
                prm.addRule(RelativeLayout.RIGHT_OF, prevId);
                prm.setMargins(5 , 0, 5, 0);
            }

            btn.setLayoutParams(prm);
            stackWidth += bmp.getWidth();
        }

    }

}
