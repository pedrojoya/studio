package es.iessaladillo.pedrojoya.pr139;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;

import es.iessaladillo.pedrojoya.pr139.api.TagResponse;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
        // Interceptor para los logs.
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // Se obtiene el constructor del cliente HTTP.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // Se establece la caché.
        builder.cache(createCache(context));
        // Se añade un interceptor para añadir las cabeceras necesarias
        // para trabajar con la caché.
        builder.addInterceptor(new Interceptor() {
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
        // Se añade el interceptor para los logs.
        builder.addInterceptor(logInterceptor);
        // Se añade el interceptor para Stetho.
        builder.addInterceptor(new StethoInterceptor());
        // Se construye el cliente HTTP.
        OkHttpClient client = builder.build();
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
        // Se crea y retorna la caché, indicando el directorio y el tamaño (10 megas)
        return new Cache(httpCacheDirectory, 10 * 1024);
    }

}