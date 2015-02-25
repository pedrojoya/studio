package es.iessaladillo.pedrojoya.pr122;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;

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

public class MainActivity extends ActionBarActivity implements PickOrCaptureDialogFragment.Listener, TextureView.SurfaceTextureListener {

    private static final int RC_CAPTURAR_VIDEO = 0;
    private static final int RC_SELECCIONAR_VIDEO = 1;
    private static final int RC_RECORTAR_VIDEO = 2;

    private static final String PREF_PATH_VIDEO = "prefPathVideo";
    private static final int OPTION_PICK = 0;
    private static final int DURACION_MAX_SEGUNDOS = 20;

    private String sPathVideoOriginal; // path en el que se guarda el video capturado.
    private String sPathVideoPropio;    // path de la copia privada del vídeo.
    private String sNombreArchivo; // Nombre para guardar en privado el video recortado.

    private TextureView tvVideo;
    private MediaPlayer mMediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setLooping(false);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        tvVideo = (TextureView) findViewById(R.id.tvVideo);
        tvVideo.setSurfaceTextureListener(this);
        // Se lee de las preferencias el path del archivo con el video recortado y privado.
        SharedPreferences preferencias = getSharedPreferences(getString(R.string.app_name),
                MODE_PRIVATE);
        String pathVideo = preferencias.getString(PREF_PATH_VIDEO, "");
        if (!TextUtils.isEmpty(pathVideo)) {
            // Se reproduce el vídeo.
            reproducirVideo(pathVideo);
        }
    }

    // Guarda en preferencias el path del vídeo recortado.
    private void guardarEnPreferencias(String path) {
        // Se almacena en las preferencias el path del archivo con el vídeo recortado y privada
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
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
            }
            PickOrCaptureDialogFragment frgDialogo = new PickOrCaptureDialogFragment();
            frgDialogo.show(this.getSupportFragmentManager(),
                    "PickOrCaptureDialogFragment");
        }
        return super.onOptionsItemSelected(item);
    }

    // Envía un intent implícito para seleccionar un vídeo de la galería.
    // Recibe el nombre que debe tomar el archivo con el vídeo recortado y guardado en privado.
    private void seleccionarVideo(String nombreArchivoPrivado) {
        // Se guarda el nombre para uso posterior.
        sNombreArchivo = nombreArchivoPrivado;
        // Se seleccionará un vídeo de la galería.
        // (el segundo parámetro es el Data, que corresponde a la Uri de la galería.)
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("video/*");
        startActivityForResult(i, RC_SELECCIONAR_VIDEO);
    }

    // Envía un intent implícito para la captura de un vídeo.
    // Recibe el nombre que debe tomar el archivo con el vídeo recortado y guardada en privado.
    private void capturarVideo(String nombreArchivoPrivado) {
        // Se guarda el nombre para uso posterior.
        sNombreArchivo = nombreArchivoPrivado;
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

    // Envía un intent implícito para recortar el vídeo. Recibe el path del vídeo a recortar.
    // Si no es posible recortar, se llama a guardar.
    private void recortarVideo(String pathVideo) {
        // Se crea el archivo para la copia privada del vídeo.
        File archivo = crearArchivoVideo(sNombreArchivo, false);
/*
        // Si hay alguna actividad que sepa realizar la acción de recortar.
        Intent i = new Intent("com.android.camera.action.TRIM");
        i.setDataAndType(Uri.fromFile(new File(pathVideo)), "video*/
/*");
        if (i.resolveActivity(getPackageManager()) != null) {
            // Se indica donde queremos que almacene el vídeo recortado.
            if (archivo != null) {
                i.putExtra("media-item-path", archivo.getAbsolutePath());
                // Se guarda el path del video propio para uso posterior.
                sPathVideoPropio = archivo.getAbsolutePath();
            }
            // Se inicia la actividad esperando el resultado.
            startActivityForResult(i, RC_RECORTAR_VIDEO);
        } else {
*/
        // Si no se puede recortar, se guarda una copia del vídeo sin recortar.
        if (archivo != null) {
            if (copiarArchivoBinario(new File(pathVideo), archivo)) {
                // Se almacena el path del vídeo privado.
                guardarEnPreferencias(archivo.getAbsolutePath());
                // Se reproduce el vídeo.
                reproducirVideo(archivo.getAbsolutePath());
            }
        }
        //}
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
                    // Se recorta la imagen.
                    recortarVideo(sPathVideoOriginal);
                    break;
                case RC_SELECCIONAR_VIDEO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = intent.getData();
                    sPathVideoOriginal = getRealPath(uriGaleria);
                    // Se recorta la imagen.
                    recortarVideo(sPathVideoOriginal);
                    break;
                case RC_RECORTAR_VIDEO:
                    // Se almacena el path del vídeo propio.
                    guardarEnPreferencias(sPathVideoPropio);
                    // Se reproduce el vídeo.
                    reproducirVideo(sPathVideoPropio);
            }
        }
    }

    // Obtiene el path real de un vídeo a partir de la URI de Galería obtenido con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        // Se consulta en el content provider de la galería el path real del archivo del vídeo.
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

    // Reproduce el vídeo con el path recibido.
    private void reproducirVideo(final String path) {
        tvVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    mMediaPlayer.setDataSource(path);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 2000);
    }

    // Cuando se selecciona una opción del diálogo PickOrCaptureDialogFragment.
    @Override
    public void onItemClick(DialogFragment dialog, int which) {
        if (which == OPTION_PICK) {
            seleccionarVideo("mivideo.mp4"); // Si BD, por ejemplo ID_alumno.jpg.
        } else {
            capturarVideo("mivideo.mp4"); // Si BD, por ejemplo ID_alumno.jpg.
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Surface surface = new Surface(surfaceTexture);
        try {
            mMediaPlayer.setSurface(surface);
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
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
