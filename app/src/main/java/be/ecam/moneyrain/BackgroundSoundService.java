package be.ecam.moneyrain;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Alessandro on 16/04/2016.
 */
public class BackgroundSoundService extends Service {

    private static final String TAG = null;
    int volume = 20; // exprimé en pourcentage de 0 à 100

    MediaPlayer player; //= new MediaPlayer();

    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.song);
        player.setLooping(true);
        player.setVolume(0.4f,0.4f);

        player.start();


    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {

    }
    public IBinder onUnBind(Intent arg0) {
        return null;
    }

    public void onStop() {
        player.stop();
    }
    public void onPause() {
        player.pause();
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }


}
