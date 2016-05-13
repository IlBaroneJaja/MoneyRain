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
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btn_start:
                Intent intent = new Intent(this, GameActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
                break;
            case R.id.btn_scores:
                intent = new Intent(StartUpActivity.this, ScoreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                break;
            case R.id.btn_settings:
                intent = new Intent(StartUpActivity.this, SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                break;
        }
    }

}