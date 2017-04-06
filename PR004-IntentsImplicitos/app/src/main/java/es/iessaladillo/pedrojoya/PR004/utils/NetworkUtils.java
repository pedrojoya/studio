package es.iessaladillo.pedrojoya.PR004.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    private NetworkUtils() {
    }

    // Retorna si hay conexión a Internet.
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager gestorConectividad = (ConnectivityManager) context
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return infoRed != null && infoRed.isConnected();
    }

}
