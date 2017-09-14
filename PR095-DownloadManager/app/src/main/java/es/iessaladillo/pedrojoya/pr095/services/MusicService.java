package es.iessaladillo.pedrojoya.pr095.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class MusicService extends Service implements
        OnCompletionListener, OnPreparedListener {

    public static final String EXTRA_SONG_PATH = "EXTRA_SONG_PATH";
    public static final String ACTION_SONG_COMPLETED = "es.iessaladillo.pedrojoya.pr089.action_song_completed";

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prepareMediaPlayer(intent);
        return START_NOT_STICKY;
    }

    private void prepareMediaPlayer(Intent intent) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.setLooping(false);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            String pathCancion = intent.getStringExtra(EXTRA_SONG_PATH);
            try {
                mediaPlayer.setDataSource(pathCancion);
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // Not binded service.
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        Intent completionIntent = new Intent(ACTION_SONG_COMPLETED);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(completionIntent);
        stopSelf();
    }
}
