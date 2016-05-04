package es.iessaladillo.pedrojoya.pr115;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.BindView;


public class MainActivity extends AppCompatActivity implements EndlessListView.LoadAgent,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LIMIT = 10;
    private static final int DATOS_LOADER = 0;
    private static final String ARG_LIMIT = "limit";
    private static final String ARG_OFFSET = "offset";

    @BindView(R.id.lstLlamadas)
    EndlessListView mLstLlamadas;

    private int mOffset;
    private Adaptador mAdaptador;
    private LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVistas();
        mLoaderManager = getSupportLoaderManager();
        mLoaderManager.initLoader(DATOS_LOADER, null, this);
        mOffset = 0;
        loadData();
    }

    private void initVistas() {
        String[] from = {CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        int[] to = {R.id.lblNumero, R.id.lblNombre, R.id.imgTipoLlamada, R.id.lblFecha, R.id.lblDuracion};
        mAdaptador = new Adaptador(this, R.layout.activity_main_item, null, from, to, 0);
        mLstLlamadas.setAdapter(mAdaptador);
        mLstLlamadas.setLoadAgent(this);
    }

    // Carga más datos.
    @Override
    public void loadData() {
        Bundle args = new Bundle();
        args.putInt(ARG_LIMIT, mOffset + LIMIT);
        mLoaderManager.restartLoader(0, args, this);
        mOffset += LIMIT;
    }

    // Cuando se debe crear el cargador. Retorna el cargador.
    // Recibe el id del cargador y sus argumentos.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Se obtienen los argumentos.
        int limit = LIMIT;
        if (args != null) {
            limit = args.getInt(ARG_LIMIT);
        }

        // Se crea el cargador, que realiza una consulta al content provider.
        // Recibe: contexto, uri del content provider, array de nombres de campos
        // a seleccionar, condición where, parámetros del where y orden.
        String[] campos = {CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        String orden = CallLog.Calls.DATE + " DESC LIMIT " + 0 + "," + limit;
        return new CursorLoader(this, CallLog.Calls.CONTENT_URI, campos, null, null, orden);
    }

    // Cuando terminan de cargarse los datos en el cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Se cambia el cursor del adaptador por el que tiene los datos cargados.
        mAdaptador.changeCursor(data);
        mLstLlamadas.setLoaded();
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se vacía de datos el adaptador asignándole un cursor nulo.
        mAdaptador.changeCursor(null);
    }
}