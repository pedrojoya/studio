package es.iessaladillo.pedrojoya.pr195;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

// Clase contrato con las claves de Instagram.
class Api {

    // Constantes.
    private static final String BASE_URL = "https://dl.dropboxusercontent.com/u/67422/Android/json/";

    // Interfaz de trabajo de Retrofit contra la API.
    @SuppressWarnings("SameParameterValue")
    public interface ApiInterface {
        @GET("datos.json")
        Observable<List<Alumno>> getAlumnos();
    }

    // Constructor privado para que NO pueda instanciarse.
    private Api() {
    }

    private static ApiInterface mApiInterface;


    // Retorna la interfaz de acceso a la API.
    public static ApiInterface getApiInterface(Context context) {
        if (mApiInterface == null) {
            createInstance(context);
        }
        return mApiInterface;
    }

    // Crea la instancia del cliente.
    private static void createInstance(Context context) {
        if (mApiInterface == null) {
            synchronized (Api.class) {
                if (mApiInterface == null) {
                    mApiInterface = buildApiClient(context.getApplicationContext());
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
        // Se añade el interceptor para los logs.
        builder.addInterceptor(logInterceptor);
        // Se añade el interceptor para Stetho.
        builder.addInterceptor(new StethoInterceptor());
        // Se añade el interceptor para Chuck.
        builder.addInterceptor(new ChuckInterceptor(context));
        // Se construye el cliente HTTP.
        OkHttpClient client = builder.build();
        // Se construye el objeto Retrofit y a partir de él se retorna el
        // servicio de acceso a la API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
