package es.iessaladillo.pedrojoya.pr096;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class ResultadoActivity extends Activity {

    private static final String EXTRA_MENSAJE = "mensaje";
    public static final String EXTRA_NOTIFICATION_CODE = "nc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        TextView lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(EXTRA_MENSAJE)) {
                lblMensaje.setText(i.getStringExtra(EXTRA_MENSAJE));
            }
            if (i.hasExtra(EXTRA_NOTIFICATION_CODE)) {
                int nc = i.getIntExtra(EXTRA_NOTIFICATION_CODE, 0);
                NotificationManager gestor =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                gestor.cancel(nc);
            }
        }
    }

    public static Intent createIntent(Context contexto, String mensaje) {
        Intent i = new Intent(contexto, ResultadoActivity.class);
        i.putExtra(EXTRA_MENSAJE, mensaje);
        return i;
    }

}
