package es.iessaladillo.pedrojoya.pr109.api;

import es.iessaladillo.pedrojoya.pr109.Model.Resultado;
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

public class ApiClient {

    private static ApiInterface apiService;

    public static ApiInterface getApiClient() {
        if (apiService == null) {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("X-Parse-Application-Id", "xgYR15iQQ1kyFoNt2ZrW6qgxF5sXtgXRDgHbzy0f");
                    request.addHeader("X-Parse-REST-API-Key", "wlfZg4Wz4GSKdpny577Xq9hLoHqEIByy6BiPBmpd");
                    request.addHeader("Content-Type", "application/json");
                }
            };
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.parse.com/1")
                    .setRequestInterceptor(requestInterceptor)
                    .build();
            apiService = restAdapter.create(ApiInterface.class);
        }
        return apiService;
    }

    public interface ApiInterface {
        @GET("/classes/Tareas")
        void listarTareas(@Query("order") String orden, Callback<Resultado<Tarea>> cb);
    }
}
