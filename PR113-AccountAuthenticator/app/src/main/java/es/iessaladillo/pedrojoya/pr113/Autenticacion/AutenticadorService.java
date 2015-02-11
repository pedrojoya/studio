package es.iessaladillo.pedrojoya.pr113.Autenticacion;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AutenticadorService extends Service {

    // Al vincularse.
    @Override
    public IBinder onBind(Intent intent) {
        // Se crea el autenticador.
        Autenticador autenticador = new Autenticador(this);
        // Se retorna el vínculo con el autenticador.
        return autenticador.getIBinder();
    }

}
