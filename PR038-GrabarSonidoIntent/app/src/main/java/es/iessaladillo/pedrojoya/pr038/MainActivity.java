package es.iessaladillo.pedrojoya.pr038;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MainActivity extends AppCompatActivity implements OnPreparedListener,
        OnCompletionListener {

    private static final int RC_GRABAR = 0;

    @BindView(R.id.btnPlay)
    ImageButton btnPlay;
    @BindView(R.id.btnPause)
    ImageButton btnPause;
    @BindView(R.id.btnStop)
    ImageButton btnStop;
    @BindView(R.id.btnGrabar)
    ImageButton btnGrabar;
    @BindView(R.id.skbBarra)
    SeekBar skbBarra;

    @State
    boolean mIsPlaying;
    @State
    Uri mUriGrabacion;
    @State
    boolean mBtnPlayEnabled;

    private MediaPlayer mReproductor;
    private boolean enPausa;
    private final Handler mManejador = new Handler();
    private Runnable mActualizacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Icepick.restoreInstanceState(this, savedInstanceState);
        initVistas();
    }

    private void initVistas() {
        skbBarra.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Si ha sido el usuario el que ha cambiado la barra.
                if (fromUser && mReproductor != null && mReproductor.isPlaying()) {
                    // Coloco el mReproductor en esa posición.
                    mReproductor.seekTo(seekBar.getProgress());
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
        btnPlay.setEnabled(mBtnPlayEnabled);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @OnClick(R.id.btnGrabar)
    public void btnGrabarOnClick() {
        // Se deshabilitan los botones de reproducción.
        btnPlay.setEnabled(mBtnPlayEnabled = false);
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        // Se envía un intent para grabar sonido.
        Intent i = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(i, RC_GRABAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_GRABAR) {
            // Se obtiene la uri de la grabación.
            mUriGrabacion = data.getData();
        }
        btnPlay.setEnabled(mBtnPlayEnabled = true);
    }

    @OnClick(R.id.btnPlay)
    public void btnPlayOnClick() {
        // Si es una nueva reproducción, se inicia.
        prepararReproductor();
    }

    @OnClick(R.id.btnPause)
    public void btnPauseOnClick() {
        if (mReproductor != null) {
            if (!enPausa) {
                // Si no se estaba en modo pausa, se pausa el reproductor.
                mReproductor.pause();
                btnPause.setImageResource(R.drawable.ic_play_arrow);
            } else {
                // Si ya estaba en modo pausa, se continua la reproducción.
                mReproductor.start();
                btnPause.setImageResource(R.drawable.ic_pause);
                actualizarProgreso();
            }
            enPausa = !enPausa;
        }
    }

    @OnClick(R.id.btnStop)
    public void btnStopOnClick() {
        if (mReproductor != null) {
            // Se para la reproducción.
            mReproductor.stop();
            enPausa = false;
            // Se coloca la barra al principio y se eliminan los callbacks.
            skbBarra.setProgress(0);
            mManejador.removeCallbacks(mActualizacion);
            // Se deshabilitan los botones de Pause y de Parar.
            btnPause.setEnabled(false);
            btnPause.setImageResource(R.drawable.ic_pause);
            btnStop.setEnabled(false);
            // Se habilita el botón de grabar.
            btnGrabar.setEnabled(true);
        }
    }

    // Prepara al reproductor para poder reproducir.
    private void prepararReproductor() {
        // Si hay canción para reproducir.
        if (mUriGrabacion != null) {
            // Si ya se tenía reproductor, se elimina.
            if (mReproductor != null) {
                mReproductor.reset();
                mReproductor.release();
                mReproductor = null;
            }
            mReproductor = new MediaPlayer();
            try {
                mReproductor.setDataSource(this, mUriGrabacion);
                mReproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mReproductor.setOnPreparedListener(this);
                mReproductor.setOnCompletionListener(this);
                mReproductor.prepareAsync(); // asíncrona.
            } catch (Exception e) {
                Log.d("Reproductor", "ERROR: " + e.getMessage());
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer repr) {
        // Se establece el máximo de la seekbar a la duración de la canción y se coloca la barra
        // al principio.
        skbBarra.setMax(mReproductor.getDuration());
        skbBarra.setProgress(0);
        // No se está en modo pausa.
        enPausa = false;
        // Se comienza la reproducción.
        repr.start();
        // Se actualiza el hilo de notificación para actualizar la barra.
        actualizarProgreso();
        // Se actualiza el estado de los botones.
        btnPause.setEnabled(true);
        btnPause.setImageResource(R.drawable.ic_pause);
        btnStop.setEnabled(true);
        btnGrabar.setEnabled(false);
    }

    // Actualiza la barra en base al progreso del contenido del reproductor.
    private void actualizarProgreso() {
        skbBarra.setProgress(mReproductor.getCurrentPosition());
        if (mReproductor.isPlaying()) {
            // Se actualiza la barra transcurrido medio segundo.
            mActualizacion = new Runnable() {
                public void run() {
                    actualizarProgreso();
                }
            };
            mManejador.postDelayed(mActualizacion, 500);
        } else {
            if (!enPausa) {
                skbBarra.setProgress(0);
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        skbBarra.setProgress(0);
        mManejador.removeCallbacks(mActualizacion);
        // Se actualiza el estado de los botones.
        btnPause.setEnabled(false);
        btnPause.setImageResource(R.drawable.ic_pause);
        btnStop.setEnabled(false);
        btnGrabar.setEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Se liberan los recursos del mReproductor y el propio objeto.
        if (mReproductor != null) {
            mReproductor.release();
            mReproductor = null;
        }
        // Se eliminan los callbacks del manejador.
        mManejador.removeCallbacks(mActualizacion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        skbBarra.setProgress(0);
    }

}
