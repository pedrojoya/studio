package es.iessaladillo.pedrojoya.pr139;

import com.squareup.otto.Bus;

import es.iessaladillo.pedrojoya.pr109.App;
import es.iessaladillo.pedrojoya.pr109.Model.ACLField;
import es.iessaladillo.pedrojoya.pr109.Model.ACLPermisos;
import es.iessaladillo.pedrojoya.pr109.Model.Resultado;
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import es.iessaladillo.pedrojoya.pr109.Model.TareaACL;
import es.iessaladillo.pedrojoya.pr109.Model.Usuario;
import es.iessaladillo.pedrojoya.pr109.Model.UsuarioNuevo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public class InstagramClient {

    // Interfaz de trabajo de Retrofit contra la API.
    public interface ApiInterface {
        @GET("/classes/Tareas")
        void retrieveTareas(@Header("X-Parse-Session-Token") String sessionToken, @Query("order") String orden, Callback<Resultado<Tarea>> cb);

        @GET("/login")
        void login(@Query("username") String username, @Query("password") String password, Callback<Usuario> cb);

        @POST("/users")
        void signUp(@Body UsuarioNuevo usuarioNuevo, Callback<Usuario> cb);

        @POST("/classes/Tareas")
        void createTarea(@Header("X-Parse-Session-Token") String sessionToken, @Body TareaACL tarea, Callback<Tarea> cb);

        @PUT("/classes/Tareas/{id}")
        void updateTarea(@Header("X-Parse-Session-Token") String sessionToken, @Path("id") String objectId, @Body Tarea tarea, Callback<Tarea> cb);

        @DELETE("/classes/Tareas/{id}")
        void deleteTarea(@Header("X-Parse-Session-Token") String sessionToken, @Path("id") String objectId, Callback<Tarea> cb);
    }

    private static ApiInterface mApiInterface;

    // Constructor privado para que la clase no pueda instanciarse.
    private InstagramClient() {}

    // Retorna la interfaz de
    public static ApiInterface getApiInterface() {
        if (mApiInterface == null) createInstance();
        return mApiInterface;
    }

    // Crea la instancia del cliente.
    private static void createInstance() {
        if (mApiInterface == null) {
            synchronized(InstagramClient.class) {
                if (mApiInterface == null) {
                    mApiInterface = buildApiClient();
                }
            }
        }
    }

    // Construye y retorna el cliente de acceso a la API a través de Retrofit.
    private static ApiInterface buildApiClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.parse.com/1")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(ApiInterface.class);
    }

}
