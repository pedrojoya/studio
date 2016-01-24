package es.iessaladillo.pedrojoya.pr140;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import es.iessaladillo.pedrojoya.pr140.data.Escrutinio_sitio;
import retrofit.Call;
import retrofit.Retrofit;
import retrofit.SimpleXmlConverterFactory;
import retrofit.http.GET;
import retrofit.http.Path;

class APIClient {

    private static final String BASE_URL =
            "https://dl.dropboxusercontent" +
                    ".com/u/67422/Android/xml/";

    // Interfaz de trabajo de Retrofit contra la API.
    public interface ApiInterface {

        @GET("{poblacion}.xml")
        Call<Escrutinio_sitio> getPoblacionData(@Path("poblacion") String poblacion);

    }

    // Constructor privado para que NO pueda instanciarse.
    private APIClient() {
    }

    private static ApiInterface mApiInterface;


    // Retorna la interfaz de
    public static ApiInterface getApiInterface() {
        if (mApiInterface == null) createInstance();
        return mApiInterface;
    }

    // Crea la instancia del cliente.
    private static void createInstance() {
        if (mApiInterface == null) {
            synchronized(APIClient.class) {
                if (mApiInterface == null) {
                    mApiInterface = buildApiClient();
                }
            }
        }
    }

    // Construye y retorna el cliente de acceso a la API a través de Retrofit.
    private static ApiInterface buildApiClient() {
        // Se crea el cliente OkHttpClient y se le indica la caché que debe usar.
        OkHttpClient client = new OkHttpClient();
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
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ApiInterface.class);
    }

}
