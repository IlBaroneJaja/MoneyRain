package be.ecam.moneyrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_start;
    private Button btn_scores;
    private Button btn_settings;
    public static final String settings = "sharedSettings";

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


        Intent backgroundMusic = new Intent(this, BackgroundSoundService.class);
        startService(backgroundMusic);

        SharedPreferences sharedSettings = getSharedPreferences(settings,0);
        SharedPreferences.Editor editor = sharedSettings.edit();

        editor.putString("level","BEGGAR"); // on set la difficulté au minimum au démarrage de l'appli, à défaut de ne pas avoir changé les préférences
        editor.commit();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btn_start:
                Intent intent = new Intent(StartUpActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_scores:
                intent = new Intent(StartUpActivity.this, ScoreActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_settings:
                intent = new Intent(StartUpActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}