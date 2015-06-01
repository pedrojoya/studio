package es.iessaladillo.pedrojoya.pr140;

import es.iessaladillo.pedrojoya.pr140.data.Escrutinio_sitio;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;
import retrofit.http.GET;
import retrofit.http.Path;

class APIClient {

    private static final String BASE_URL =
            "https://dl.dropboxusercontent" +
                    ".com/u/67422/Android/xml";

    // Interfaz de trabajo de Retrofit contra la API.
    public interface ApiInterface {

        @GET("/{poblacion}.xml")
        void getPoblacionData(@Path("poblacion") String poblacion,
                              Callback<Escrutinio_sitio> cb);

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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new SimpleXMLConverter())
                        .build();
        return restAdapter.create(ApiInterface.class);
    }

}
