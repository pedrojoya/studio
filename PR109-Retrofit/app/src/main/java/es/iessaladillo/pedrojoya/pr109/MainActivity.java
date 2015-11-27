package es.iessaladillo.pedrojoya.pr109;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import es.iessaladillo.pedrojoya.pr109.api.ApiService;
import retrofit.RetrofitError;


public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.lstTareas)
    ListView mLstTareas;
    @InjectView(R.id.swlPanel)
    SwipeRefreshLayout mSwlPanel;

    private TareasAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        // Se inicializan las vistas.
        initVistas();
    }

    private void checkUserLoggedIn() {
        SharedPreferences preferencias = getSharedPreferences(LoginActivity.PREFS_USUARIO, MODE_PRIVATE);
        Usuario usuario = App.getUsuario();
        usuario.setObjectId(preferencias.getString(LoginActivity.PREF_OBJECT_ID, ""));
        usuario.setSessionToken(preferencias.getString(LoginActivity.PREF_SESSION_TOKEN, ""));
        if (usuario == null || !usuario.isLoggedIn()) {
            mostrarLogin();
        } else {
            // Se obtienen los datos.
            onRefresh();
        }
    }

    private void mostrarLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // Inicializa las vistas.
    private void initVistas() {
        mSwlPanel.setOnRefreshListener(this);
        mSwlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mAdaptador = new TareasAdapter(this, new ArrayList<Tarea>());
        mLstTareas.setAdapter(mAdaptador);
        mLstTareas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Se establece el listener para los eventos del modo de acción
        // contextual.
        mLstTareas
                .setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                    // Al preparse el modo.
                    @Override
                    public boolean onPrepareActionMode(ActionMode mode,
                                                       Menu menu) {
                        // No se hace nada.
                        return false;
                    }

                    // Al destruirse el modo.
                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        // No se hace nada.
                    }

                    // Al crear el modo.
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        // Se infla la especificación del menú contextual en el
                        // menú.
                        mode.getMenuInflater().inflate(R.menu.contextual_activity_main,
                                menu);
                        // Se retorna que ya se ha gestionado el evento.
                        return true;
                    }

                    // Al hacer click sobre un ítem de acción del modo
                    // contextual.
                    @Override
                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                        // Dependiendo del elemento pulsado.
                        switch (item.getItemId()) {
                            case R.id.mnuEliminar:
                                // Se obtienen los elementos seleccionados (y se
                                // quita la selección).
                                ArrayList<Tarea> elems = getElementosSeleccionados(
                                        mLstTareas, true);
                                // Se eliminan del mAdaptador.
                                for (Tarea elemento : elems) {
                                    App.getApiService().deleteTarea(elemento);
                                }
                                // Se notifica al mAdaptador que ha habido cambios.
                                // mAdaptador.notifyDataSetChanged();
                                break;
                        }
                        // Se retorna que se ha procesado el evento.
                        return true;
                    }

                    // Al cambiar el estado de selección de un elemento.
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode,
                                                          int position, long id, boolean checked) {
                        // Se actualiza el título de la action bar contextual.
                        mode.setTitle(getString(R.string.de, mLstTareas.getCheckedItemCount(),
                                mLstTareas.getCount()));
                    }
                });
        // Un click simple ya activa el modo de acción contextual.
        mLstTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adaptador, View v,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TareaActivity.class);
                intent.putExtra(TareaActivity.EXTRA_TAREA, (Tarea) mLstTareas.getItemAtPosition(position));
                startActivity(intent);
            }
        });

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
            case R.id.mnuAgregar:
                // Se solicita agregar la tarea.
                startActivity(new Intent(this, TareaActivity.class));
                return true;
            case R.id.mnuLogout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences preferencias = getSharedPreferences(LoginActivity.PREFS_USUARIO, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove(LoginActivity.PREF_OBJECT_ID);
        editor.remove(LoginActivity.PREF_SESSION_TOKEN);
        mostrarLogin();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se registra la actividad en el bus.
        App.getEventBus().register(this);
        checkUserLoggedIn();
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
        // Se añaden las tareas al mAdaptador.
        mAdaptador.clear();
        mAdaptador.addAll(event.getTareas());
        // Se cancela la animación de refresco.
        mSwlPanel.setRefreshing(false);
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
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Subscribe
    public void onTareaCreada(ApiService.TareaCreatedEvent event) {
        mAdaptador.insert(event.getTarea(), 0);
        mAdaptador.notifyDataSetChanged();
    }

    @Subscribe
    public void onTareaActualizada(ApiService.TareaUpdatedEvent event) {
        Toast.makeText(this, event.getTarea().getUpdatedAt(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onTareaEliminada(ApiService.TareaDeletedEvent event) {
        mAdaptador.remove(event.getTarea());
    }

    // Retorna un ArrayList con los elementos seleccionados. Recibe la lista y
    // si debe quitarse la selección una vez obtenidos los elementos.
    private ArrayList<Tarea> getElementosSeleccionados(ListView lst,
                                                       boolean uncheck) {
        // ArrayList resultado.
        ArrayList<Tarea> datos = new ArrayList<Tarea>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lst.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                int position = selec.keyAt(i);
                // Se quita de la selección (si hay que hacerlo).
                if (uncheck) {
                    lst.setItemChecked(position, false);
                }
                // Se añade al resultado.
                datos.add((Tarea) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

}
