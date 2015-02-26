package es.iessaladillo.pedrojoya.pr122;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends ActionBarActivity implements
        PickOrCaptureDialogFragment.Listener,
        TextureView.SurfaceTextureListener, MediaController.MediaPlayerControl {

    private static final int RC_CAPTURAR_VIDEO = 0;
    private static final int RC_SELECCIONAR_VIDEO = 1;

    private static final String PREF_PATH_VIDEO = "prefPathVideo";
    private static final int OPTION_PICK = 0;
    private static final int DURACION_MAX_SEGUNDOS = 20;

    private static final String STATE_CURRENT_POSITION = "current_position";

    private String sPathVideoOriginal;
    private String sNombreArchivoPrivado;
    private int mPosicionInicial;

    private TextureView mTvVideo;
    private MediaPlayer mReproductor;
    private Controles mControles;
    private ActionBar mActionBar;
    private TextView mLblSinVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configActionBar();
        restaurarEstado(savedInstanceState);
        configReproductor();
        initVistas();
        restaurarPreferencias();
    }

    // Configura el reproductor.
    private void configReproductor() {
        mReproductor = new MediaPlayer();
        mReproductor.setLooping(false);
        mReproductor.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                actualizarDimensionesVisor();
                mediaPlayer.seekTo(mPosicionInicial);
                mControles.setEnabled(true);
                mControles.show();
            }
        });
        mControles = new Controles(this);
        mControles.setMediaPlayer(this);
        mControles.setAnchorView(this.findViewById(R.id.rlRaiz));
        mControles.setEnabled(true);
    }

    // Restaura las preferencias (path de la copia privada del vídeo).
    private void restaurarPreferencias() {
        // Se lee de las preferencias el path del archivo con el video recortado y privado.
        SharedPreferences preferencias = getSharedPreferences(getString(R.string.app_name),
                MODE_PRIVATE);
        String pathVideo = preferencias.getString(PREF_PATH_VIDEO, "");
        if (!TextUtils.isEmpty(pathVideo)) {
            cargarVideo(pathVideo);
        }
        else {
            // No hay vídeo que mostrar, por lo que se tan sólo se muestra
            // mensaje informativo.
            mLblSinVideo.setText(R.string.seleccione_un_video);
            mControles.setEnabled(false);
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        mTvVideo = (TextureView) findViewById(R.id.tvVideo);
        // Al hacer click sobre el visor, se muestran los controles.
        mTvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mControles.isEnabled()) {
                    mControles.show();
                }
            }
        });
        mTvVideo.setSurfaceTextureListener(this);
        mLblSinVideo = (TextView) findViewById(R.id.lblSinVideo);
        mLblSinVideo.setVisibility(View.VISIBLE);
    }

    // Restaura el estado previo al cambio de configuración (posición inicial de reproducción).
    private void restaurarEstado(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPosicionInicial = savedInstanceState.getInt(STATE_CURRENT_POSITION, 0);
        } else {
            mPosicionInicial = 0;
        }
    }

    // Configura la Action Bar para que tenga un fondo semistransparente.
    private void configActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(200, 0, 0, 0)));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Se guarda la posición actual de reproducción.
        outState.putInt(STATE_CURRENT_POSITION, mReproductor.getCurrentPosition());
    }

    // Actualiza las dimensiones del visor acorde al vídeo del reproductor.
    private void actualizarDimensionesVisor() {
        // Se obtiene la anchura y altura del vÌdeo y del visor.
        int anchuraVideo = mReproductor.getVideoWidth();
        int alturaVideo = mReproductor.getVideoHeight();
        int anchuraVisor = mTvVideo.getWidth();
        int alturaVisor = mTvVideo.getHeight();
        // Se calculan las proporciones de anchura y altura y la ratio.
        float proporcionAnchura = (float) anchuraVisor / (float) anchuraVideo;
        float proporcionAltura = (float) alturaVisor / (float) alturaVideo;
        float proporcionAspecto = (float) anchuraVideo / (float) alturaVideo;
        // Se actualizan las dimensiones del visorObtenemos los par·metros de anchura y altura de la ventana de reproducciÛn.
        ViewGroup.LayoutParams lp = mTvVideo.getLayoutParams();
        ViewGroup.LayoutParams lp2 = mLblSinVideo.getLayoutParams();
        if (proporcionAnchura > proporcionAltura) {
            // Si es más ancho que alto, la altura es la que ya tiene y se actualiza el ancho
            // manteniendo el ratio de aspecto.
            lp.height = alturaVisor;
            lp2.height = alturaVisor;
            lp.width = (int) (alturaVisor * proporcionAspecto);
            lp2.width = (int) (alturaVisor * proporcionAspecto);
        } else {
            // Si es más alto que ancho, la anchura es la que ya tiene y se actualiza el alto
            // manteniendo el ratio de aspecto.
            lp.width = anchuraVisor;
            lp2.width = anchuraVisor;
            lp.height = (int) (anchuraVisor / proporcionAspecto);
            lp2.height = (int) (anchuraVisor / proporcionAspecto);
        }
        mTvVideo.setLayoutParams(lp);
        mLblSinVideo.setLayoutParams(lp2);
    }

    // Guarda en preferencias el path del vídeo.
    private void guardarEnPreferencias(String path) {
        // Se almacena en las preferencias el path del video
        SharedPreferences preferencias = getSharedPreferences(getString(R.string.app_name),
                MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(PREF_PATH_VIDEO, path);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuVideo) {
            // Si se está reproduciendo, se pausa.
            if (mReproductor.isPlaying()) {
                mReproductor.pause();
            }
            // Se muestra el fragmento de diálogo de opciones.
            PickOrCaptureDialogFragment frgDialogo = new PickOrCaptureDialogFragment();
            frgDialogo.show(this.getSupportFragmentManager(),
                    "PickOrCaptureDialogFragment");
        }
        return super.onOptionsItemSelected(item);
    }

    // Envía un intent implícito para seleccionar un vídeo de la galería.
    // Recibe el nombre que debe tomar la copia privada del archivo seleccionado.
    private void seleccionarVideo(String nombreArchivoPrivado) {
        // Se guarda el nombre para uso posterior.
        sNombreArchivoPrivado = nombreArchivoPrivado;
        // Se seleccionará un vídeo de la galería.
        // (el segundo parámetro es el Data, que corresponde a la Uri de la galería.)
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("video/*");
        startActivityForResult(i, RC_SELECCIONAR_VIDEO);
    }

    // Envía un intent implícito para la captura de un vídeo.
    // Recibe el nombre que debe tomar la copia privada del vídeo.
    private void capturarVideo(String nombreArchivoPrivado) {
        // Se guarda el nombre para uso posterior.
        sNombreArchivoPrivado = nombreArchivoPrivado;
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // Si hay alguna actividad que sepa realizar la acción.
        if (i.resolveActivity(getPackageManager()) != null) {
            // Se crea el archivo para la foto en el directorio público (true).
            // Se obtiene la fecha y hora actual.
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            String nombre = "VID_" + timestamp + "_" + ".MP4";
            File videoFile = crearArchivoVideo(nombre, true);
            if (videoFile != null) {
                // Se guarda el path del archivo para cuando se haya hecho la captura.
                sPathVideoOriginal = videoFile.getAbsolutePath();
                // Se añade como extra del intent la uri donde debe guardarse.
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
                // Se establecen los extras de calidad y duración máxima.
                i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                i.putExtra(MediaStore.EXTRA_DURATION_LIMIT, DURACION_MAX_SEGUNDOS);
                startActivityForResult(i, RC_CAPTURAR_VIDEO);
            }
        }
    }

    // Crea la copia privada del vídeo y la carga en el reproductor.
    private void crearYCargarCopiaPrivada(String pathVideo) {
        File archivo = crearArchivoVideo(sNombreArchivoPrivado, false);
        if (archivo != null) {
            if (copiarArchivoBinario(new File(pathVideo), archivo)) {
                guardarEnPreferencias(archivo.getAbsolutePath());
                mPosicionInicial = 0;
                cargarVideo(archivo.getAbsolutePath());
            }
        }
    }

    // Crea un archivo de vídeo con el nombre indicado en almacenamiento externo si es posible, o si
    // no en almacenamiento interno, y lo retorna. Retorna null si fallo.
    // Si publico es true -> en la carpeta pública de vídeos.
    // Si publico es false, en la carpeta propia de vídeos.
    private File crearArchivoVideo(String nombre, boolean publico) {
        // Se obtiene el directorio en el que almacenarlo.
        File directorio;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (publico) {
                // En el directorio público para imágenes del almacenamiento externo.
                directorio = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            } else {
                directorio = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            }
        } else {
            // En almacenamiento interno.
            directorio = getFilesDir();
        }
        // Su no existe el directorio, se crea.
        if (directorio != null && !directorio.exists()) {
            if (!directorio.mkdirs()) {
                Log.d(getString(R.string.app_name), "error al crear el directorio");
                return null;
            }
        }
        // Se crea un archivo con ese nombre y la extensión jpg en ese
        // directorio.
        File archivo = null;
        if (directorio != null) {
            archivo = new File(directorio.getPath() + File.separator +
                    nombre);
            Log.d(getString(R.string.app_name), archivo.getAbsolutePath());
        }
        // Se retorna el archivo creado.
        return archivo;
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_CAPTURAR_VIDEO:
                    // Se agrega el vídeo a la Galería
                    agregarVideoAGaleria(sPathVideoOriginal);
                    // Se crea la copia privada del vídeo y se carga.
                    crearYCargarCopiaPrivada(sPathVideoOriginal);
                    break;
                case RC_SELECCIONAR_VIDEO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = intent.getData();
                    sPathVideoOriginal = getRealPath(uriGaleria);
                    // Se crea la copia privada del vídeo y se carga.
                    crearYCargarCopiaPrivada(sPathVideoOriginal);
                    break;
            }
        }
    }

    // Obtiene el path real de un vídeo a partir de la URI de Galería obtenido con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        // Se consulta en el content provider de la galería.
        String[] filePath = {MediaStore.Video.Media.DATA};
        Cursor c = getContentResolver().query(uriGaleria, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String path = c.getString(columnIndex);
        c.close();
        return path;
    }

    // Agrega a la Galería el vídeo indicado.
    private void agregarVideoAGaleria(String pathVideo) {
        // Se crea un intent implícito con la acción de
        // escaneo de un fichero multimedia.
        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // Se obtiene la uri del archivo a partir de su path.
        File archivo = new File(pathVideo);
        Uri uri = Uri.fromFile(archivo);
        // Se establece la uri con datos del intent.
        i.setData(uri);
        // Se envía un broadcast con el intent.
        this.sendBroadcast(i);
    }

    // Crear una copia del archivo. Retorna si ha ido bien.
    private boolean copiarArchivoBinario(File origen, File destino) {
        try {
            InputStream entrada = new FileInputStream(origen);
            BufferedInputStream lector = new BufferedInputStream(entrada);
            Log.d(getString(R.string.app_name), destino.getPath());
            FileOutputStream salida = new FileOutputStream(destino);
            BufferedOutputStream escritor = new BufferedOutputStream(salida);
            byte[] array = new byte[1000];
            int leidos = lector.read(array);
            while (leidos > 0) {
                escritor.write(array, 0, leidos);
                leidos = lector.read(array);
            }
            escritor.close();
            lector.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Carga en el reproductor el vídeo con el path recibido.
    private void cargarVideo(final String path) {
        try {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                    MediaStore.Images.Thumbnails.MINI_KIND);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
            mLblSinVideo.setBackgroundDrawable(bitmapDrawable);
            mLblSinVideo.setText("");
            mReproductor.reset();
            mReproductor.setDataSource(path);
            mReproductor.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cuando se selecciona una opción del diálogo PickOrCaptureDialogFragment.
    @Override
    public void onItemClick(DialogFragment dialog, int which) {
        if (which == OPTION_PICK) {
            seleccionarVideo("mivideo.mp4");
        } else {
            capturarVideo("mivideo.mp4");
        }
    }

    // Cuando la textura está disponible.
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        // Se crea una superficio con esa textura y se le establece al reproductor.
        Surface surface = new Surface(surfaceTexture);
        try {
            mReproductor.setSurface(surface);
        } catch (Exception e) {
            Log.d(getString(R.string.app_name), e.getMessage());
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Se liberar todos los recursos asociados al reproductor.
        if (mReproductor != null) {
            mReproductor.stop();
            mReproductor.release();
            mReproductor = null;
        }
    }

    /**** Acciones de los Controles ****/
    @Override
    public void start() {
        if (mReproductor != null) {
            mReproductor.start();
            mLblSinVideo.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void pause() {
        if (mReproductor != null) {
            mReproductor.pause();
        }
    }

    @Override
    public int getDuration() {
        if (mReproductor != null) {
            return mReproductor.getDuration();
        }
        else {
            return 0;
        }
    }

    @Override
    public int getCurrentPosition() {
        if (mReproductor != null) {
            return mReproductor.getCurrentPosition();
        }
        else {
            return 0;
        }
    }

    @Override
    public void seekTo(int pos) {
        if (mReproductor != null) {
            mReproductor.seekTo(pos);
        }
    }

    @Override
    public boolean isPlaying() {
        if (mReproductor != null) {
            return mReproductor.isPlaying();
        }
        else {
            return false;
        }
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    // Clase que personaliza los Controles.
    private class Controles extends MediaController {

        public Controles(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public Controles(Context context, boolean useFastForward) {
            super(context, useFastForward);
        }

        public Controles(Context context) {
            super(context);
        }

        @Override
        public void hide() {
            // Cuando se ocultan los controles se oculta también la action bar.
            super.hide();
            mActionBar.hide();
        }

        @Override
        public void show() {
            // Cuando se muestran los controles se muestra también la action bar.
            super.show();
            mActionBar.show();
        }
    }

}
