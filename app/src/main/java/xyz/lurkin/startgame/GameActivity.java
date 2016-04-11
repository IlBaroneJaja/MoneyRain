package xyz.lurkin.startgame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    private GameView mGameView;
    private Handler frameHandler;
    private static final int FRAME_RATE = 20; //50 frames per second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGameView = (GameView) findViewById(R.id.gameview);

        frameHandler = new Handler();

        frame();
    }

    //Runnable that call the frame() method
    private Runnable frameUpdate = new Runnable() {
        @Override
        public void run() {
            frame();
        }
    };

    private void frame() {
        mGameView.next();

        //make a new frame() call in FRAME_RATE millisecond
        frameHandler.postDelayed(frameUpdate, FRAME_RATE);
    }

    //hide system UI
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mGameView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
