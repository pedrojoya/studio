package es.iessaladillo.pedrojoya.pr139;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr139.api.Datum;
import es.iessaladillo.pedrojoya.pr139.api.TagResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FotosAdapter mAdaptador;
    private String mMaxTagId;
    private SwipeRefreshLayout swlPanel;
    private EndlessRecyclerOnScrollListener mEndlessScrollListener;
    private Instagram.ApiInterface mApiClient;

    // Al crearse la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se obtiene la interfaz de acceso a la api.
        mApiClient = Instagram.getApiInterface(this);
        // Se cargan los datos iniciales.
        swlPanel.post(new Runnable() {
            @Override
            public void run() {
                swlPanel.setRefreshing(true);
                onRefresh();
            }
        });
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        swlPanel = (SwipeRefreshLayout) findViewById(R.id.swlPanel);
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(this);
        RecyclerView lstFotos = (RecyclerView) this.findViewById(R.id.lstFotos);
        mAdaptador = new FotosAdapter(new ArrayList<Foto>());
        lstFotos.setHasFixedSize(true);
        lstFotos.setAdapter(mAdaptador);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, getResources()
                .getInteger(R.integer.grid_columns),
                LinearLayoutManager.VERTICAL, false);
        lstFotos.setLayoutManager(gridLayoutManager);
        lstFotos.setItemAnimator(new DefaultItemAnimator());
        mEndlessScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                obtenerDatos();
            }
        };
        lstFotos.addOnScrollListener(mEndlessScrollListener);
    }

    private void configToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    // Obtiene los datos JSON de la lista de fotos de Instagram.
    private void obtenerDatos() {
        mApiClient.getTagPhotos("algeciras", Instagram.CLIENT_ID, mMaxTagId,
                new Callback<TagResponse>() {
            @Override
            public void success(TagResponse tagResponse, Response response) {
                // Se obtiene la próxima URL.
                mMaxTagId = tagResponse.getPagination().getNextMaxTagId();
                Log.d("Mia", mMaxTagId);
                // Se añade cada foto al adaptador.
                List<Datum> datos = tagResponse.getData();
                for (Datum dato : datos) {
                    if (dato.getType().equals(
                            Instagram.TIPO_ELEMENTO_IMAGEN)) {
                        // Se crea un objeto modelo.
                        Foto foto = new Foto();
                        foto.setDescripcion(dato.getUser().getUsername());
                        foto.setUrl(dato.getImages().getThumbnail().getUrl());
                        mAdaptador.addItem(foto, mAdaptador.getItemCount());
                    }
                }
                // Se indica al gridview que ya ha finalizado la carga.
                swlPanel.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                // Se indica al gridview que ya ha finalizado la carga.
                swlPanel.setRefreshing(false);
            }
        });
    }


    // Retorna si hay conexión a la red o no.
    private boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la información de red.
        ConnectivityManager gestorConectividad =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }

    // Muestra un toast con duración larga.
    private void mostrarToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onRefresh() {
        mEndlessScrollListener.reset(0, true);
        // Se establece la url inicial.
        mMaxTagId = null;
        // Si hay conexión a Internet se obtienen los datos.
        if (isConnectionAvailable()) {
            mAdaptador.removeAllItems();
            obtenerDatos();
        } else {
            mostrarToast(getString(R.string.no_hay_conexion_a_internet));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mEndlessScrollListener.reset(0, true);
    }

}
