package es.iessaladillo.pedrojoya.pr096;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class ResultadoActivity extends ActionBarActivity {

    // Constantes.
    public static final String EXTRA_NOTIFICATION_CODE = "nc";
    public static final String ACTION_VIEW = "es.iessaladillo.pedrojoya.pr096.action.VIEW";
    public static final String ACTION_SEND = "es.iessaladillo.pedrojoya.pr096.action.SEND";
    public static final String ACTION_DELETE = "es.iessaladillo.pedrojoya.pr096.action.DELETE";

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        TextView lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        // Se obtiene el gestor de notificaciones por si hay que cancelar alguna.
        NotificationManager gestor =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Se el intent con el que ha sido llamado.
        Intent i = getIntent();
        if (i != null) {
            // Dependiendo de la acción especificada.
            String accion = i.getAction();
            if (ACTION_SEND.equals(accion)) {
                lblMensaje.setText(getString(R.string.enviar));
                if (i.hasExtra(EXTRA_NOTIFICATION_CODE)) {
                    int nc = i.getIntExtra(EXTRA_NOTIFICATION_CODE, 0);
                    gestor.cancel(nc);
                }
            }
            else if (ACTION_DELETE.equals(accion)) {
                lblMensaje.setText(getString(R.string.eliminar));
                if (i.hasExtra(EXTRA_NOTIFICATION_CODE)) {
                    int nc = i.getIntExtra(EXTRA_NOTIFICATION_CODE, 0);
                    gestor.cancel(nc);
                }
            }
            else {
                lblMensaje.setText(getString(R.string.ver));
            }
        }
    }

}
