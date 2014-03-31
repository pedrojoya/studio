package es.iessaladillo.pedrojoya.pr091.servicios;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import es.iessaladillo.pedrojoya.pr091.modelos.Cancion;

public class MusicaOnlineService extends Service implements
        OnCompletionListener, OnPreparedListener {

    // Constantes.
    public static String ACTION_PLAYING = "es.iessaladillo.pedrojoya.pr091.action.playing";

    // Variables.
    private MediaPlayer mReproductor;
    private LocalBinder mBinder;
    private ArrayList<Cancion> mCanciones;
    private int mPosCancionActual = -1;

    // Clase que actúa como Binder con el servicio.
    public class LocalBinder extends Binder {
        // Retorna la instancia del servicio para que el cliente pueda
        // llamar a sus métodos públicos.
        public MusicaOnlineService getService() {
            return MusicaOnlineService.this;
        }
    }

    // Al crear el servicio.
    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea el binder con el que se vinculará la actividad.
        mBinder = new LocalBinder();
        // Se crea y configura el reproductor.
        mReproductor = new MediaPlayer();
    }

    // Al destruir el servicio.
    @Override
    public void onDestroy() {
        // Se para la reproducción y se liberan los recursos.
        if (mReproductor != null) {
            mReproductor.stop();
            mReproductor.release();
        }
        super.onDestroy();
    }

    // Reproduce la canción con dicha posición en la lista.
    public void reproducirCancion(int position) {
        if (mCanciones != null) {
            // Se actualiza cuál es la canción actual.
            mPosCancionActual = position;
            // Se reproduce la url correspondiente a la canción.
            reproducirCancion(mCanciones.get(position).getUrl());
        }
    }

    // Envía un broadcast informativo de que se está reproduciendo una canción.
    private void enviarBroadcast() {
        // Se envía un intent informativo a los receptores locales registrados
        // para la acción.
        Intent intentRespuesta = new Intent(ACTION_PLAYING);
        LocalBroadcastManager gestor = LocalBroadcastManager.getInstance(this);
        gestor.sendBroadcast(intentRespuesta);
    }

    // Reproduce la url correspondiente a una canción.
    public void reproducirCancion(String url) {
        // Se prepara la reproducción de la canción.
        if (mReproductor != null) {
            mReproductor.reset();
            mReproductor.setLooping(false);
            mReproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mReproductor.setOnPreparedListener(this);
            mReproductor.setOnCompletionListener(this);
            try {
                mReproductor.setDataSource(url);
                mReproductor.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Cuando se establece el vínculo.
    @Override
    public IBinder onBind(Intent intent) {
        // Se retorna el binder con el servicio.
        return mBinder;
    }

    // Cuando ya está preparada la reproducción de la canción.
    @Override
    public void onPrepared(MediaPlayer arg0) {
        // Se inicia la reproducción.
        mReproductor.start();
        // Se envía un broadcast informativo.
        enviarBroadcast();
    }

    // Cuando ha finalizado la reproducción de la canción.
    @Override
    public void onCompletion(MediaPlayer arg0) {
        siguienteCancion();
    }

    // Reproduce la siguiente canción a la actual.
    public void siguienteCancion() {
        if (mCanciones != null) {
            reproducirCancion((mPosCancionActual + 1) % mCanciones.size());
        }
    }

    // Reproduce la anterior canción a la actual.
    public void anteriorCancion() {
        if (mCanciones != null) {
            int anterior;
            if (mPosCancionActual >= 0) {
                anterior = mPosCancionActual - 1;
                if (anterior < 0) {
                    anterior = mCanciones.size() - 1;
                }
            } else {
                anterior = 0;
            }
            reproducirCancion(anterior);
        }
    }

    // Pausa la reproducción.
    public void pausarReproduccion() {
        mReproductor.pause();
    }

    // Retorna si está reproduciendo una canción.
    public boolean reproduciendo() {
        return mReproductor.isPlaying();
    }

    // Continua la reproducción después de una pausa.
    public void continuarReproduccion() {
        mReproductor.start();
    }

    // Para la reproducción.
    public void pararReproduccion() {
        mReproductor.stop();
    }

    // Establece la lista de canciones.
    public void setLista(ArrayList<Cancion> list) {
        mCanciones = list;
    }

    // Limpia la lista de canciones.
    public void limpiarLista() {
        mCanciones = null;
    }

    // Agrega una canción a la lista de canciones.
    public void agregarCancion(Cancion cancion) {
        if (mCanciones == null) {
            mCanciones = new ArrayList<Cancion>();
        }
        mCanciones.add(cancion);
    }

    // Retorna el índice de la canción actual.
    public int getPosCancionActual() {
        return mPosCancionActual;
    }

}