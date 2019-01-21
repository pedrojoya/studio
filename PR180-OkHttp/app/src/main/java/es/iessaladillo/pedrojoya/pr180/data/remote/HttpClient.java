package es.iessaladillo.pedrojoya.pr180.data.remote;

import android.content.Context;
import android.text.TextUtils;

import com.ashokvarma.gander.GanderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class HttpClient {

    private static OkHttpClient instance;

    public static OkHttpClient getInstance(Context context) {
        if (instance == null) synchronized (HttpClient.class) {
            if (instance == null) {
                instance = buildOkHttpClient(context.getApplicationContext());
            }
        }
        return instance;
    }

    private static OkHttpClient buildOkHttpClient(Context context) {
        return new OkHttpClient.Builder().addInterceptor(new GanderInterceptor(context))
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();
    }

    public static void cancelCallsWithTag(OkHttpClient client, String tag) {
        for (Call call : client.dispatcher().queuedCalls()) {
            if (TextUtils.equals((String) call.request().tag(), tag)) call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (TextUtils.equals((String) call.request().tag(), tag)) call.cancel();
        }
    }

}
