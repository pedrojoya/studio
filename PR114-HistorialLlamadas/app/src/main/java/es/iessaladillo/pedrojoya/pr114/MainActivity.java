package es.iessaladillo.pedrojoya.pr114;

import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    private static final int LIMIT = 20;

    @InjectView(R.id.lstLlamadas)
    ListView mLstLlamadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se carga la lista de últimas llamadas.
        cargarLista();
    }

    // Carga de datos la lista.
    private void cargarLista() {
        int offset = 0;
        String[] campos = {CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        String orden = CallLog.Calls.DATE + " DESC LIMIT " + offset + "," + LIMIT;
        CursorLoader cargador = new CursorLoader(this, CallLog.Calls.CONTENT_URI, campos, null, null, orden);
        Cursor cursor = cargador.loadInBackground();
        String[] from = {CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        int[] to = {R.id.lblNumero, R.id.lblNombre, R.id.imgTipoLlamada, R.id.lblFecha, R.id.lblDuracion};
        Adaptador mAdaptador = new Adaptador(this, R.layout.activity_main_item, cursor, from, to, 0);
        mLstLlamadas.setAdapter(mAdaptador);
    }

}