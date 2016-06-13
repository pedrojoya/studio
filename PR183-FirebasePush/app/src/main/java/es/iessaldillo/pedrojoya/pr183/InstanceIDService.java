package es.iessaldillo.pedrojoya.pr183;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

// Cuando se obtiene el token que identifica en FCM la instancia
// de la aplicación instalada en el dispositivo.
public class InstanceIDService extends FirebaseInstanceIdService {

    // Cuando se recibe el token.
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        registrarTokenEnMiServidor(token);
    }

    private void registrarTokenEnMiServidor(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(ServidorContrat.TokensTable.TOKEN, token)
                .build();
        Request request = new Request.Builder()
                .url(ServidorContrat.URL_REGISTRO)
                .post(body)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}