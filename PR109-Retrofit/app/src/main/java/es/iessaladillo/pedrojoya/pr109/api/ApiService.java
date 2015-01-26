package es.iessaladillo.pedrojoya.pr109.api;

import android.text.TextUtils;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import java.lang.reflect.Field;
import java.util.List;

import es.iessaladillo.pedrojoya.pr109.App;
import es.iessaladillo.pedrojoya.pr109.Model.ACLField;
import es.iessaladillo.pedrojoya.pr109.Model.ACLPermisos;
import es.iessaladillo.pedrojoya.pr109.Model.Resultado;
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import es.iessaladillo.pedrojoya.pr109.Model.TareaACL;
import es.iessaladillo.pedrojoya.pr109.Model.Usuario;
import es.iessaladillo.pedrojoya.pr109.Model.UsuarioNuevo;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public class ApiService {

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

    // Evento: tareas cargadas. El constructor recibe la lista de tareas.
    public static class TareasListedEvent {
        private final List<Tarea> mTareas;

        public TareasListedEvent(List<Tarea> tareas) {
            mTareas = tareas;
        }

        // Retorna la lista de tareas.
        public List<Tarea> getTareas() {
            return mTareas;
        }
    }

    // Evento: tarea creada.
    public static class TareaCreatedEvent {
        private final Tarea mTarea;

        public TareaCreatedEvent(Tarea tarea) {
            mTarea = tarea;
        }

        public Tarea getTarea() {
            return mTarea;
        }
    }

    // Evento: tarea actualizada.
    public static class TareaUpdatedEvent {
        private final Tarea mTarea;

        public TareaUpdatedEvent(Tarea tarea) {
            mTarea = tarea;
        }

        public Tarea getTarea() {
            return mTarea;
        }
    }

    // Evento: tarea borrada.
    public static class TareaDeletedEvent {
        private final Tarea mTarea;

        public TareaDeletedEvent(Tarea tarea) {
            mTarea = tarea;
        }

        public Tarea getTarea() {
            return mTarea;
        }
    }

    // Evento: es necesario hacer login
    public static class LoginNeededEvent {
    }

    // Evento: login correcto.
    public static class UsuarioLoggedInEvent {
    }

    // Evento: signup correcto.
    public static class UsuarioSignedUpEvent {
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

        public RetrofitError getError() {
            return mError;
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

    // Solicita la lista de tareas.
    public void listTareas() {
        Usuario usuario = App.getUsuario();
        if (usuario != null && !TextUtils.isEmpty(usuario.getSessionToken())) {
            // Se realiza la petición a través de Retrofit.
            mApiClient.retrieveTareas(usuario.getSessionToken(), "-updatedAt", new Callback<Resultado<Tarea>>() {
                @Override
                public void success(Resultado<Tarea> resultadoTareas, Response response) {
                    // Se envía al bus el evento de que ya están disponibles las tareas.
                    mBus.post(new TareasListedEvent(resultadoTareas.getResults()));
                }

                @Override
                public void failure(RetrofitError error) {
                    // Se envía al bus el evento de que se ha producido un error en una petición.
                    mBus.post(new ApiErrorEvent(error));
                }
            });
        } else {
            mBus.post(new LoginNeededEvent());
        }
    }

    // Solicita la creación de una tareas.
    public void createTarea(final Tarea tarea) {
        Usuario usuario = App.getUsuario();
        if (usuario != null && !TextUtils.isEmpty(usuario.getSessionToken())) {
            // Se configura el ACL de la tarea.
            ACLField acl = new ACLField(new ACLPermisos(true, true));
            TareaACL tareaACL = new TareaACL(tarea.getConcepto(), tarea.getResponsable(), acl);
            // Se realiza la petición a través de Retrofit.
            mApiClient.createTarea(usuario.getSessionToken(), tareaACL, new Callback<Tarea>() {
                @Override
                public void success(Tarea t, Response response) {
                    // Se envía al bus el evento de que se ha creado la tarea.
                    t.setUpdatedAt(t.getCreatedAt());
                    t.setConcepto(tarea.getConcepto());
                    t.setResponsable(tarea.getResponsable());
                    mBus.post(new TareaCreatedEvent(t));
                }

                @Override
                public void failure(RetrofitError error) {
                    // Se envía al bus el evento de que se ha producido un error en una petición.
                    mBus.post(new ApiErrorEvent(error));
                }
            });
        } else {
            mBus.post(new LoginNeededEvent());
        }
    }

    // Solicita la actualización de una tarea.
    public void updateTarea(String objectId, Tarea tarea) {
        Usuario usuario = App.getUsuario();
        if (usuario != null && !TextUtils.isEmpty(usuario.getSessionToken())) {
            // Se realiza la petición a través de Retrofit.
            mApiClient.updateTarea(usuario.getSessionToken(), objectId, tarea, new Callback<Tarea>() {
                @Override
                public void success(Tarea tarea, Response response) {
                    // Se envía al bus el evento de que se ha actualizado la tarea.
                    mBus.post(new TareaUpdatedEvent(tarea));
                }

                @Override
                public void failure(RetrofitError error) {
                    // Se envía al bus el evento de que se ha producido un error en una petición.
                    mBus.post(new ApiErrorEvent(error));
                }
            });
        } else {
            mBus.post(new LoginNeededEvent());
        }
    }

    // Solicita la eliminación de una tarea.
    public void deleteTarea(final Tarea tarea) {
        Usuario usuario = App.getUsuario();
        if (usuario != null && !TextUtils.isEmpty(usuario.getSessionToken())) {
            // Se realiza la petición a través de Retrofit.
            mApiClient.deleteTarea(usuario.getSessionToken(), tarea.getObjectId(), new Callback<Tarea>() {
                @Override
                public void success(Tarea t, Response response) {
                    // Se envía al bus el evento de que se ha eliminado la tarea.
                    mBus.post(new TareaDeletedEvent(tarea));
                }

                @Override
                public void failure(RetrofitError error) {
                    // Se envía al bus el evento de que se ha producido un error en una petición.
                    mBus.post(new ApiErrorEvent(error));
                }
            });
        } else {
            mBus.post(new LoginNeededEvent());
        }
    }

    // Realiza login de usuario.
    public void login(String username, String password) {
        // Se realiza la petición a través de Retrofit.
        mApiClient.login(username, password, new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                // Se almacena el usuario.
                App.setUsuario(usuario);
                // Se envía al bus el evento de que se ha hecho el login correctamente.
                mBus.post(new UsuarioLoggedInEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                // Se envía al bus el evento de que se ha producido un error en una petición.
                mBus.post(new ApiErrorEvent(error));
            }
        });
    }

    // Solicita el registro de un nuevo usuario.
    public void signUp(UsuarioNuevo usuarioNuevo) {
        // Se guarda el nombre de usuario suministrado.
        final String username = usuarioNuevo.getUsername();
        // Se realiza la petición a través de Retrofit.
        mApiClient.signUp(usuarioNuevo, new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                // Se almacena el usuario.
                usuario.setUsername(username);
                App.setUsuario(usuario);
                // Se envía al bus el evento de que se ha registrado el usuario.
                mBus.post(new UsuarioSignedUpEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                // Se envía al bus el evento de que se ha producido un error en una petición.
                mBus.post(new ApiErrorEvent(error));
            }
        });
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

        Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(new FieldNamingStrategy() {

                    @Override
                    public String translateName(Field f) {
                        if (f.getName().equals("userACL"))
                            return App.getUsuario().getObjectId();
                        else
                            return f.getName();
                    }
                })
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.parse.com/1")
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
        return restAdapter.create(ApiInterface.class);
    }

}
