package es.iessaladillo.pedrojoya.pr093.actividades;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr093.R;
import es.iessaladillo.pedrojoya.pr093.modelos.Cancion;
import es.iessaladillo.pedrojoya.pr093.servicios.MusicaOnlineService;
import es.iessaladillo.pedrojoya.pr093.servicios.MusicaOnlineService.LocalBinder;

public class MainActivity extends Activity implements OnItemClickListener,
        OnClickListener {

    // Variables.
    private ServiceConnection mConexion;
    private MusicaOnlineService mServicio;
    private ArrayList<Cancion> mCanciones;
    private LocalBroadcastManager mGestor;
    private BroadcastReceiver mReceptor;
    private IntentFilter mFiltro;
    private boolean mVinculado = false;

    // Vistas.
    private ListView lstCanciones;
    private ImageView imgAnterior;
    private ImageView imgSiguiente;
    private ImageView imgPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        // Se obtiene el gestor de receptores locales.
        mGestor = LocalBroadcastManager.getInstance(this);
        // Se crea el receptor de mensajes desde el servicio.
        mReceptor = new BroadcastReceiver() {
            // Cuando se recibe el intent.
            @Override
            public void onReceive(Context context, Intent intent) {
                // Se actualiza la canción seleccionada
                actualizarIU();
            }
        };
        // Se crea el filtro para al receptor.
        mFiltro = new IntentFilter(MusicaOnlineService.ACTION_PLAYING);
        // Se crea el objeto que representa la conexión con el servicio.
        mConexion = new ServiceConnection() {

            // Cuando se desconecta el servicio.
            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                mServicio = null;
                mVinculado = false;
            }

            // Cuando se completa la conexión.
            @Override
            public void onServiceConnected(ComponentName className,
                    IBinder binder) {
                mVinculado = true;
                // Se obtiene la referencia al servicio.
                mServicio = ((LocalBinder) binder).getService();
                // Se carga la lista de canciones en el servicio.
                mServicio.setLista(mCanciones);
                actualizarIU();
            }
        };
    }

    private void getVistas() {
        imgAnterior = (ImageView) findViewById(R.id.imgAnterior);
        imgAnterior.setOnClickListener(this);
        imgSiguiente = (ImageView) findViewById(R.id.imgSiguiente);
        imgSiguiente.setOnClickListener(this);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgPlay.setOnClickListener(this);
        lstCanciones = (ListView) findViewById(R.id.lstCanciones);
        mCanciones = getListaCanciones();
        ArrayAdapter<Cancion> adaptador = new ArrayAdapter<Cancion>(this,
                android.R.layout.simple_list_item_activated_1, mCanciones);
        lstCanciones.setAdapter(adaptador);
        lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstCanciones.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se vincula el servicio.
        vincularServicio();
        actualizarIU();
        // Se registra el receptor en el gestor de receptores locales para dicha
        // acción.
        mGestor.registerReceiver(mReceptor, mFiltro);
    }

    private void vincularServicio() {
        // Se crea el intent de vinculaci�n con el servicio.
        Intent intentServicio = new Intent(getApplicationContext(),
                MusicaOnlineService.class);
        // Se vincula el servicio.
        bindService(intentServicio, mConexion, Context.BIND_AUTO_CREATE);
    }

    // Caundo se pausa la actividad.
    @Override
    protected void onPause() {
        // Se desregistra el receptor del gestor de receptores locales para
        // dicha acci�n.
        mGestor.unregisterReceiver(mReceptor);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            desvincularServicio();
        }
        super.onDestroy();
    }

    private void desvincularServicio() {
        // Se desvincula del servicio.
        if (mVinculado) {
            unbindService(mConexion);
            mVinculado = false;
        }
    }

    // Actualiza la IU seg�n el estado del servicio.
    private void actualizarIU() {
        if (mVinculado) {
            // Se selecciona en la lista la canci�n que se est� reproduciendo.
            int posCancionActual = mServicio.getPosCancionActual();
            if (posCancionActual >= 0) {
                lstCanciones.setItemChecked(posCancionActual, true);
            }
            // Se actualiza el icono de reproducir / pausar.
            if (mServicio.reproduciendo()) {
                imgPlay.setImageResource(android.R.drawable.ic_media_pause);
            }
        }
    }

    // Cuando se pulsa en una canci�n de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se reproduce la canción.
        reproducirCancion(position);
    }

    // Reproduce una canción. Recibe su posición en la lista.
    private void reproducirCancion(int position) {
        if (mVinculado) {
            mServicio.reproducirCancion(position);
        }
    }

    // Cuando se pulsa en algún botón del panel.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.imgAnterior:
            imgAnteriorOnClick();
            break;
        case R.id.imgSiguiente:
            imgSiguienteOnClick();
            break;
        case R.id.imgPlay:
            imgPlayOnClick();
            break;
        }
    }

    // Al pulsar sobre reproducir / pausar.
    private void imgPlayOnClick() {
        if (mVinculado) {
            // Si estamos reproduciendo.
            if (mServicio.reproduciendo()) {
                // Se pausa la reproducción.
                mServicio.pausarReproduccion();
                // Se actualiza el icono al de reproducir.
                imgPlay.setImageResource(android.R.drawable.ic_media_play);
            } else {
                // Si se estaba reproduciendo ninguna canción que se ha pausado.
                if (mServicio.getPosCancionActual() >= 0) {
                    // Se continua la reproducción.
                    mServicio.continuarReproduccion();
                } else {
                    // Se comienza la reproducción de la primera canción de la
                    // lista.
                    mServicio.reproducirCancion(0);
                }
                // Se actualiza el icono al de pausar.
                imgPlay.setImageResource(android.R.drawable.ic_media_pause);
            }
        }
    }

    // Al pulsar sobre Siguiente.
    private void imgSiguienteOnClick() {
        // Se pasa a la siguiente canción.
        if (mVinculado) {
            mServicio.siguienteCancion();
        }
    }

    // Al pulsar sobre Anterior.
    private void imgAnteriorOnClick() {
        // Se pasa a la canción anterior.
        if (mVinculado) {
            mServicio.anteriorCancion();
        }
    }

    // Crea y retorna el ArrayList de canciones.
    private ArrayList<Cancion> getListaCanciones() {
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        canciones
                .add(new Cancion("Morning Mood (by Grieg)", "3:43",
                        "https://www.youtube.com/audiolibrary_download?vid=036500ffbf472dcc"));
        canciones
                .add(new Cancion("Brahms Lullaby", "1:46",
                        "https://www.youtube.com/audiolibrary_download?vid=9894a50b486c6136"));
        canciones
                .add(new Cancion("From Russia With Love", "2:26",
                        "https://www.youtube.com/audiolibrary_download?vid=4e8d1a0fdb3bbe12"));
        canciones
                .add(new Cancion("Les Toreadors from Carmen (by Bizet)",
                        "2:21",
                        "https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e73"));
        canciones
                .add(new Cancion("Funeral March (by Chopin)", "9:25",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        return canciones;
    }

}