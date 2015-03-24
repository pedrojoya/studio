package es.iessaladillo.pedrojoya.pr116;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

// El problema de esta implementación es que si se produce una nueva llamada, el loader no
// es notificado, ya que está subscripto sólo a los últimos registros obtenidos, y la nueva
// llamada sería la primera.
public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LIMIT = 10;
    private static final int DATOS_LOADER = 0;
    private static final String ARG_LIMIT = "limit";
    private static final String ARG_OFFSET = "offset";
    private static final String ARG_MAS = "mas";

    @InjectView(R.id.lstLlamadas)
    ListView mLstLlamadas;

    private int mOffset;
    private Adaptador mAdaptador;
    private LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initVistas();
        mLoaderManager = getSupportLoaderManager();
        mLoaderManager.initLoader(DATOS_LOADER, null, this);
        mOffset = 0;
        loadData(0);
    }

    private void initVistas() {
        mLstLlamadas.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadData(page);
            }
        });
        mAdaptador = new Adaptador(this, new ArrayList<Llamada>());
        mLstLlamadas.setAdapter(mAdaptador);
    }

    // Carga más datos.
    public void loadData(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_OFFSET, page);
        mLoaderManager.restartLoader(0, args, this);
    }

    // Cuando se debe crear el cargador. Retorna el cargador.
    // Recibe el id del cargador y sus argumentos.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Se obtienen los argumentos.
        int offset = 0;
        if (args != null) {
            offset = args.getInt(ARG_OFFSET);
        }
        // Se crea el cargador, que realiza una consulta al content provider.
        // Recibe: contexto, uri del content provider, array de nombres de campos
        // a seleccionar, condición where, parámetros del where y orden.
        String[] campos = {CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        String orden = CallLog.Calls.DATE + " DESC LIMIT " + offset + "," + LIMIT;
        return new CursorLoader(this, CallLog.Calls.CONTENT_URI, campos, null, null, orden);
    }

    // Cuando terminan de cargarse los datos en el cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Se añaden los datos obtenidos al adaptador.
        mAdaptador.addAll(Llamada.CursorToArrayList(data));
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdaptador.clear();
    }
}