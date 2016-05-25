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
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_back;
    public static final String highScores = "highScores";
    public static final String settings = "sharedSettings";

    static SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;
    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;
    static int soundID_blop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences sharedSettings = getSharedPreferences(settings,0);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        Boolean sound = sharedSettings.getBoolean("sound",true);
        if (sound)
            startService(new Intent(this, BackgroundSoundService.class));


        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        createSound();
        loadSounds();

        TextView highScoreList = (TextView) findViewById(R.id.highScoreList);
        String[] savedScores = sharedSettings.getString("highScores", "").split("\\|");

        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores){
            scoreBuild.append(score+"\n");
        }

        highScoreList.setText(scoreBuild.toString());
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedSettings = getSharedPreferences(settings,0);
        Boolean sound = sharedSettings.getBoolean("sound",true);
        switch(view.getId())
        {
            case R.id. btn_back:
                Intent intent = new Intent(ScoreActivity.this, StartUpActivity.class);
//              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if(sound)
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
    }

    public static void playBlop() {
        soundPool.play(soundID_blop, 0.5f, 0.5f, 1, 0, 1);
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
