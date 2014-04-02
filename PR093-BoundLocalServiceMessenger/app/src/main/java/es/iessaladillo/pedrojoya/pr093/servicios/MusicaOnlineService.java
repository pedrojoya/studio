package es.iessaladillo.pedrojoya.pr093.servicios;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr093.modelos.Cancion;

public class MusicaOnlineService extends Service implements
        OnCompletionListener, OnPreparedListener {

    // Constantes.
    private static final int MSG_REPRODUCIR = 0;
    private static final int MSG_CONTINUAR = 1;
    private static final int MSG_PAUSAR = 2;
    private static final int MSG_SIGUIENTE = 3;
    private static final int MSG_ANTERIOR = 4;
    private static final int MSG_ESTABLECER__LISTA = 5;
    public static final int MSG_ESTADO = 6;
    private static final String EXTRA_LISTA = "lista";
    public static final String ACTION_ESTADO = "estado";
    public static final String EXTRA_POS_CANCION_ACTUAL = "posCancionActual";
    public static final String EXTRA_REPRODUCIENDO = "isReproduciendo";

    // Variables.
    private MediaPlayer mReproductor;
    private ArrayList<Cancion> mCanciones;
    private int mPosCancionActual = -1;
    private Messenger mReceptor;

    // Clase gestora de mensajes recibidos.
    private class ReceptorMensajes extends Handler {
        // Cuando se recibe un mensaje.
        @Override
        public void handleMessage(Message msg) {
            // Dependiendo del mensaje.
            switch (msg.what) {
                case MSG_REPRODUCIR:
                    // Se obtiene la posición de la canción y se reproduce.
                    int position = msg.arg1;
                    reproducirCancion(position);
                    break;
                case MSG_CONTINUAR:
                    // Se continua la reproducción.
                    continuarReproduccion();
                    broadcastEstado();
                    break;
                case MSG_PAUSAR:
                    // Se pausa la reproducción.
                    pausarReproduccion();
                    broadcastEstado();
                    break;
                case MSG_SIGUIENTE:
                    // Se pasa a la siguiente canción.
                    siguienteCancion();
                    break;
                case MSG_ANTERIOR:
                    // Se pasa a la siguiente canción.
                    anteriorCancion();
                    break;
                case MSG_ESTABLECER__LISTA:
                    // Se establece la lista de reproducción.
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        ArrayList<Cancion> lista = bundle.getParcelableArrayList(EXTRA_LISTA);
                        if (lista != null) {
                            setLista(lista);
                        }
                    }
                    break;
                case MSG_ESTADO:
                    // Se envía un mensaje de vuelta con la posición de la canción actual.
                    broadcastEstado();
                    break;
            }
        }
    }

    // API.

    // Envía un mensaje al servicio para establecer la lista de reproducción.
    public static void setLista(Messenger emisor, ArrayList<Cancion> lista) {
        Message mensaje = Message.obtain(null, MSG_ESTABLECER__LISTA);
        if (mensaje != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(EXTRA_LISTA, lista);
            mensaje.setData(bundle);
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Envía un mensaje al servicio para reproducir una canción.
    public static void reproducirCancion(Messenger emisor, int position) {
        Message mensaje = Message.obtain(null, MSG_REPRODUCIR);
        if (mensaje != null) {
            mensaje.arg1 = position;
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Envía un mensaje al servicio para continuar la reproducción.
    public static void continuarReproduccion(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_CONTINUAR);
        if (mensaje != null) {
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Envía un mensaje al servicio para pausar la reproducción.
    public static void pausarReproduccion(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_PAUSAR);
        if (mensaje != null) {
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Envía un mensaje al servicio para reproducir la siguiente canción.
    public static void siguienteCancion(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_SIGUIENTE);
        if (mensaje != null) {
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Envía un mensaje al servicio para reproducir la anterior canción.
    public static void anteriorCancion(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_ANTERIOR);
        if (mensaje != null) {
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Envía un mensaje al servicio para solicitar el estado.
    public static void askEstado(Messenger emisor) {
        Message mensaje = Message.obtain(null, MusicaOnlineService.MSG_ESTADO);
        if (mensaje != null) {
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // FIN API.

    // MENSAJES DE RESPUESTA

    private void broadcastEstado() {
        Intent intent = new Intent(ACTION_ESTADO);
        intent.putExtra(EXTRA_POS_CANCION_ACTUAL, mPosCancionActual);
        intent.putExtra(EXTRA_REPRODUCIENDO, isReproduciendo());
        sendBroadcast(intent);
    }

    // FIN MENSAJES DE RESPUESTA

    // Al crear el servicio.
    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea y configura el reproductor.
        mReproductor = new MediaPlayer();
        // Se crea un objeto Messenger asociado a un objeto gestor de mensajes recibidos.
        mReceptor = new Messenger(new ReceptorMensajes());
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
    private void reproducirCancion(int position) {
        if (mCanciones != null) {
            // Se actualiza cuál es la canción actual.
            mPosCancionActual = position;
            // Se reproduce la url correspondiente a la canción.
            reproducirCancion(mCanciones.get(position).getUrl());
        }
    }

    // Reproduce la url correspondiente a una canción.
    private void reproducirCancion(String url) {
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

    // Cuando ya está preparada la reproducción de la canción.
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Se inicia la reproducción.
        mediaPlayer.start();
        // Se envía un broadcast con el estado a los cliente.
        broadcastEstado();
    }

    // Cuando se establece el vínculo.
    @Override
    public IBinder onBind(Intent intent) {
        // Se retorna la interfaz de vinculación con el receptor de mensajes del servicio.
        return mReceptor.getBinder();
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
    private void anteriorCancion() {
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
    private void pausarReproduccion() {
        mReproductor.pause();
    }

    // Retorna si está isReproduciendo una canción.
    private boolean isReproduciendo() {
        return mReproductor.isPlaying();
    }

    // Continua la reproducción después de una pausa.
    private void continuarReproduccion() {
        mReproductor.start();
    }

    // Establece la lista de canciones.
    private void setLista(ArrayList<Cancion> list) {
        mCanciones = list;
    }

/*
    public void keepAlive(boolean value) {
        if (value) startService(new Intent(getApplicationContext(), getClass()));
        else stopSelf();
    }

    @Override
    public void onRebind(Intent intent) {
        keepAlive(false);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        keepAlive(true);
        return true;
    }
*/

}