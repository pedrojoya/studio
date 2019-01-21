package es.iessaladillo.pedrojoya.pr180.data.remote;

import android.content.Context;

import com.ashokvarma.gander.GanderInterceptor;

import okhttp3.OkHttpClient;

public class HttpClient {

    private static OkHttpClient instance;

    public static OkHttpClient getInstance(Context context) {
        if (instance == null)
        synchronized (HttpClient.class) {
            if (instance == null) {
                instance = buildOkHttpClient(context.getApplicationContext());
            }
        }
        return instance;
    }

    private static OkHttpClient buildOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
            .addInterceptor(new GanderInterceptor(context))
            .build();
    }

}
