package es.iessaladillo.pedrojoya.pr034;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import es.iessaladillo.pedrojoya.videoview.R;

public class MainActivity extends AppCompatActivity implements
        MediaPlayer.OnPreparedListener {

    private static final int RC_SELECCIONAR_VIDEO = 0;

    private static final String STATE_CURRENT_POSITION = "current_position";
    private static final String STATE_PATH_VIDEO = "path_video";

    private String mPathVideo;
    private int mPosicionInicial;

    private VideoView vvReproductor;
    private MediaController mControles;
    private ActionBar mActionBar;
    private TextView mLblSinVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configActionBar();
        initVistas();
        restaurarEstado(savedInstanceState);
    }

    // Configura la Action Bar para que tenga un fondo semistransparente.
    private void configActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(
                new ColorDrawable(Color.argb(200, 0, 0, 0)));
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        vvReproductor = (VideoView) this.findViewById(R.id.vvReproductor);
        if (vvReproductor != null) {
            vvReproductor.setEnabled(false);
            vvReproductor.setOnPreparedListener(this);
        }
        mLblSinVideo = (TextView) findViewById(R.id.lblSinVideo);
        mLblSinVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se envía el intent de selección del vídeo en la galería.
                seleccionarVideo();
            }
        });
        // Se crean los controles y se asocian al reproductor.
        mControles = new MediaController(this) {
            @Override
            public void show() {
                // Cuando se muestran los controles se muestra también la
                // action bar.
                super.show();
                mActionBar.show();
            }

            @Override
            public void hide() {
                // Cuando se ocultan los controles se oculta también la
                // action bar.
                super.hide();
                mActionBar.hide();
            }
        };
        mControles.setAnchorView(vvReproductor);
        vvReproductor.setMediaController(mControles);
    }

    // Restaura el estado previo al cambio de configuración.
    private void restaurarEstado(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Se retaura la posición actual y el path del vídeo.
            mPosicionInicial = savedInstanceState.getInt(
                    STATE_CURRENT_POSITION, 0);
            mPathVideo = savedInstanceState.getString(STATE_PATH_VIDEO, "");
        } else {
            mPathVideo = "";
            mPosicionInicial = 0;
        }
        if (!TextUtils.isEmpty(mPathVideo)) {
            // Si disponemos del vídeo lo cargamos.
            cargarVideo(mPathVideo);
        } else {
            // Si no disponemos del vídeo, se selecciona.
            seleccionarVideo();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Se guarda la posición actual de reproducción y el path del vídeo.
        outState.putInt(STATE_CURRENT_POSITION,
                vvReproductor.getCurrentPosition());
        outState.putString(STATE_PATH_VIDEO, mPathVideo);
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
            if (vvReproductor.isPlaying()) {
                vvReproductor.pause();
            }
            // Se envía el intent de selección del vídeo en la galería.
            seleccionarVideo();
        }
        return super.onOptionsItemSelected(item);
    }

    // Envía un intent implícito para seleccionar un vídeo de la galería.
    private void seleccionarVideo() {
        // Se seleccionará un vídeo de la galería.
        // (el segundo parámetro es el Data, que corresponde a la Uri de la
        // galería.)
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("video/*");
        startActivityForResult(i, RC_SELECCIONAR_VIDEO);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SELECCIONAR_VIDEO:
                    // Se obtiene el path real a partir de la uri retornada
                    // por la galería.
                    Uri uriGaleria = intent.getData();
                    mPathVideo = getRealPath(uriGaleria);
                    mPosicionInicial = 0;
                    // Se carga el vídeo en el reproductor.
                    if (!TextUtils.isEmpty(mPathVideo)) {
                        cargarVideo(mPathVideo);
                    }
                    break;
            }
        }
    }

    // Obtiene el path real de un vídeo a partir de la URI de Galería obtenido
    // con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        String path = "";
        // Se consulta en el content provider de la galería.
        String[] filePath = {MediaStore.Video.Media.DATA};
        Cursor c = getContentResolver()
                .query(uriGaleria, filePath, null, null, null);
        if (c != null && c.moveToFirst()) {
            int columnIndex = c.getColumnIndex(filePath[0]);
            path = c.getString(columnIndex);
            c.close();
        }
        return path;
    }

    // Carga en el reproductor el vídeo con el path recibido.
    private void cargarVideo(String path) {
        mLblSinVideo.setVisibility(View.INVISIBLE);
        vvReproductor.setEnabled(true);
        vvReproductor.setVideoPath(path);
        vvReproductor.seekTo(mPosicionInicial);
    }

    // Cuando ya el VideoView ya está preparado para reproducir.
    @Override
    public void onPrepared(MediaPlayer mp) {
        // Se inicia la reproducción y se muestran los controles.
        vvReproductor.start();
        mControles.show();
    }

}