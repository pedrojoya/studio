package es.iessaladillo.pedrojoya.pr093.actividades;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr093.R;
import es.iessaladillo.pedrojoya.pr093.modelos.Cancion;
import es.iessaladillo.pedrojoya.pr093.servicios.MusicaOnlineService;

public class MainActivity extends Activity implements OnItemClickListener,
        OnClickListener {

    // Variables.
    private ArrayList<Cancion> mCanciones;
    private boolean mVinculado = false;
    private Conexion mConexion;
    private Messenger mEmisorMensajes;
    private Messenger mReceptorMensajes;
    public boolean mReproduciendo = false;
    public int mPosCancionActual = -1;

    // Vistas.
    private ListView lstCanciones;
    private ImageView imgPlay;

    // Clase para la conexión.
    private class Conexion implements ServiceConnection {

        // Cuando se desconecta el servicio.
        @Override
        public void onServiceDisconnected(ComponentName arg0) {

            mEmisorMensajes = null;
            mVinculado = false;
        }

        // Cuando se completa la conexión.
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            // Se crea el objeto emisor de mensajes al servicio.
            mEmisorMensajes = new Messenger(binder);
            // Se carga la lista de canciones en el servicio.
            MusicaOnlineService.setLista(mEmisorMensajes, mCanciones);
            mVinculado = true;
            actualizarIU();
        }
    }

    // Clase gestora de mensajes recibidos.
    private class ReceptorMensajes extends Handler {

        // Cuando se recibe un mensaje.
        @Override
        public void handleMessage(Message msg) {
            // Dependiendo del mensaje.
            switch (msg.what) {
                case MusicaOnlineService.MSG_CANCION_ACTUAL:
                    // Se selecciona en la lista la canci�n que se est� reproduciendo.
                        mPosCancionActual = MusicaOnlineService.getPosCancionActual(msg);
                        if (mPosCancionActual >= 0) {
                            lstCanciones.setItemChecked(mPosCancionActual, true);
                        }
                    break;
                case MusicaOnlineService.MSG_REPRODUCIENDO:
                        mReproduciendo = MusicaOnlineService.isReproduciendo(msg);
                        if (mReproduciendo) {
                            imgPlay.setImageResource(android.R.drawable.ic_media_pause);
                        } else {
                            imgPlay.setImageResource(android.R.drawable.ic_media_play);
                        }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        // Se crea el receptor de mensajes desde el servicio.
        mReceptorMensajes = new Messenger(new ReceptorMensajes());
        // Se crea el objeto que representa la conexión con el servicio.
        mConexion = new Conexion();

    }

    private void getVistas() {
        ImageView imgAnterior = (ImageView) findViewById(R.id.imgAnterior);
        imgAnterior.setOnClickListener(this);
        ImageView imgSiguiente = (ImageView) findViewById(R.id.imgSiguiente);
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se vincula el servicio.
        vincularServicio();
    }

    private void vincularServicio() {

        // Se obtiene el intent de vinculación con el servicio.
        Intent intentServicio = MusicaOnlineService.getIntent(getApplicationContext(), mReceptorMensajes);
        // Se vincula el servicio.
        bindService(intentServicio, mConexion, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
//        desvincularServicio();
        super.onStop();
    }

    private void desvincularServicio() {
        // Se desvincula del servicio.
        if (mVinculado) {
            unbindService(mConexion);
            mConexion = null;
            mVinculado = false;
        }
    }

    // Actualiza la IU según el estado del servicio.
    private void actualizarIU() {
        if (mVinculado) {
            // Se pregunta al servicio por la cancion actual.
            MusicaOnlineService.askPosCancionActual(mEmisorMensajes);
            // Se pregunta al servicio si está reproduciendo.
            MusicaOnlineService.askIsReproduciendo(mEmisorMensajes);
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
            MusicaOnlineService.reproducirCancion(mEmisorMensajes, position);
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
            if (mReproduciendo) {
                // Se pausa la reproducción.
                MusicaOnlineService.pausarReproduccion(mEmisorMensajes);
            } else {
                // Si se estaba reproduciendo ninguna canción que se ha pausado.
                if (mPosCancionActual >= 0) {
                    // Se continua la reproducción.
                    MusicaOnlineService.continuarReproduccion(mEmisorMensajes);
                } else {
                    // Se comienza la reproducción de la primera canción de la
                    // lista.
                    MusicaOnlineService.reproducirCancion(mEmisorMensajes, 0);
                }
            }
        }
    }


    // Al pulsar sobre Siguiente.
    private void imgSiguienteOnClick() {
        // Se pasa a la siguiente canción.
        if (mVinculado) {
            MusicaOnlineService.siguienteCancion(mEmisorMensajes);
        }
    }

    // Al pulsar sobre Anterior.
    private void imgAnteriorOnClick() {
        // Se pasa a la canción anterior.
        if (mVinculado) {
            MusicaOnlineService.anteriorCancion(mEmisorMensajes);
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