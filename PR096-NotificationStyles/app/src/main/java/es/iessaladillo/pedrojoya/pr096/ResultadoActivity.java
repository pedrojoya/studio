package es.iessaladillo.pedrojoya.pr096;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ResultadoActivity extends Activity {

    private static final String EXTRA_MENSAJE = "mensaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        TextView lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        Intent i = getIntent();
        if (i != null && i.hasExtra(EXTRA_MENSAJE)) {
            lblMensaje.setText(i.getStringExtra(EXTRA_MENSAJE));
        }
    }

    public static Intent getIntent(Context contexto, String mensaje) {
        Intent i = new Intent(contexto, ResultadoActivity.class);
        i.putExtra(EXTRA_MENSAJE, mensaje);
        return i;
    }


}
