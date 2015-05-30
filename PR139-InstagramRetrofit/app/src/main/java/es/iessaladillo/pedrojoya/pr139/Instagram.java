package es.iessaladillo.pedrojoya.pr139;

import es.iessaladillo.pedrojoya.pr139.api.TagResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

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


    // Retorna la interfaz de
    public static ApiInterface getApiInterface() {
        if (mApiInterface == null) createInstance();
        return mApiInterface;
    }

    // Crea la instancia del cliente.
    private static void createInstance() {
        if (mApiInterface == null) {
            synchronized(Instagram.class) {
                if (mApiInterface == null) {
                    mApiInterface = buildApiClient();
                }
            }
        }
    }

    // Construye y retorna el cliente de acceso a la API a través de Retrofit.
    private static ApiInterface buildApiClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(ApiInterface.class);
    }

}
