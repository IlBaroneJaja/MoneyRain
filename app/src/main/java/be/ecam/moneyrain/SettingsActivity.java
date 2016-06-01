package be.ecam.moneyrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String settings = "sharedSettings";
    public static final String highScores = "highScores";

    private Button btn_reset;
    private Switch switch_sound;
    private Button btn_back;
    private Spinner spLevel = null;

    static SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;
    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;
    static int soundID_blop, soundID_coin, soundID_bomb, soundID_bonus, soundID_malus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedSettings = getSharedPreferences(settings, 0);

        btn_reset = (Button) findViewById(R.id.btn_resetHighScore);
        switch_sound = (Switch) findViewById(R.id.switchSound);
        btn_back = (Button) findViewById(R.id.btn_back);

        btn_reset.setOnClickListener(this);
        switch_sound.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        spLevel = (Spinner) findViewById(R.id.levelDropDown);
        List<String> levelValue = Arrays.asList(getResources().getStringArray(R.array.levelArray));
        String level = sharedSettings.getString("level","BEGGAR");
        int levelPosition = levelValue.indexOf(level);
        spLevel.setSelection(levelPosition,false);
        spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    String level = adapterView.getItemAtPosition(position).toString();
                    SharedPreferences sharedSettings = getSharedPreferences(settings, 0);
                    SharedPreferences.Editor editor = sharedSettings.edit();
                    editor.putString("level", level);
                    editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        sharedSettings = getSharedPreferences(settings,0);
        Boolean sound = sharedSettings.getBoolean("sound",true);
        if (sound)
            startService(new Intent(this, BackgroundSoundService.class));

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        createSound();
        loadSounds();

        switch_sound.setChecked(sound);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){}
    @Override
    public void onClick(View view) {
        SharedPreferences sharedSettings = getSharedPreferences(settings,0);
        Boolean sound = sharedSettings.getBoolean("sound",true);
        switch(view.getId())
        {
            case R.id.levelDropDown:
                spLevel.performClick();
                break;
            case R.id.btn_resetHighScore:
                Toast.makeText(this,"High score succesfully reset", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedSettings.edit();
                editor.putString(highScores,"");
                editor.commit();
                break;
            case R.id.switchSound:
                if (!switch_sound.isChecked())
                {
                    // OFF
                    stopService(new Intent(this, BackgroundSoundService.class));
                    sharedSettings = getSharedPreferences("sharedSettings", 0);
                    editor = sharedSettings.edit();
                    editor.putBoolean("sound",false);
                    editor.commit();
                }
                else {
                    // ON
                    startService(new Intent(this, BackgroundSoundService.class));
                    sharedSettings = getSharedPreferences("sharedSettings", 0);
                    editor = sharedSettings.edit();
                    editor.putBoolean("sound",true);
                    editor.commit();
                }
                break;
            case R.id. btn_back:
                Intent intent = new Intent(SettingsActivity.this, StartUpActivity.class);
                if (sound)
                    playBlop();
                stopService(new Intent(this, BackgroundSoundService.class));
                startActivity(intent);
                finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //Arret du servcice musique lorsque l'on clique sur la fleche retour de l'appareil
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            stopService(new Intent(this, BackgroundSoundService.class));
        }
        return super.onKeyDown(keyCode, event);
    }
}
