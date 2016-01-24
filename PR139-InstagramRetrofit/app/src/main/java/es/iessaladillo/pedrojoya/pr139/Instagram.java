package es.iessaladillo.pedrojoya.pr139;

import android.content.Context;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;

import es.iessaladillo.pedrojoya.pr139.api.TagResponse;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

// Clase contrato con las claves de Instagram.
class Instagram {

    // Constantes.
    public static final String CLIENT_ID = "c432d0e158dd46f7873950a19a582102";
    public static final String BASE_URL = "https://api.instagram.com/v1/";
    public static final String TIPO_ELEMENTO_IMAGEN = "image";

    // Interfaz de trabajo de Retrofit contra la API.
    public interface ApiInterface {
        @GET("tags/{tag}/media/recent")
        Call<TagResponse> getTagPhotos(@Path("tag") String tag,
                                       @Query("client_id") String clientId,
                                       @Query("max_tag_id") String maxTagId);

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
        OkHttpClient client = new OkHttpClient();
        client.setCache(createCache(context));
        // Se le añade un interceptor para añadir las cabeceras necesarias
        // para trabajar con la caché.
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                // Se obtiene la petición original.
                Request request = chain.request();
                // Se crea una nueva petición en base a la original pero se le
                // añaden las cabeceras deseadas
                Request newRequest;
                newRequest = request.newBuilder()
                        .addHeader("Accept", "application/json;versions=1")
                        .addHeader("Cache-Control", "public, max-age=" + 60)
                        .build();
                // Se reemplaza la nueva petición por la antigua y se continúa.
                return chain.proceed(newRequest);
            }
        });
        // Se añade un interceptor para los logs.
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(logInterceptor);
        // Se añade el interceptor para Stetho.
        client.networkInterceptors().add(new StethoInterceptor());
        // Se construye el objeto Retrofit y a partir de él se retorna el
        // servicio de acceso a la API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

                .client(client)
                .build();
        return retrofit.create(ApiInterface.class);
    }

    // Retorna la caché para OkHttp.
    private static Cache createCache(Context context) {
        // Se crea el archivo para la caché en el directorio correspondiente.
        File httpCacheDirectory = new File(context.getApplicationContext()
                .getCacheDir().getAbsolutePath(), "HttpCache");
        // Se crea la caché, indicando el directorio y el tamaño (10 megas)
        Cache httpResponseCache = null;
            httpResponseCache = new Cache(httpCacheDirectory, 10 *
                    1024);
        return httpResponseCache;
    }

}
