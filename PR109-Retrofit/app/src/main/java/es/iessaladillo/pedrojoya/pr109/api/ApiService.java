package es.iessaladillo.pedrojoya.pr109.api;

import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import es.iessaladillo.pedrojoya.pr109.Model.Resultado;
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

public class ApiService {

    // Interfaz de trabajo de Retrofit contra la API.
    public interface ApiInterface {
        @GET("/classes/Tareas")
        void listarTareas(@Query("order") String orden, Callback<Resultado<Tarea>> cb);
    }

    // Evento: se desea obtener la lista de tareas.
    public static class ListarTareasEvent {
    }

    // Evento: tareas cargadas. El constructor recibe la lista de tareas.
    public static class TareasListadasEvent {
        private final List<Tarea> mTareas;

        public TareasListadasEvent(List<Tarea> tareas) {
            mTareas = tareas;
        }

        // Retorna la lista de tareas.
        public List<Tarea> getTareas() {
            return mTareas;
        }
    }

    // Evento: Error en la API.
    public static class ApiErrorEvent {
        private final RetrofitError mError;

        public ApiErrorEvent(RetrofitError error) {
            this.mError = error;
        }

        public String getErrorMessage() {
            return mError.getMessage();
        }
    }

    private final ApiService.ApiInterface mApiClient;
    private final Bus mBus;

    // Constructor.
    public ApiService(Bus bus) {
        // Se construye el cliente de acceso a la API a través de Retrofit.
        mApiClient = buildApiClient();
        mBus = bus;
    }

    // Cuando se ha solicitado obtener la lista de tareas.
    @Subscribe
    public void onListarTareas(ListarTareasEvent event) {
        // Se realiza la petición a través de Retrofit.
        mApiClient.listarTareas("-updatedAt", new Callback<Resultado<Tarea>>() {
            @Override
            public void success(Resultado<Tarea> resultadoTareas, Response response) {
                // Se envía al bus el evento de que ya están disponibles las tareas.
                mBus.post(new TareasListadasEvent(resultadoTareas.getResults()));
            }

            @Override
            public void failure(RetrofitError error) {
                // Se envía al bus el evento de que se ha producido un error en una petición.
                mBus.post(new ApiErrorEvent(error));
            }
        });
    }

    // Si se ha producido un evento de error en la petición.
    @Subscribe
    public void onApiError(ApiErrorEvent event) {
        Log.e("Mia", event.getErrorMessage());
    }

    // Construye y retorna el cliente de acceso a la API a través de Retrofit.
    private ApiInterface buildApiClient() {
        // Se crea un interceptor de peticiones para que añada las cabeceras necesarias en
        // todas las peticiones.
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
        return restAdapter.create(ApiInterface.class);
    }

}
