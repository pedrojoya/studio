package es.iessaladillo.pedrojoya.pr109;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.apache.http.HttpStatus;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.iessaladillo.pedrojoya.pr109.Adapters.TareasAdapter;
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import es.iessaladillo.pedrojoya.pr109.Model.Usuario;
import es.iessaladillo.pedrojoya.pr109.Model.UsuarioNuevo;
import es.iessaladillo.pedrojoya.pr109.api.ApiService;
import retrofit.RetrofitError;


public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.lstTareas)
    ListView mLstTareas;
    @InjectView(R.id.swlPanel)
    SwipeRefreshLayout mSwlPanel;

    private TareasAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        // Se inicializan las vistas.
        initVistas();
        // Se realiza el login.
        // login();
        // Se obtienen los datos.
        // onRefresh();
    }

    // Inicializa las vistas.
    private void initVistas() {
        mSwlPanel.setOnRefreshListener(this);
        mSwlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        adaptador = new TareasAdapter(this, new ArrayList<Tarea>());
        mLstTareas.setAdapter(adaptador);

    }

    // Cuando se hace swipe to refresh.
    @Override
    public void onRefresh() {
        mSwlPanel.setRefreshing(true);
        // Se solicita la lista de tareas.
        App.getApiService().listTareas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuRegistrarse:
                signUp();
                return true;
            case R.id.mnuLogin:
                login();
                return true;
            case R.id.mnuListar:
                // Se solicita la lista de tareas.
                App.getApiService().listTareas();
                return true;
            case R.id.mnuAgregar:
                // Se solicita agregar la tarea.
                Tarea tarea = new Tarea("Estudiar" , "Estudiante");
                App.getApiService().createTarea(tarea);
                return true;
            case R.id.mnuActualizar:
                // Se solicita agregar la tarea.
                Tarea tareaUpdate = new Tarea("Estudia más" , "Estudiante vago");
                App.getApiService().updateTarea("L49ysm6li2", tareaUpdate);
                return true;
            case R.id.mnuEliminar:
                // Se solicita eliminar la tarea.
                App.getApiService().deleteTarea("MDs1FS5WFM");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se registra la actividad en el bus.
        App.getEventBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // La actividad se desregistra del bus.
        App.getEventBus().unregister(this);
    }

    // Cuando se produce el evento de que las tareas ya hayan sido obtenidas.
    @Subscribe
    public void onTareasListadas(ApiService.TareasListedEvent event) {
        // Se añaden las tareas al adaptador.
        adaptador.clear();
        adaptador.addAll(event.getTareas());
        // Se cancela la animación de refresco.
        mSwlPanel.setRefreshing(false);
    }

    // Solicita que se haga login.
    private void login() {
        App.getApiService().login("baldomero", "llegateligero");
    }

    // Solicita que se haga signup.
    private void signUp() {
        App.getApiService().signUp(new UsuarioNuevo("lorenzo", "lorenzo"));
    }

    // Cuando se produce el evento de que el usuario ha hecho login.
    @Subscribe
    public void onUsuarioLoggedIn(ApiService.UsuarioLoggedInEvent event) {
        Toast.makeText(this, App.getUsuario().getUsername() + " logged in", Toast.LENGTH_SHORT).show();
    }

    // Cuando se produce el evento de que el usuario se ha registrado correctamente (con login incluido).
    @Subscribe
    public void onUsuarioSignedUp(ApiService.UsuarioSignedUpEvent event) {
        Toast.makeText(this, App.getUsuario().getUsername() + " registrado y conectado", Toast.LENGTH_SHORT).show();
    }

    // Si se ha producido un evento de error en la petición.
    @Subscribe
    public void onApiError(ApiService.ApiErrorEvent event) {
        RetrofitError error = event.getError();
        switch (error.getKind()) {
            // Si hay un problema con la red.
            case NETWORK:
                Toast.makeText(this, "Comprueba que tienes internet", Toast.LENGTH_SHORT).show();
                break;
            case HTTP:
                int status = error.getResponse().getStatus();
                if (status == HttpStatus.SC_FORBIDDEN) {
                    Toast.makeText(this, "Hay que estar logueado", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.d("Mia", error.getMessage());
                break;
        }
    }

    @Subscribe
    public void onLoginNeeded(ApiService.LoginNeededEvent event) {
        Toast.makeText(this, "Hay que estar logueado", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onTareaCreada(ApiService.TareaCreatedEvent event) {
        Toast.makeText(this, event.getTarea().getObjectId(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onTareaActualizada(ApiService.TareaUpdatedEvent event) {
        Toast.makeText(this, event.getTarea().getUpdatedAt(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onTareaEliminada(ApiService.TareaDeletedEvent event) {
        Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
    }

}
