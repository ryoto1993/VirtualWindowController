package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ThumbsActivity extends AppCompatActivity {
    Handler UIHandler = new Handler(Looper.getMainLooper());
    private int buttonIdCounter = 1;
    private int buttonAreaWidth;
    private int stackWidth = 0;
    private RelativeLayout buttonArea;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbs);

        // Get intent
        Intent intent = getIntent();
        mode = intent.getStringExtra("MODE");

        // Send fetch thumbs command
        switch (mode) {
            case "IMAGE":
                new SocketConnection(this).execute("GET_IMAGE_THUMBS");
                break;
            case "VIDEO":
                new SocketConnection(this).execute("GET_VIDEO_THUMBS");
                break;
        }


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
            default:
                int id = view.getId();
                id--;
                switch (mode) {
                    case "IMAGE":
                        new SocketConnection(this).execute("SET_IMAGE_BY_ID", String.valueOf(id));
                        break;
                    case "VIDEO":
                        new SocketConnection(this).execute("SET_VIDEO_BY_ID", String.valueOf(id));
                        break;
                }

        }
    }

    // http://tongari.webcrow.jp/frog/?p=99
    public void addThumbnailButton(Bitmap bmp) {
        // Generate Image button with bitmap sent from server
        ImageButton btn = new ImageButton(this);
        btn.setImageBitmap(bmp);
        btn.setBackground(getDrawable(R.drawable.ripple_button));
        btn.setId(buttonIdCounter++);
        btn.setOnClickListener(this::buttonOnClick);

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
            int margin = 7;
            if (buttonAreaWidth - (bmp.getWidth() + stackWidth + margin *2) < 0) {
                stackWidth = 0;
                prm.addRule(RelativeLayout.BELOW, prevId);
                prm.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
                prm.setMargins(margin , margin*2, margin, margin*2);
            }
            // The case next button can be placed next to previous button
            else {
                prm.addRule(RelativeLayout.ALIGN_TOP, prevId);
                prm.addRule(RelativeLayout.RIGHT_OF, prevId);
                prm.setMargins(margin , 0, margin, 0);
            }

            btn.setLayoutParams(prm);
            stackWidth += bmp.getWidth();
        }
    }
}
