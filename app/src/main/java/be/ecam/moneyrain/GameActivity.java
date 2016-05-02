package be.ecam.moneyrain;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private GameView mGameView;
    private Handler frameHandler;
    private static final int FRAME_RATE = 16;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        SharedPreferences settings = getSharedPreferences("sharedSettings",0);
        String level = settings.getString("level", "BEGGAR");
        mGameView = (GameView) findViewById(R.id.gameview);
        mGameView.setLevel(level);
        mGameView.setLives(5);
        frameHandler = new Handler();

        TextView tvLives = (TextView) findViewById(R.id.tvLives);
        tvLives.setText(Integer.toString(mGameView.getLives()));

        Toast.makeText(this, level,Toast.LENGTH_LONG).show();
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
        TextView tvLives = (TextView) findViewById(R.id.tvLives);
        TextView tvScore = (TextView) findViewById(R.id.tvScore);

        tvLives.setText(Integer.toString(mGameView.getLives()));
        tvScore.setText(Integer.toString(mGameView.getScore()));
        if (mGameView.getLives() > 0)
        {
            //make a new frame() call in FRAME_RATE millisecond
            frameHandler.postDelayed(frameUpdate, FRAME_RATE);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Game has ended");
            builder.setMessage("You lost with a score of"+mGameView.getScore());
            builder.setCancelable(false).setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(GameActivity.this, StartUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id. btn_back:
                Intent intent = new Intent(GameActivity.this, StartUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
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
