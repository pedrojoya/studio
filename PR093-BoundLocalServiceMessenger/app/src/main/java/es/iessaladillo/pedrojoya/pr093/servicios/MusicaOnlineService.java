package es.iessaladillo.pedrojoya.pr093.servicios;

import android.app.Service;
import android.content.Context;
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
    public static final int MSG_CANCION_ACTUAL = 6;
    public static final int MSG_REPRODUCIENDO = 7;
    private static final String EXTRA_LISTA = "lista";
    private static final String EXTRA_CLIENTE = "cliente";

    // Variables.
    private MediaPlayer mReproductor;
    private ArrayList<Cancion> mCanciones;
    private int mPosCancionActual = -1;
    private Messenger receptorMensajes;
    private Messenger emisorMensajes;

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
                    responderIsReproduciendo();
                    break;
                case MSG_PAUSAR:
                    // Se pausa la reproducción.
                    pausarReproduccion();
                    responderIsReproduciendo();
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
                    ArrayList<Cancion> lista = msg.getData().getParcelableArrayList(EXTRA_LISTA);
                    setLista(lista);
                    break;
                case MSG_CANCION_ACTUAL:
                    // Se envía un mensaje de vuelta con la posición de la canción actual.
                    responderGetPosCancionActual();
                    break;
                case MSG_REPRODUCIENDO:
                    // Se envía un mensaje de vuelta con un 1 si está reproduciendo.
                    responderIsReproduciendo();
                    break;

            }
        }
    }

    // API.

    // Construye un intent para la conexión con servicio.
    public static Intent getIntent(Context context, Messenger receptor) {
        // Se crea el intent de vinculación con el servicio.
        Intent intent = new Intent(context,
                MusicaOnlineService.class);
        intent.putExtra(EXTRA_CLIENTE, receptor);
        return intent;
    }

    // Envía un mensaje al servicio para establecer la lista de reproducción.
    public static void setLista(Messenger emisor, ArrayList<Cancion> lista) {
        Message mensaje = Message.obtain(null, MSG_ESTABLECER__LISTA);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_LISTA, lista);
        if (mensaje != null) {
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
        try {
            emisor.send(mensaje);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Envía un mensaje al servicio para pausar la reproducción.
    public static void pausarReproduccion(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_PAUSAR);
        try {
            emisor.send(mensaje);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Envía un mensaje al servicio para reproducir la siguiente canción.
    public static void siguienteCancion(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_SIGUIENTE);
        try {
            emisor.send(mensaje);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Envía un mensaje al servicio para reproducir la anterior canción.
    public static void anteriorCancion(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_ANTERIOR);
        try {
            emisor.send(mensaje);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Envía un mensaje al servicio para solicitar si se está reproduciendo actualmente.
    public static void askIsReproduciendo(Messenger emisor) {
        Message mensaje = Message.obtain(null, MusicaOnlineService.MSG_REPRODUCIENDO);
        if (mensaje != null) {
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Envía un mensaje al servicio para obtener la canción actual.
    public static void askPosCancionActual(Messenger emisor) {
        Message mensaje = Message.obtain(null, MSG_CANCION_ACTUAL);
        if (mensaje != null) {
            try {
                emisor.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Retorna la posición de la canción actual, en base al mensaje recibido.
    public static int getPosCancionActual(Message mensaje) {
        return mensaje.arg1;
    }

    // Retorna si se está reproduciendo alguna canción, en base al mensaje recibido.
    public static boolean isReproduciendo(Message mensaje) {
        return mensaje.arg1 != 0;
    }

    // FIN API.

    // MENSAJES DE RESPUESTA

    private void responderIsReproduciendo() {
        Message mensaje = Message.obtain(null, MSG_REPRODUCIENDO);
        if (mensaje != null) {
            mensaje.arg1 = reproduciendo()?1:0;
            try {
                emisorMensajes.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void responderGetPosCancionActual() {
        Message mensaje = Message.obtain(null, MSG_CANCION_ACTUAL);
        if (mensaje != null) {
            mensaje.arg1 = getPosCancionActual();
            try {
                emisorMensajes.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // FIN MENSAJES DE RESPUESTA

    // Al crear el servicio.
    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea y configura el reproductor.
        mReproductor = new MediaPlayer();
        // Se crea un objeto Messenger asociado a un objeto gestor de mensajes recibidos.
        receptorMensajes = new Messenger(new ReceptorMensajes());
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

    // Cuando se establece el vínculo.
    @Override
    public IBinder onBind(Intent intent) {
        // Se obtiene del intent el messenger del cliente para que podamos enviarle mensajes.
        emisorMensajes = (Messenger) intent.getParcelableExtra(EXTRA_CLIENTE);
        // Se retorna la interfaz de vinculación con el receptor de mensajes del servicio.
        return receptorMensajes.getBinder();
    }

    // Cuando ya está preparada la reproducción de la canción.
    @Override
    public void onPrepared(MediaPlayer arg0) {
        // Se inicia la reproducción.
        mReproductor.start();
        // Se envía un mensaje informativo al cliente.
        responderGetPosCancionActual();
        responderIsReproduciendo();
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

    // Retorna si está reproduciendo una canción.
    private boolean reproduciendo() {
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

    // Retorna el índice de la canción actual.
    private int getPosCancionActual() {
        return mPosCancionActual;
    }

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
        return true;
    }

}