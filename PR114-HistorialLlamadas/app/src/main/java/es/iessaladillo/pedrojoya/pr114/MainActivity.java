package es.iessaladillo.pedrojoya.pr114;

import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.lstLlamadas)
    ListView mLstLlamadas;

    private Adaptador mAdaptador;

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    // Carga de datos la lista.
    private void cargarLista() {
        String[] campos = {CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        String orden = CallLog.Calls.DATE + " DESC LIMIT 0,10";
        CursorLoader cargador = new CursorLoader(this, CallLog.Calls.CONTENT_URI, campos, null, null, orden);
        Cursor cursor = cargador.loadInBackground();
        String[] from = {CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        int[] to = {R.id.lblNumero, R.id.lblNombre, R.id.lblTipoLlamada, R.id.lblFecha, R.id.lblDuracion};
        mAdaptador = new Adaptador(this, R.layout.activity_main_item, cursor, from, to, 0);
        mLstLlamadas.setAdapter(mAdaptador);
    }

}