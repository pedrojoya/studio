package es.iessaladillo.pedrojoya.pr134.base;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class ConnectionStatus {

    private final boolean connected;
    private final boolean wifiConnection;
    private final boolean mobileConnection;

    ConnectionStatus(@NonNull Intent intent) {
        if (intent.getExtras() != null) {
            @SuppressWarnings("deprecation") NetworkInfo activeNetwork = (NetworkInfo) intent.getExtras()
                    .get(ConnectivityManager.EXTRA_NETWORK_INFO);
            connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            wifiConnection = connected && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnection =
                    connected && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            connected = false;
            wifiConnection = false;
            mobileConnection = false;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean hasWifiConnection() {
        return wifiConnection;
    }

    public boolean hasMobileConnection() {
        return mobileConnection;
    }

}
