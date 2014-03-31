package es.iessaladillo.pedrojoya.pr093.servicios;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr093.modelos.Cancion;

public class MusicaOnlineService extends Service implements
        OnCompletionListener, OnPreparedListener {

    // Constantes.
    public static String ACTION_PLAYING = "es.iessaladillo.pedrojoya.pr091.action.playing";

    // Variables.
    private MediaPlayer mReproductor;
    private LocalBinder mBinder;
    private ArrayList<Cancion> mCanciones;
    private int mPosCancionActual = -1;

    // Clase que act�a como Binder con el servicio.
    public class LocalBinder extends Binder {
        // Retorna la instancia del servicio para que el cliente pueda
        // llamar a sus m�todos p�blicos.
        public MusicaOnlineService getService() {
            return MusicaOnlineService.this;
        }
    }

    // Al crear el servicio.
    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea el binder con el que se vincular� la actividad.
        mBinder = new LocalBinder();
        // Se crea y configura el reproductor.
        mReproductor = new MediaPlayer();
    }

    // Al destruir el servicio.
    @Override
    public void onDestroy() {
        // Se para la reproducci�n y se liberan los recursos.
        if (mReproductor != null) {
            mReproductor.stop();
            mReproductor.release();
        }
        super.onDestroy();
    }

    // Reproduce la canci�n con dicha posici�n en la lista.
    public void reproducirCancion(int position) {
        if (mCanciones != null) {
            // Se actualiza cu�l es la canci�n actual.
            mPosCancionActual = position;
            // Se reproduce la url correspondiente a la canci�n.
            reproducirCancion(mCanciones.get(position).getUrl());
        }
    }

    // Env�a un broadcast informativo de que se est� reproduciendo una canci�n.
    private void enviarBroadcast() {
        // Se env�a un intent informativo a los receptores locales registrados
        // para la acci�n.
        Intent intentRespuesta = new Intent(ACTION_PLAYING);
        LocalBroadcastManager gestor = LocalBroadcastManager.getInstance(this);
        gestor.sendBroadcast(intentRespuesta);
    }

    // Reproduce la url correspondiente a una canci�n.
    public void reproducirCancion(String url) {
        // Se prepara la reproducci�n de la canci�n.
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

    // Cuando se establece el v�nculo.
    @Override
    public IBinder onBind(Intent intent) {
        // Se retorna el binder con el servicio.
        return mBinder;
    }

    // Cuando ya est� preparada la reproducci�n de la canci�n.
    @Override
    public void onPrepared(MediaPlayer arg0) {
        // Se inicia la reproducci�n.
        mReproductor.start();
        // Se env�a un broadcast informativo.
        enviarBroadcast();
    }

    // Cuando ha finalizado la reproducci�n de la canci�n.
    @Override
    public void onCompletion(MediaPlayer arg0) {
        siguienteCancion();
    }

    // Reproduce la siguiente canci�n a la actual.
    public void siguienteCancion() {
        if (mCanciones != null) {
            reproducirCancion((mPosCancionActual + 1) % mCanciones.size());
        }
    }

    // Reproduce la anterior canci�n a la actual.
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

    // Pausa la reproducci�n.
    public void pausarReproduccion() {
        mReproductor.pause();
    }

    // Retorna si est� reproduciendo una canci�n.
    public boolean reproduciendo() {
        return mReproductor.isPlaying();
    }

    // Continua la reproducci�n despu�s de una pausa.
    public void continuarReproduccion() {
        mReproductor.start();
    }

    // Para la reproducci�n.
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

    // Agrega una canci�n a la lista de canciones.
    public void agregarCancion(Cancion cancion) {
        if (mCanciones == null) {
            mCanciones = new ArrayList<Cancion>();
        }
        mCanciones.add(cancion);
    }

    // Retorna el �ndice de la canci�n actual.
    public int getPosCancionActual() {
        return mPosCancionActual;
    }

}