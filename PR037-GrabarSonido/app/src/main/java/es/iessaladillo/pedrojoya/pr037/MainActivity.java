package es.iessaladillo.pedrojoya.pr037;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnTouchListener,
        OnPreparedListener, OnCompletionListener, OnInfoListener {

    // Constantes.
    private static final int MAX_DURACION_MS = 10000;

    // Vistas.
    private ImageView btnRec;

    // Variables.
    private MediaPlayer reproductor;
    private MediaRecorder grabadora;
    private boolean grabando = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRec = (ImageView) this.findViewById(R.id.btnRec);
        // La actividad actuará de listener mientras se esté pulsando el botón.
        btnRec.setOnTouchListener(this);
    }

    // Al pulsar el botón.
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Si no se estaba grabando, se inicia la grabación.
        if (!grabando) {
            iniciarGrabacion();
        }
        // Si se suelta el botón, se finaliza la grabación.
        if ((event.getAction() == MotionEvent.ACTION_UP ||
                event.getAction() == MotionEvent.ACTION_CANCEL)) {
            pararGrabacion();
        }
        return false;
    }

    // Inicia la grabación.
    private void iniciarGrabacion() {
        prepararGrabacion();
        grabadora.start();
        cambiarEstadoGrabacion(true);
    }

    // Prepara la grabación.
    private void prepararGrabacion() {
        // Se crea el objeto grabadora.
        grabadora = new MediaRecorder();
        // Se configura la grabación con fichero de salida, origen, formato,
        // tipo de codificación y duración máxima.
        String pathGrabacion = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/audio.3gp";
        grabadora.setOutputFile(pathGrabacion);
        grabadora.setAudioSource(MediaRecorder.AudioSource.MIC);
        grabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        grabadora.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        grabadora.setMaxDuration(MAX_DURACION_MS);
        grabadora.setOnInfoListener(this);
        // Se prepara la grabadora (de forma síncrona).
        try {
            grabadora.prepare();
        } catch (IOException e) {
            Log.e(getString(R.string.app_name), "Fallo en grabación");
        }
    }

    // Cuando se ha producido un evento de información en la grabador.
    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        // Si se ya llegado al tiempo máximo.
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED
                || what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {
            // Se cambia el icono del botón para que el usuario se de cuenta de
            // que ya no está grabando.
            btnRec.setImageResource(R.drawable.ic_micro);
        }
    }

    // Detiene la grabación en curso.
    private void pararGrabacion() {
        // Se detiene la grabación y se liberan los recursos de la grabadora.
        if (grabadora != null) {
            try {
                grabadora.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } finally {
                grabadora.release();
                grabadora = null;
            }
        }
        // Se cambia el estado de grabación y el icono del botón.
        cambiarEstadoGrabacion(false);
        // Se prepara la reproducción.
        prepararReproductor();
    }

    // Cambia el estado de grabación.
    private void cambiarEstadoGrabacion(boolean estaGrabando) {
        grabando = estaGrabando;
        btnRec.setImageResource(estaGrabando ? R.drawable.ic_micro_recording
                : R.drawable.ic_micro);
    }

    // Prepara al reproductor para poder reproducir.
    private void prepararReproductor() {
        // Si ya existía reproductor, se elimina.
        if (reproductor != null) {
            reproductor.reset();
            reproductor.release();
            reproductor = null;
        }
        // Se crea el objeto reproductor.
        reproductor = new MediaPlayer();
        try {
            // Path de la grabación.
            reproductor.setDataSource(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/audio.3gp");
            // Stream de audio que utilizará el reproductor.
            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // Volumen
            reproductor.setVolume(1.0f, 1.0f);
            // La actividad actuará como listener cuando el reproductor ya esté
            // preparado para reproducir y cuando se haya finalizado la
            // reproducción.
            reproductor.setOnPreparedListener(this);
            reproductor.setOnCompletionListener(this);
            // Se prepara el reproductor.
            // reproductor.prepare(); // síncrona.
            reproductor.prepareAsync(); // asíncrona.
        } catch (Exception e) {
            Log.d("Reproductor", "ERROR: " + e.getMessage());
        }
    }

    // Cuando el reproductor ya está preparado para reproducir.
    @Override
    public void onPrepared(MediaPlayer repr) {
        // Se inicia la reproducción.
        repr.start();
        // Se desactiva el botón de grabación.
        btnRec.setEnabled(false);
    }

    // Cuando ha finalizado la reproducción.
    @Override
    public void onCompletion(MediaPlayer arg0) {
        // Se desactiva el botón de grabación.
        btnRec.setEnabled(true);
    }

    // Cuando se pausa la actividad.
    @Override
    protected void onPause() {
        super.onPause();
        // Se liberan los recursos del reproductor.
        if (reproductor != null) {
            reproductor.release();
            reproductor = null;
        }
        // Se liberan los recursos de la grabadora.
        if (grabadora != null) {
            grabadora.release();
            grabadora = null;
        }
    }

}