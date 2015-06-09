package es.iessaladillo.pedrojoya.pr139;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import es.iessaladillo.pedrojoya.pr139.api.TagResponse;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

;

// Clase contrato con las claves de Instagram.
class Instagram {

    // Constantes.
    public static final String CLIENT_ID = "c432d0e158dd46f7873950a19a582102";
    public static final String BASE_URL = "https://api.instagram.com/v1";
    public static final String TIPO_ELEMENTO_IMAGEN = "image";

    // Interfaz de trabajo de Retrofit contra la API.
    public interface ApiInterface {

        @GET("/tags/{tag}/media/recent")
        void getTagPhotos(@Path("tag") String tag,
                          @Query("client_id") String clientId,
                          @Query("max_tag_id") String maxTagId,
                          Callback<TagResponse> cb);

    }

    // Constructor privado para que NO pueda instanciarse.
    private Instagram() {
    }

    private static ApiInterface mApiInterface;


    // Retorna la interfaz de acceso a la API.
    public static ApiInterface getApiInterface(Context context) {
        if (mApiInterface == null) createInstance(context);
        return mApiInterface;
    }

    // Crea la instancia del cliente.
    private static void createInstance(Context context) {
        if (mApiInterface == null) {
            synchronized (Instagram.class) {
                if (mApiInterface == null) {
                    mApiInterface = buildApiClient(context);
                }
            }
        }
    }

    // Construye y retorna el cliente de acceso a la API a través de Retrofit.
    private static ApiInterface buildApiClient(Context context) {
        // Se crea el cliente OkHttpClient y se le indica la caché que debe usar.
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(createCache(context));
        // Se construye el cliente.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json;versions=1");
                            int maxAge = 60; // read from cache for 1 minute
                            // Se añade la línea de cabecera necesaria para trabajar con la caché.
                            request.addHeader("Cache-Control", "public, max-age=" + maxAge);
                    }
                })
                .build();
        return restAdapter.create(ApiInterface.class);
    }

    // Retorna la caché para OkHttp.
    private static Cache createCache(Context context) {
        // Se crea el archivo para la caché en el directorio correspondiente.
        File httpCacheDirectory = new File(context.getApplicationContext()
                .getCacheDir().getAbsolutePath(), "HttpCache");
        // Se crea la caché, indicando el directorio y el tamaño (10 megas)
        Cache httpResponseCache = null;
        try {
            httpResponseCache = new Cache(httpCacheDirectory, 10 *
                    1024);
        } catch (IOException e) {
            Log.e("Mia", "Could not create http cache", e);
        }
        return httpResponseCache;
    }

}
