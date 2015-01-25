package es.iessaladillo.pedrojoya.pr109;

import android.app.Application;

import com.squareup.otto.Bus;

import es.iessaladillo.pedrojoya.pr109.Model.Usuario;
import es.iessaladillo.pedrojoya.pr109.api.ApiService;

public class App extends Application {

    // Bus de eventos de la aplicación (Otto).
    private static Bus sEventBus;
    private static Usuario sUsuario;
    private static ApiService sApiService;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea el bus de eventos.
        sEventBus = new Bus();
        // Se crea el servicio de conexión a la API.
        sApiService = new ApiService(sEventBus);
        // Se registra el servicio de la api en el bus.
        // sEventBus.register(apiService);
    }

    // Retorna el bus de eventos de la aplicación.
    public static Bus getEventBus() {
        // Si no existe, se crea.
        if (sEventBus == null) {
            sEventBus = new Bus();
        }
        return sEventBus;
    }

    // Retorna el servicio de conexión a la API.
    public static ApiService getApiService() { return  sApiService; }

    // Retorna el usuario actual.
    public static Usuario getUsuario() {
        return sUsuario;
    }

    // Establece el usuario actual.
    public static void setUsuario(Usuario usuario) {
        sUsuario = usuario;
    }

}
