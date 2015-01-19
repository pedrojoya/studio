package es.iessaladillo.pedrojoya.pr006;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    // Constantes.
    private static final String LOG_PROYECTO = "CICLO";
    private static final String STATE_LISTADO = "mListado";

    // Variables a nivel de clase.
    private String mListado = "";

    // Vistas.
    private TextView lblListado;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        lblListado = (TextView) this.findViewById(R.id.lblListado);
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.oncreate));
    }

    // Al destruir la actividad.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.ondestroy));
    }

    // Al pausar la actividad.
    @Override
    protected void onPause() {
        super.onPause();
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.onpause));
    }

    // Al volver a estar en primer plano la actividad.
    @Override
    protected void onResume() {
        super.onResume();
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.onresume));
    }

    // Al salvar la instancia por cambio de configuración.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se almacena el valor de mListado.
        outState.putString(STATE_LISTADO, mListado);
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.onsaveinstancestate));
    }

    // Al restaurar la instancia después de un cambio de configuración.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Se restaura el valor de mListado.
        mListado = savedInstanceState.getString(STATE_LISTADO);
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.onrestoreinstancestate));
    }

    // Al iniciar la actividad.
    @Override
    protected void onStart() {
        super.onStart();
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.onstart));
    }

    // Al parar la actividad.
    @Override
    protected void onStop() {
        super.onStop();
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.onstop));
    }

    // Al reiniciar la actividad.
    @Override
    protected void onRestart() {
        super.onRestart();
        // Se envía el mensaje de depuración.
        escribirLog(getString(R.string.onrestart));
    }

    // Se envía el mensaje de depuración.
    private void escribirLog(String metodo) {
        // Se muestra el log.
        Log.d(LOG_PROYECTO, metodo);
        // Se agrega al listado.
        mListado = mListado.concat(metodo + "\n");
        lblListado.setText(mListado);
    }

}