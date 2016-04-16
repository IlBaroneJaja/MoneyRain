package be.ecam.moneyrain;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private GameView mGameView;
    private Handler frameHandler;
    private static final int FRAME_RATE = 15;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGameView = (GameView) findViewById(R.id.gameview);

        frameHandler = new Handler();
        SharedPreferences settings = getSharedPreferences("sharedSettings",0);
        level = settings.getString("level","BEGGAR"); // le 2e string est là au cas où les préférences n'ont pas été initialisiées.
        Toast.makeText(this,level,Toast.LENGTH_LONG).show();
        final TextView letsgo = (TextView) findViewById(R.id.letsGO);
        letsgo.setAlpha(0f);
        letsgo.setVisibility(View.VISIBLE);
        letsgo.animate()
                .alpha(1f)
                .setDuration(2000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        letsgo.animate()
                                .alpha(0f)
                                .setDuration(1000)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        letsgo.setVisibility(View.GONE);
                                    }
                                });
                    }
                });

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
