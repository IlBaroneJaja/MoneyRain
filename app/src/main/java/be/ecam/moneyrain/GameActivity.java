package be.ecam.moneyrain;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private GameView mGameView;
    private Handler frameHandler;
    private static final int FRAME_RATE = 20;
    public static final String settings = "sharedSettings";
    public static final String highScores = "highScores";
    private Button btn_back;



    private int highScore;
    static SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;
    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;
    static int soundID_blop, soundID_coin, soundID_bomb, soundID_bonus, soundID_malus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        createSound();
        loadSounds();

        SharedPreferences sharedSettings = getSharedPreferences(settings,0);
        String level = sharedSettings.getString("level", "BEGGAR");
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
            // Le jeu a terminé, car l'utilisateur n'a plus de vie
            if (mGameView.getScore()>0)
            {
                SharedPreferences sharedSettings = getSharedPreferences(settings,0);
                SharedPreferences.Editor editor = sharedSettings.edit();

                int style = DateFormat.SHORT;
                DateFormat dateForm;
                dateForm = DateFormat.getDateInstance(style, Locale.ENGLISH);
                String dateOutput = dateForm.format(new Date());

                String scores = sharedSettings.getString("highScores", "");
                if(scores.length()>0){
                    // des scores sont déjà présents dans la librairie partagée
                    setHighScore(mGameView.getScore());
                }
                else{
                    // Rien n'a été sauvegardé dans la libraire partagée pour les high Scores, donc on crée un nouveau score
                    editor.putString(highScores,""+dateOutput+" - "+mGameView.getScore()); // on set la difficulté au minimum au démarrage de l'appli, à défaut de ne pas avoir changé les préférences
                    editor.commit();
                }


            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Game has ended");
            builder.setMessage("You lost with a score of: "+mGameView.getScore());
            builder.setCancelable(false).setPositiveButton("MENU", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(GameActivity.this, StartUpActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                Intent intent = new Intent(this, StartUpActivity.class);
                playBlop();
                startActivity(intent);
                frameHandler.removeCallbacks(frameUpdate);
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

    protected void createSound(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //if API >= 21
            attributesBuilder = new  AudioAttributes.Builder();
            attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
            attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            attributes=attributesBuilder.build();

            soundPoolBuilder = new SoundPool.Builder();
            soundPoolBuilder.setAudioAttributes(attributes);
            soundPool = soundPoolBuilder.build();
        }
        else{
            //API <21
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
    }

    protected void loadSounds(){
        soundID_blop = soundPool.load(this, R.raw.blop, 1);
        soundID_coin = soundPool.load(this, R.raw.coin, 1);
        soundID_bomb = soundPool.load(this, R.raw.bomb, 1);
        soundID_bonus = soundPool.load(this, R.raw.bonus, 1);
        soundID_malus = soundPool.load(this, R.raw.malus, 1);
    }

    public static void playBlop() {
        soundPool.play(soundID_blop, 0.5f, 0.5f, 1, 0, 1);
        Log.d("debug", "test playblop");
    }

    public static void playCoin() {
        soundPool.play(soundID_coin, 0.4f, 0.4f, 1, 0, 1);
    }

    public static void playBomb() {
        soundPool.play(soundID_bomb, 0.4f, 0.4f, 1, 0, 1);

    }

    public static void playBonus() {
        soundPool.play(soundID_bonus, 0.4f, 0.4f, 1, 0, 1);
    }

    public static void playMalus() {
        soundPool.play(soundID_malus, 0.4f, 0.4f, 1, 0, 1);
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {

        SharedPreferences sharedSettings = getSharedPreferences(settings,0);
        List<Score> scoreStrings = new ArrayList<>();
        String scores = sharedSettings.getString("highScores", "");
        String[] exScores = scores.split("\\|");

        for(String eSc : exScores){
            String[] parts = eSc.split(" - ");
            scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
        }

        DateFormat dateForm = new SimpleDateFormat("dd/mm/yyyy");
        String dateOutput = dateForm.format(new Date());
        Score newScore = new Score(dateOutput,highScore);
        scoreStrings.add(newScore);
        Collections.sort(scoreStrings);

        StringBuilder scoreBuild = new StringBuilder("");
        for(int s=0; s<scoreStrings.size(); s++){
            if(s>=10) break; // seulement 10 HS
            if(s>0) scoreBuild.append("|"); // La barre verticale sépare les scores au sein du string
            scoreBuild.append(scoreStrings.get(s).getScoreText());
        }

        SharedPreferences.Editor editor = sharedSettings.edit();
        editor.putString(highScores, scoreBuild.toString());
        editor.commit();
    }
}
