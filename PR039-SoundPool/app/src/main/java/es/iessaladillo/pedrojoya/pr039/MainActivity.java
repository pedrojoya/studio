package es.iessaladillo.pedrojoya.pr039;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final float NORMAL_RATE = 1f;
    private static final int MAX_PRIORITY = 1;
    private static final float MAX_VOLUME = 1f;
    private static final int NO_LOOP = 0;
    private static final int NORMAL_QUALITY = 0;
    private static final int NORMAL_PRIORITY = 1;
    private static final int MAX_STREAMS = 8;

    private SoundPool player;
    private int shotId;
    private int explosionId;

    private ImageView imgShoot;
    private ImageView imgExplode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupSoundPool();
    }

    private void initViews() {
        imgShoot = ActivityCompat.requireViewById(this, R.id.imgShoot);
        imgExplode = ActivityCompat.requireViewById(this, R.id.imgExplode);

        imgShoot.setOnClickListener(view -> shoot());
        imgExplode.setOnClickListener(view -> explosion());
        // Initially disabled until sounds loaded.
        imgShoot.setEnabled(false);
        imgExplode.setEnabled(false);
    }

    private void setupSoundPool() {
        player = buildSoundPool();
        shotId = player.load(this, R.raw.shot, NORMAL_PRIORITY);
        explosionId = player.load(this, R.raw.explosion, NORMAL_PRIORITY);
        player.setOnLoadCompleteListener((soundPool, sampleId, status) -> enableButton(sampleId));
    }

    private SoundPool buildSoundPool() {
        SoundPool soundPool;
        // Builder pattern in API 21+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_NOTIFICATION,
                    NORMAL_QUALITY);
        }
        return soundPool;
    }

    private void enableButton(int sampleId) {
        if (sampleId == shotId) {
            imgShoot.setEnabled(true);
        } else if (sampleId == explosionId) {
            imgExplode.setEnabled(true);
        }
    }

    private void shoot() {
        player.play(shotId, MAX_VOLUME, MAX_VOLUME, MAX_PRIORITY,
                NO_LOOP, NORMAL_RATE);
    }

    private void explosion() {
        player.play(explosionId, MAX_VOLUME, MAX_VOLUME,
                MAX_PRIORITY, NO_LOOP, NORMAL_RATE);
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.release();
            player = null;
        }
        super.onDestroy();
    }

}