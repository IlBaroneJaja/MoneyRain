package be.ecam.moneyrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_level;
    private Button btn_reset;
    private Switch switch_sound;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_level = (Button) findViewById(R.id.btn_level);
        btn_reset = (Button) findViewById(R.id.btn_resetHighScore);
        switch_sound = (Switch) findViewById(R.id.switchSound);
        btn_back = (Button) findViewById(R.id.btn_back);

        btn_level.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        switch_sound.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        SharedPreferences settings = getSharedPreferences("sharedSettings",0);
        btn_level.setText(settings.getString("level","BEGGAR"));

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){}
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_level:
                final Spinner spLevel = (Spinner) findViewById(R.id.levelDropDown);
                spLevel.performClick();
                spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        btn_level.setText(spLevel.getItemAtPosition(position).toString());
                        SharedPreferences settings = getSharedPreferences("sharedSettings",0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("level",btn_level.getText().toString());
                        editor.commit();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;
            case R.id.btn_resetHighScore:
                Toast.makeText(this,"High score succesfully reset", Toast.LENGTH_LONG).show();
                break;
            case R.id.switchSound:
                if (!switch_sound.isChecked())
                {
                    stopService(new Intent(this, BackgroundSoundService.class));
                    SharedPreferences sharedSettings = getSharedPreferences("sharedSettings",0);
                    SharedPreferences.Editor editor = sharedSettings.edit();
                    editor.putBoolean("sound",false);
                    editor.commit();
                }
                else {
                    startService(new Intent(this, BackgroundSoundService.class));
                    SharedPreferences sharedSettings = getSharedPreferences("sharedSettings",0);
                    SharedPreferences.Editor editor = sharedSettings.edit();
                    editor.putBoolean("sound",true);
                    editor.commit();
                }
            case R.id. btn_back:
                Intent intent = new Intent(SettingsActivity.this, StartUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
