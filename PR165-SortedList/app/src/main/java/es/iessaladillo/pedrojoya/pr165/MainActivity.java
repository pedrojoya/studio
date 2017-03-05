package es.iessaladillo.pedrojoya.pr165;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ProductosAdapter.OnItemClickListener, View.OnClickListener,
        ProductoDialogFragment.ProductoDialogListener,
        ProductosAdapter.OnItemLongClickListener {

    private static final String STATE_DATOS = "state_datos";
    private static final String STATE_LISTA = "state_lista";

    private ProductosAdapter mAdaptador;
    private ArrayList<Producto> mDatos;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mEstadoLista;
    private RecyclerView lstTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se recupera el estado anterior de la lista o se obtiene los datos
        // iniciales.
        if (savedInstanceState == null) {
            mDatos = getDatosIniciales();
        } else {
            mDatos = savedInstanceState.getParcelableArrayList(STATE_DATOS);
            mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
        }
        setupToolbar();
        setupRecyclerView();
        setupFAB();
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void setupRecyclerView() {
        mAdaptador = new ProductosAdapter(mDatos);
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        lstTareas = (RecyclerView) findViewById(R.id.lstTareas);
        if (lstTareas != null) {
            lstTareas.setHasFixedSize(true);
            lstTareas.setAdapter(mAdaptador);
            lstTareas.setLayoutManager(mLayoutManager);
            lstTareas.addItemDecoration(
                    new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            lstTareas.setItemAnimator(new DefaultItemAnimator());
        }
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mEstadoLista = mLayoutManager.onSaveInstanceState();
        // Se almacena el estado de la lista y sus datos.
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putParcelableArrayList(STATE_DATOS, mAdaptador.getData());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            mLayoutManager.onRestoreInstanceState(mEstadoLista);
        }
    }

    // Retorna los datos iniciales para la lista.
    private ArrayList<Producto> getDatosIniciales() {
        ArrayList<Producto> datos = new ArrayList<>();
        datos.add(new Producto("Macarrones integrales", 1, "kg"));
        datos.add(new Producto("Atún en aceite vegetal", 6, "latas"));
        datos.add(new Producto("Aceite Carbonell", 2, "litros"));
        return datos;
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Producto producto, int position) {
        // Se conmuta su estado de compra.
        mAdaptador.toggleComprado(position);
    }

    // Cuando se hace click largo sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Producto producto, int position) {
        // Se elimina el producto de la lista.
        if (mAdaptador.removeItem(producto)) {
            Snackbar.make(lstTareas,
                    getString(R.string.se_ha_eliminado, producto.getNombre()),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    // Cuando se hace click sobre el FAB.
    @Override
    public void onClick(View view) {
        // Se muestra el fragmento de diálogo para agregar un producto.
        (new ProductoDialogFragment()).show(getSupportFragmentManager(),
                getString(R.string.agregar_producto));
    }

    // Cuando se pulsa Aceptar en el fragmento de diálogo para agregar un producto.
    @Override
    public void onAgregarClick(Producto producto) {
        mAdaptador.addItem(producto);
        Snackbar.make(lstTareas,
                getString(R.string.se_ha_agregado, producto.getNombre()),
                Snackbar.LENGTH_SHORT).show();
    }

    // Cuando se pulsa Cancelar en el fragmento de diálogo para agregar un producto.
    @Override
    public void onCancelarClick() {

    }

}
