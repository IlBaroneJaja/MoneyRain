package be.ecam.moneyrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_back;
    public static final String highScores = "highScores";
    public static final String settings = "sharedSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences sharedSettings = getSharedPreferences(settings,0);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

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
        switch(view.getId())
        {
            case R.id. btn_back:
                Intent intent = new Intent(ScoreActivity.this, StartUpActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
}
