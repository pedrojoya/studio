package es.iessaladillo.pedrojoya.pr039;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Constantes.
    private static final float VELOCIDAD_NORMAL = 1f;
    private static final int PRIORIDAD_MAXIMA = 1;
    private static final float VOLUMEN_MAX = 1f;
    private static final int SIN_BUCLE = 0;
    private static final int CALIDAD_NORMAL = 0;
    private static final int PRIORIDAD_NORMAL = 1;
    private static final int MAX_STREAMS = 8;

    // Variables.
    private SoundPool mReproductor;
    private int mIdDisparo;
    private int mIdExplosion;

    // Vistas.
    private ImageView btnDisparar;
    private ImageView btnExplosion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        configSonidos();

    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        btnDisparar = (ImageView) findViewById(R.id.btnDisparar);
        btnDisparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disparar();
            }
        });
        btnExplosion = (ImageView) findViewById(R.id.btnExplosion);
        btnExplosion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explosion();
            }
        });
        // Inicialmente los botones estarán deshabilitados hasta que los sonidos
        // correspondientes están cargados en el mReproductor.
        btnDisparar.setEnabled(false);
        btnExplosion.setEnabled(false);
    }

    // Crea el reproductor de sonidos.
    @SuppressWarnings("deprecation")
    private void crearSoundPool() {
        // En API 21+ se usa el patrón Builder.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mReproductor = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mReproductor = new SoundPool(MAX_STREAMS, AudioManager.STREAM_NOTIFICATION,
                    CALIDAD_NORMAL);
        }
    }

    // Configura el mReproductor de sonidos.
    private void configSonidos() {
        // Se crea el objeto SoundPool con un límite de 8 sonidos simultáneos y
        // calidad estándar.
        crearSoundPool();
        // Se cargan los ficheros de sonido (recibe el contexto, el recurso y la
        // prioridad estándar).
        mIdDisparo = mReproductor.load(this, R.raw.disparo, PRIORIDAD_NORMAL);
        mIdExplosion = mReproductor.load(this, R.raw.explosion, PRIORIDAD_NORMAL);
        // Cuando se termine de cargar el sonido, se activa el botón
        // correspondiente.
        mReproductor.setOnLoadCompleteListener(new OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                // Activo el botón correspondiente al sonido.
                if (sampleId == mIdDisparo) {
                    btnDisparar.setEnabled(true);
                } else if (sampleId == mIdExplosion) {
                    btnExplosion.setEnabled(true);
                }
            }
        });
    }

    // Reproduce el sonido de disparo.
    private void disparar() {
        // Se reproduce el disparo.
        mReproductor.play(mIdDisparo, VOLUMEN_MAX, VOLUMEN_MAX, PRIORIDAD_MAXIMA,
                SIN_BUCLE, VELOCIDAD_NORMAL);
    }

    // Reproduce el sonido de explosión.
    private void explosion() {
        // Se reproduce la explosión.
        mReproductor.play(mIdExplosion, VOLUMEN_MAX, VOLUMEN_MAX,
                PRIORIDAD_MAXIMA, SIN_BUCLE, VELOCIDAD_NORMAL);
    }

    // Al destruir la actividad.
    @Override
    protected void onDestroy() {
        // Se liberan los recursos del mReproductor.
        if (mReproductor != null) {
            mReproductor.release();
            mReproductor = null;
        }
        super.onDestroy();
    }

}