package be.ecam.moneyrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id. btn_back:
                Intent intent = new Intent(ScoreActivity.this, StartUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
