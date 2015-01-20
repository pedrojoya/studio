package es.iessaladillo.pedrojoya.pr109;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.iessaladillo.pedrojoya.pr109.Adapters.TareasAdapter;
import es.iessaladillo.pedrojoya.pr109.Model.Resultado;
import es.iessaladillo.pedrojoya.pr109.Model.Tarea;
import es.iessaladillo.pedrojoya.pr109.api.ApiClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.lstTareas)
    ListView mLstTareas;
    @InjectView(R.id.swlPanel)
    SwipeRefreshLayout mSwlPanel;

    private ApiClient.ApiInterface apiClient;
    private TareasAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        // Se obtiene el cliente para hacer las peticiones a la API.
        apiClient = ApiClient.getApiClient();
        // Se inicializan las vistas.
        initVistas();
        // Se obtienen los datos.
        onRefresh();
    }

    private void initVistas() {
        mSwlPanel.setOnRefreshListener(this);
        mSwlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        adaptador = new TareasAdapter(this, new ArrayList<Tarea>());
        mLstTareas.setAdapter(adaptador);

    }

    private void getTareas() {
        apiClient.listarTareas("-updatedAt", new Callback<Resultado<Tarea>>() {
            @Override
            public void success(Resultado<Tarea> resultadoTareas, Response response) {
                // Se añaden las tareas al adaptador.
                adaptador.clear();
                for (Tarea tarea : resultadoTareas.getResults()) {
                    adaptador.add(tarea);
                }
                // Se cancela la animación de refresco.
                mSwlPanel.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Mia", "Error al obtener las tareas");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Cuando se hace swipe to refresh.
    @Override
    public void onRefresh() {
        // Se obtienen las tareas y se carga el adaptador.
        getTareas();
    }

}
