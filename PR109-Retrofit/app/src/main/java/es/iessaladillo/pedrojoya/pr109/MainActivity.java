package es.iessaladillo.pedrojoya.pr109;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.iessaladillo.pedrojoya.pr109.Adapters.TareasAdapter;
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import es.iessaladillo.pedrojoya.pr109.api.ApiService;


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
        // Se obtienen los datos.
        onRefresh();
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
        // Se envía al bus el evento de que se desea obtener la lista de tareas.
        App.getEventBus().post(new ApiService.ListarTareasEvent());
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se registra la actividad en el bus.
        App.getEventBus().register(this);
        // Se envía un evento de petición de la lista de tareas.
        App.getEventBus().post(new ApiService.ListarTareasEvent());
    }

    @Override
    public void onPause() {
        super.onPause();
        // La actividad se desregistra del bus.
        App.getEventBus().unregister(this);
    }

    // Cuando se produce el evento de que las tareas ya hayan sido obtenidas.
    @Subscribe
    public void onTareasListadas(ApiService.TareasListadasEvent event) {
        // Se añaden las tareas al adaptador.
        adaptador.clear();
        adaptador.addAll(event.getTareas());
        // Se cancela la animación de refresco.
        mSwlPanel.setRefreshing(false);
    }


}
