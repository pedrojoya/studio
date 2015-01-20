package es.iessaladillo.pedrojoya.pr109;

import android.app.Application;

import com.squareup.otto.Bus;

import es.iessaladillo.pedrojoya.pr109.api.ApiService;

public class App extends Application {

    // Bus de eventos de la aplicación (Otto).
    private static Bus sEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea el bus de eventos.
        sEventBus = new Bus();
        // Se crea el servicio de conexión a la API.
        ApiService apiService = new ApiService(sEventBus);
        // Se registra el servicio de la api en el bus.
        sEventBus.register(apiService);
    }

    // Retorna el bus de eventos de la aplicación.
    public static Bus getEventBus() {
        // Si no existe, se crea.
        if (sEventBus == null) {
            sEventBus = new Bus();
        }
        return sEventBus;
    }

}
