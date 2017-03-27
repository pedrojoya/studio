package es.iessaladillo.pedrojoya.pr038;

import android.Manifest;
import android.app.Activity;
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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal", "EmptyMethod"})
public class MainActivity extends AppCompatActivity implements OnPreparedListener,
        OnCompletionListener {

    private static final int RC_GRABAR = 0;
    private static final int RC_SELECCIONAR_AUDIO = 1;

    @BindView(R.id.btnPlay)
    ImageButton btnPlay;
    @BindView(R.id.btnPause)
    ImageButton btnPause;
    @BindView(R.id.btnStop)
    ImageButton btnStop;
    @BindView(R.id.btnGrabar)
    ImageButton btnGrabar;
    @BindView(R.id.btnSeleccionar)
    ImageButton btnSeleccionar;
    @BindView(R.id.skbBarra)
    SeekBar skbBarra;
    @BindView(R.id.lblNombre)
    TextView lblNombre;

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
        try {
            startActivityForResult(i, RC_GRABAR);
        } catch (Exception e) {
            Snackbar.make(skbBarra, R.string.no_hay, Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnSeleccionar)
    public void btnSeleccionarOnClick() {
        // Se deshabilitan los botones de reproducción.
        btnPlay.setEnabled(mBtnPlayEnabled = false);
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        // Se envía un intent para seleccionar sonido.
//        Intent i = new Intent(Intent.ACTION_PICK,
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//        i.setType("audio/*");
//        try {
//            startActivityForResult(i, RC_SELECCIONAR_AUDIO);
//        } catch (Exception e) {
//            Snackbar.make(skbBarra, R.string.no_hay, Snackbar.LENGTH_SHORT).show();
//        }
        MainActivityPermissionsDispatcher.solicitarAudioWithCheck(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_GRABAR) {
                // Se obtiene la uri de la grabación.
                mUriGrabacion = data.getData();
                btnPlay.setEnabled(mBtnPlayEnabled = true);
                lblNombre.setText(mUriGrabacion.getLastPathSegment());
            } else if (requestCode == RC_SELECCIONAR_AUDIO) {
                // Se obtiene la Uri real del archivo.
//                mUriGrabacion = Uri.parse(getRealPath(data.getData()));
                mUriGrabacion = data.getData();
                btnPlay.setEnabled(mBtnPlayEnabled = true);
                lblNombre.setText(getFileName(mUriGrabacion));
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
            btnSeleccionar.setEnabled(true);
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
        btnSeleccionar.setEnabled(false);
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
        btnSeleccionar.setEnabled(true);

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

    // Obtiene el path real de un audio a partir de la URI de Galería obtenido
    // con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        // Se consulta en el content provider de la galería.
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

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void solicitarAudio() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        try {
            startActivityForResult(i, RC_SELECCIONAR_AUDIO);
        } catch (Exception e) {
            Snackbar.make(skbBarra, R.string.no_hay_audio, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onShowRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onPermissionDenied() {
        Snackbar.make(btnSeleccionar, R.string.permission_denied,
                Snackbar.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onNeverAskAgain() {
        Snackbar.make(btnSeleccionar, R.string.permission_neverask,
                Snackbar.LENGTH_LONG).setAction(R.string.configurar, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInstalledAppDetailsActivity(MainActivity.this);
            }
        }).show();
    }

    public static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
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

}
