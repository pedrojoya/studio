package es.iessaladillo.pedrojoya.pr037;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import java.io.IOException;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements OnTouchListener,
        OnPreparedListener, OnCompletionListener, OnInfoListener {

    // Constantes.
    private static final int MAX_DURACION_MS = 10000;

    // Vistas.
    private ImageView btnRec;

    // Variables.
    private MediaPlayer mReproductor;
    private MediaRecorder mGrabadora;
    private boolean mGrabando = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRec = (ImageView) this.findViewById(R.id.btnRec);
        // La actividad actuará de listener mientras se esté pulsando el botón.
        if (btnRec != null) {
            btnRec.setOnTouchListener(this);
        }
        MainActivityPermissionsDispatcher.configButtonWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})
    public void configButton() {
        btnRec.setEnabled(true);
    }

    // Al pulsar el botón.
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Si no se estaba grabando, se inicia la grabación.
        if (!mGrabando) {
            iniciarGrabacion();
        }
        // Si se suelta el botón, se finaliza la grabación.
        if (event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL) {
            pararGrabacion();
        }
        return false;
    }

    // Inicia la grabación.
    private void iniciarGrabacion() {
        MainActivityPermissionsDispatcher.prepararGrabacionWithCheck(this);
        mGrabadora.start();
        cambiarEstadoGrabacion(true);
    }

    // Prepara la grabación.
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})
    public void prepararGrabacion() {
        // Se crea el objeto grabadora.
        mGrabadora = new MediaRecorder();
        // Se configura la grabación con fichero de salida, origen, formato,
        // tipo de codificación y duración máxima.
        String pathGrabacion = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/audio.3gp";
        mGrabadora.setOutputFile(pathGrabacion);
        mGrabadora.setAudioSource(MediaRecorder.AudioSource.MIC);
        mGrabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mGrabadora.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mGrabadora.setMaxDuration(MAX_DURACION_MS);
        mGrabadora.setOnInfoListener(this);
        // Se prepara la grabadora (de forma síncrona).
        try {
            mGrabadora.prepare();
        } catch (IOException e) {
            Log.e(getString(R.string.app_name), "Fallo en grabación");
        }
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})
    void showRationaleForWriteExternalStorage(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage(
                R.string.permission_write_external_storage_rationale).setPositiveButton(
                R.string.permitir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                }).setNegativeButton(R.string.rechazar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                }).show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})
    void showDeniedForWriteExternalStorage() {
        new AlertDialog.Builder(this).setMessage(
                R.string.lo_sentimos).setPositiveButton(
                R.string.permitir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivityPermissionsDispatcher.configButtonWithCheck(MainActivity.this);
                    }
                }).setNegativeButton(R.string.cerrar_app, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})
    void showNeverAskForWriteExternalStorage() {
        new AlertDialog.Builder(this).setMessage(
                R.string.lo_sentimos).setPositiveButton(
                R.string.permitir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startInstalledAppDetailsActivity(MainActivity.this);
                        finish();
                    }
                }).setNegativeButton(R.string.cerrar_app, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();



        Snackbar.make(btnRec, R.string.permission_write_external_storage_neverask,
                Snackbar.LENGTH_LONG).setAction(R.string.configurar, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startInstalledAppDetailsActivity(MainActivity.this);
                    }
                }).show();
    }

    private static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        // Para que deje rastro en la pila de actividades se añaden flags.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
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
        if (mGrabadora != null) {
            try {
                mGrabadora.stop();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mGrabadora.release();
                mGrabadora = null;
            }
        }
        // Se cambia el estado de grabación y el icono del botón.
        cambiarEstadoGrabacion(false);
        // Se prepara la reproducción.
        prepararReproductor();
    }

    // Cambia el estado de grabación.
    private void cambiarEstadoGrabacion(boolean estaGrabando) {
        mGrabando = estaGrabando;
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_micro);
        drawable = DrawableCompat.wrap(drawable);
        if (mGrabando) {
            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            DrawableCompat.setTintList(drawable, null);
        }
        btnRec.setImageDrawable(drawable);
    }

    // Prepara al reproductor para poder reproducir.
    private void prepararReproductor() {
        // Si ya existía reproductor, se elimina.
        if (mReproductor != null) {
            mReproductor.reset();
            mReproductor.release();
            mReproductor = null;
        }
        // Se crea el objeto reproductor.
        mReproductor = new MediaPlayer();
        try {
            // Path de la grabación.
            mReproductor.setDataSource(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/audio.3gp");
            // Stream de audio que utilizará el reproductor.
            mReproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // Volumen
            mReproductor.setVolume(1.0f, 1.0f);
            // La actividad actuará como listener cuando el reproductor ya esté
            // preparado para reproducir y cuando se haya finalizado la
            // reproducción.
            mReproductor.setOnPreparedListener(this);
            mReproductor.setOnCompletionListener(this);
            // Se prepara el reproductor.
            // reproductor.prepare(); // síncrona.
            mReproductor.prepareAsync(); // asíncrona.
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
        if (mReproductor != null) {
            mReproductor.release();
            mReproductor = null;
        }
        // Se liberan los recursos de la grabadora.
        if (mGrabadora != null) {
            mGrabadora.release();
            mGrabadora = null;
        }
    }

}
