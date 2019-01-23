package es.iessaladillo.pedrojoya.pr194.base;


import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TagCallFactory implements Call.Factory {

    private final OkHttpClient httpClient;

    public TagCallFactory(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    @NonNull
    public Call newCall(@NonNull Request request) {
        // Hacemos que el tag sea un array para que se modificable posteriormente.
        request = request.newBuilder().tag(new String[]{null}).build();
        return httpClient.newCall(request);
    }

}
