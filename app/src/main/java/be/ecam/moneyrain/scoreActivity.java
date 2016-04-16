package be.ecam.moneyrain;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScoreActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    public static final String GAME_SCORES = "highScores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
//      prefs = getSharedPreferences(GAME_SCORES,0);

    }
}
