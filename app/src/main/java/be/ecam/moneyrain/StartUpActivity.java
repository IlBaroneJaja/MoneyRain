package be.ecam.moneyrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_start;
    private Button btn_scores;
    private Button btn_settings;

    static SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;
    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;
    static int soundID_blop, soundID_coin, soundID_bomb, soundID_bonus, soundID_malus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_scores = (Button) findViewById(R.id.btn_scores);
        btn_settings = (Button) findViewById(R.id.btn_settings);
        btn_start.setOnClickListener(this);
        btn_scores.setOnClickListener(this);
        btn_settings.setOnClickListener(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        createSound();
        loadSounds();

        Intent backgroundMusic = new Intent(this, BackgroundSoundService.class);


        SharedPreferences sharedSettings = getSharedPreferences("sharedSettings",0);
        Boolean sound = sharedSettings.getBoolean("sound",true);
        if (sound)
            startService(backgroundMusic);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btn_start:
                Intent intent = new Intent(this, GameActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                playBonus();
                stopService(new Intent(this, BackgroundSoundService.class));
                startActivity(intent);
                finish();
                break;
            case R.id.btn_scores:
                intent = new Intent(StartUpActivity.this, ScoreActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                playBonus();
                stopService(new Intent(this, BackgroundSoundService.class));
                finish();
                startActivity(intent);
                break;
            case R.id.btn_settings:
                intent = new Intent(StartUpActivity.this, SettingsActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                playBonus();
                stopService(new Intent(this, BackgroundSoundService.class));
                finish();
                startActivity(intent);
                break;
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

}