package es.iessaladillo.pedrojoya.pr122;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        PickOrCaptureDialogFragment.Listener, MediaPlayer.OnPreparedListener, View.OnClickListener,
        CustomVideoView.PlayPauseListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener {

    private static final int RC_CAPTURAR_VIDEO = 0;
    private static final int RC_SELECCIONAR_VIDEO = 1;
    private static final String PREF_PATH_VIDEO = "prefPathVideo";
    private static final String STATE_CURRENT_POSITION = "current_position";
    private static final int OPTION_PICK = 0;
    private static final int CALIDAD_MAX = 1;
    private static final int DURACION_MAX_SEGUNDOS = 20;

    private String sPathVideoOriginal;
    private String sNombreArchivoPrivado;
    private String mPathVideo;
    private int mPosicionInicial;

    private CustomVideoView mReproductor;
    private ActionBar mActionBar;
    private MediaController mControles;
    private ImageView mImgThumbnail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configActionBar();
        restaurarEstado(savedInstanceState);
        initVistas();
        configReproductor();
        restaurarPreferencias();
    }

    // Configura el reproductor.
    private void configReproductor() {
        mReproductor = (CustomVideoView) this.findViewById(R.id.vvReproductor);
        mControles = new MediaController(this) {
            // Cuando se muestran los controles se muestra también la ActionBar.
            @Override
            public void show() {
                super.show();
                mActionBar.show();
            }
            // Cuando se ocultan los controles se oculta también la ActionBar.
            @Override
            public void hide() {
                super.hide();
                mActionBar.hide();
            }
        };
        mReproductor.setMediaController(mControles);
        mReproductor.setOnPreparedListener(this);  // Está preparado para la reproducción.
        mReproductor.setPlayPauseListener(this);  // Se reproduce / se pausa.
        mReproductor.setOnCompletionListener(this);  // Termina la reproducción.
        if (Build.VERSION.SDK_INT >= 17)
            mReproductor.setOnInfoListener(this);  // Vídeo renderizado (evita fotograma en negro).
    }

    // Restaura las preferencias.
    private void restaurarPreferencias() {
        SharedPreferences preferencias = getSharedPreferences(getString(R.string.app_name),
                MODE_PRIVATE);
        mPathVideo = preferencias.getString(PREF_PATH_VIDEO, "");
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        mImgThumbnail = ActivityCompat.requireViewById(this, R.id.imgThumbnail);
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
        // getCurrentPosition() no da valores correctos si el vídeo ha concluído su reproducción.
        int pos;
        if (mReproductor.isPlaying()) {
            pos = mReproductor.getCurrentPosition();
        } else {
            pos = mPosicionInicial;
        }
        outState.putInt(STATE_CURRENT_POSITION, pos);
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
            mostrarDialogo();

        }
        return super.onOptionsItemSelected(item);
    }

    // Muestra el diálogo PickOrCaptureDialogFragment.
    private void mostrarDialogo() {
        PickOrCaptureDialogFragment frgDialogo = new PickOrCaptureDialogFragment();
        frgDialogo.show(this.getSupportFragmentManager(),
                "PickOrCaptureDialogFragment");
    }

    // Envía un intent implícito para seleccionar un vídeo de la galería.
    // Recibe el nombre que debe tomar la copia privada del archivo seleccionado.
    @SuppressWarnings("SameParameterValue")
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
    @SuppressWarnings("SameParameterValue")
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
                i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, CALIDAD_MAX);
                i.putExtra(MediaStore.EXTRA_DURATION_LIMIT, DURACION_MAX_SEGUNDOS);
                // Se deshabilita el sensor de orientación (evita errores al girar el dispositivo
                // para hacer el vídeo y después retornar).
                setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                startActivityForResult(i, RC_CAPTURAR_VIDEO);
            }
        }
    }

    // Crea la copia privada del vídeo.
    private void crearCopiaPrivada(String pathVideo) {
        File archivo = crearArchivoVideo(sNombreArchivoPrivado, false);
        if (archivo != null) {
            if (copiarArchivoBinario(new File(pathVideo), archivo)) {
                // Se guarda en las preferencias el path del vídeo.
                guardarEnPreferencias(archivo.getAbsolutePath());
                // Se actualizan las variables a nivel de clase para mostrar el vídeo.
                mPosicionInicial = 0;
                mPathVideo = archivo.getAbsolutePath();
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
                    Log.d("mia", sPathVideoOriginal);
                    // Se agrega el vídeo a la Galería
                    agregarVideoAGaleria(sPathVideoOriginal);
                    // Se crea la copia privada del vídeo y se carga.
                    crearCopiaPrivada(sPathVideoOriginal);
                    // Se vuelve a habilitar el sensor de orientación.
                    setRequestedOrientation(
                            ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    break;
                case RC_SELECCIONAR_VIDEO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = intent.getData();
                    sPathVideoOriginal = getRealPath(uriGaleria);
                    Log.d("mia", sPathVideoOriginal);
                    // Se crea la copia privada del vídeo y se carga.
                    crearCopiaPrivada(sPathVideoOriginal);
                    break;
            }
        }
    }

    // Obtiene el path real de un vídeo a partir de la URI de Galería obtenido con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        // Se consulta en el content provider de la galería.
        String[] filePath = {MediaStore.Video.Media.DATA};
        Cursor c = getContentResolver().query(uriGaleria, filePath, null, null, null);
        String path = "";
        if (c != null && c.moveToFirst()) {
            int columnIndex = c.getColumnIndex(filePath[0]);
            path = c.getString(columnIndex);
            c.close();
        }
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
        /* Para crear un bitmap de un vídeo.
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                    MediaStore.Images.Thumbnails.MINI_KIND);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
         */
        // Se obtienen los metadatos del vídeo.
        new GetMetadataTask().execute();
        mReproductor.setVideoPath(path);
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

    // Cuando la actividad pasa a primer plano.
    @Override
    protected void onResume() {
        super.onResume();
        // Si hay vídeo que mostrar.
        if (!TextUtils.isEmpty(mPathVideo)) {
            // Se carga el vídeo en el reproductor.
            cargarVideo(mPathVideo);
        }
    }

    // Cuando el vídeo ya está cargado y listo para reproducirse.
    @Override
    public void onPrepared(MediaPlayer mp) {
        // Se actualizan las dimensiones del VideoView.
        // actualizarDimensionesVisor(mp);
        // Se muestran los controles.
        mControles.show();
        // Se coloca el vídeo en la posición idónea.
        mReproductor.seekTo(mPosicionInicial);
    }

    // Actualiza las dimensiones del visor acorde al vídeo a reproducir.
    @SuppressWarnings("unused")
    private void actualizarDimensionesVisor(MediaPlayer mp) {
        // Se obtiene la anchura y altura del vÌdeo y del visor.
        int anchuraVideo = mp.getVideoWidth();
        int alturaVideo = mp.getVideoHeight();
        int anchuraVisor = mReproductor.getWidth();
        int alturaVisor = mReproductor.getHeight();
        // Se calculan las proporciones de anchura y altura y la ratio.
        float proporcionAnchura = (float) anchuraVisor / (float) anchuraVideo;
        float proporcionAltura = (float) alturaVisor / (float) alturaVideo;
        float proporcionAspecto = (float) anchuraVideo / (float) alturaVideo;
        // Se actualizan las dimensiones del visorObtenemos los par·metros de anchura y altura de la ventana de reproducciÛn.
        ViewGroup.LayoutParams lp = mReproductor.getLayoutParams();
        ViewGroup.LayoutParams lp2 = mImgThumbnail.getLayoutParams();
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
        mReproductor.setLayoutParams(lp);
        mImgThumbnail.setLayoutParams(lp2);
    }

    // Cuando se hace click sobre la miniatura.
    @Override
    public void onClick(View v) {
        // Se muestran los controles.
        mControles.show();
    }

    // Cuando comienza/continua la reproducción del vídeo.
    @Override
    public void onVideoPlay() {
        // Se oculta la miniatura para que se vea el vídeo.
        mImgThumbnail.setVisibility(View.INVISIBLE);
    }

    // Cuando se pausa el vídeo.
    @Override
    public void onVideoPause() {
        // Se guarda la posición actual (que después será salvada si hay cambio de orientación)
        // Evita el problema de que getCurrentPosition() no siempre devuelve lo que debiera si
        // ha terminado de reproducirse el vídeo.
        mPosicionInicial = mReproductor.getCurrentPosition();
    }

    // Cuando se termina la reproducción.
    @Override
    public void onCompletion(MediaPlayer mp) {
        // Se vuelve a colocar al principio.
        mReproductor.seekTo(0);
        mPosicionInicial = 0;
    }

    // Sólo disponible a partir de API 17 (evita el parpadeo a negro de VideoView).
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            // El vídeo ha empezado, se oculta el thumbnail.
            mImgThumbnail.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    // Tarea asíncrona para obtener los metadatos del vídeo.
    class GetMetadataTask extends AsyncTask<Void, Void, Bundle> {

        private static final int MICROSEGUNDOS_EN_MILISEGUNDO = 1000;
        private static final String KEY_FRAME = "frame";
        private static final String KEY_DATE = "date";
        private static final String KEY_DURATION = "duration";
        private static final String KEY_VIDEO_WIDTH = "video_width";
        private static final String KEY_VIDEO_HEIGHT = "video_height";

        @Override
        protected Bundle doInBackground(Void... params) {
            // Se obtienen los metadatos.
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(mPathVideo);
            Bitmap frame = retriever.getFrameAtTime(mPosicionInicial * MICROSEGUNDOS_EN_MILISEGUNDO,
                    MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
            String date = retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_DATE);
            String duration = retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_DURATION);
            String width = retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String height = retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            // Se retorna un bundle con los metadatos.
            Bundle result = new Bundle();
            result.putParcelable(KEY_FRAME, frame);
            result.putString(KEY_DATE, date);
            result.putString(KEY_DURATION, duration);
            result.putString(KEY_VIDEO_WIDTH, width);
            result.putString(KEY_VIDEO_HEIGHT, height);
            return result;
        }

        @Override
        protected void onPostExecute(Bundle bundle) {
            // Se establece como thumbnail el frame y se hace visible.
            mImgThumbnail.setImageBitmap((Bitmap) bundle.getParcelable(KEY_FRAME));
            mImgThumbnail.setVisibility(View.VISIBLE);
        }
    }
}
