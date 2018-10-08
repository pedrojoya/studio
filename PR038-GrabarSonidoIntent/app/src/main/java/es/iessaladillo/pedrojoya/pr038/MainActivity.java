package es.iessaladillo.pedrojoya.pr038;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements OnPreparedListener,
        OnCompletionListener {

    private static final int RC_RECORD = 0;
    private static final int RC_SELECT = 1;

    private static final String STATE_IS_PLAYING = "STATE_IS_PLAYING";
    private static final String STATE_RECORDING_URI = "STATE_RECORDING_URI";
    private static final String STATE_BUTTON_ENABLED = "STATE_BUTTON_ENABLED";

    private ImageButton btnPlay;
    private ImageButton btnPause;
    private ImageButton btnStop;
    private ImageButton btnRecord;
    private ImageButton btnSelect;
    private SeekBar skbTime;
    private TextView lblName;

    private boolean isPlaying;
    private Uri recordUri;
    private boolean isPlayEnabled;

    private MediaPlayer mediaPlayer;
    private boolean isPaused;
    private final Handler handler = new Handler();
    private Runnable updateTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreInstanceState(savedInstanceState);
        initViews();
    }

    private void initViews() {
        btnPlay = ActivityCompat.requireViewById(this, R.id.btnPlay);
        btnPause = ActivityCompat.requireViewById(this, R.id.btnPause);
        btnStop = ActivityCompat.requireViewById(this, R.id.btnStop);
        btnRecord = ActivityCompat.requireViewById(this, R.id.btnRecord);
        btnSelect = ActivityCompat.requireViewById(this, R.id.btnSelect);
        skbTime = ActivityCompat.requireViewById(this, R.id.skbTime);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);

        btnRecord.setOnClickListener(v -> record());
        btnSelect.setOnClickListener(v -> select());
        btnPlay.setOnClickListener(v -> play());
        btnPause.setOnClickListener(v -> pause());
        btnStop.setOnClickListener(v -> stop());

        skbTime.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // If user changed position
                if (fromUser && mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

        });
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        btnPlay.setEnabled(isPlayEnabled);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_IS_PLAYING, isPlaying);
        outState.putParcelable(STATE_RECORDING_URI, recordUri);
        outState.putBoolean(STATE_BUTTON_ENABLED, isPlayEnabled);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isPlaying = savedInstanceState.getBoolean(STATE_IS_PLAYING, false);
            recordUri = savedInstanceState.getParcelable(STATE_RECORDING_URI);
            isPlayEnabled = savedInstanceState.getBoolean(STATE_BUTTON_ENABLED, true);
        }
    }

    public void record() {
        btnPlay.setEnabled(isPlayEnabled = false);
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        Intent i = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        try {
            startActivityForResult(i, RC_RECORD);
        } catch (Exception e) {
            Snackbar.make(skbTime, R.string.main_activity_no_activity, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void select() {
        btnPlay.setEnabled(isPlayEnabled = false);
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        // Se envía un intent para seleccionar sonido.
        //        Intent i = new Intent(Intent.ACTION_PICK,
        //                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        //        i.setType("audio/*");
        //        try {
        //            startActivityForResult(i, RC_SELECT);
        //        } catch (Exception e) {
        //            Snackbar.make(skbTime, R.string.no_hay, Snackbar.LENGTH_SHORT).show();
        //        }
        selectAudio();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_RECORD) {
                // Obtain record uri.
                recordUri = data.getData();
                btnPlay.setEnabled(isPlayEnabled = true);
                lblName.setText(recordUri.getLastPathSegment());
            } else if (requestCode == RC_SELECT) {
                // Obtain file uri.
                // recordUri = Uri.parse(getRealPath(data.getData()));
                recordUri = data.getData();
                btnPlay.setEnabled(isPlayEnabled = true);
                lblName.setText(getFileName(recordUri));
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void play() {
        preparePlayer();
    }

    public void pause() {
        if (mediaPlayer != null) {
            if (!isPaused) {
                mediaPlayer.pause();
                btnPause.setImageResource(R.drawable.ic_play_arrow);
            } else {
                mediaPlayer.start();
                btnPause.setImageResource(R.drawable.ic_pause);
                updateTimeBar();
            }
            isPaused = !isPaused;
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPaused = false;
            skbTime.setProgress(0);
            handler.removeCallbacks(updateTime);
            btnPause.setEnabled(false);
            btnPause.setImageResource(R.drawable.ic_pause);
            btnStop.setEnabled(false);
            btnRecord.setEnabled(true);
            btnSelect.setEnabled(true);
        }
    }

    private void preparePlayer() {
        if (recordUri != null) {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this, recordUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.prepareAsync(); // asíncrona.
            } catch (Exception e) {
                Log.d("MediaPlayer", "ERROR: " + e.getMessage());
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer repr) {
        skbTime.setMax(mediaPlayer.getDuration());
        skbTime.setProgress(0);
        isPaused = false;
        repr.start();
        updateTimeBar();
        btnPause.setEnabled(true);
        btnPause.setImageResource(R.drawable.ic_pause);
        btnStop.setEnabled(true);
        btnRecord.setEnabled(false);
        btnSelect.setEnabled(false);
    }

    private void updateTimeBar() {
        skbTime.setProgress(mediaPlayer.getCurrentPosition());
        updateTime = this::updateTimeBar;
        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(updateTime, 500);
        } else {
            if (!isPaused) {
                skbTime.setProgress(0);
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        skbTime.setProgress(0);
        handler.removeCallbacks(updateTime);
        btnPause.setEnabled(false);
        btnPause.setImageResource(R.drawable.ic_pause);
        btnStop.setEnabled(false);
        btnRecord.setEnabled(true);
        btnSelect.setEnabled(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        skbTime.setProgress(0);
    }

    private String getRealPath(Uri uriGaleria) {
        String[] filePath = {MediaStore.Audio.Media.DATA};
        Cursor c = getContentResolver().query(uriGaleria, filePath, null, null, null);
        String path = "";
        if (c != null) {
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            path = c.getString(columnIndex);
            c.close();
        }
        return path;
    }

    @WithPermissions(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE})
    void selectAudio() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        try {
            startActivityForResult(i, RC_SELECT);
        } catch (Exception e) {
            Snackbar.make(skbTime, R.string.main_activity_no_audio_selection_activity, Snackbar.LENGTH_SHORT).show();
        }
    }

}
