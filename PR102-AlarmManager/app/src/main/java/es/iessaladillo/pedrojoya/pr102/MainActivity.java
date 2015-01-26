package es.iessaladillo.pedrojoya.pr102;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements OnCheckedChangeListener {

    // Vistas.
    private TextView txtMensaje;
    private TextView txtIntervalo;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtiene e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        txtMensaje = (TextView) findViewById(R.id.txtMensaje);
        txtIntervalo = (TextView) findViewById(R.id.txtIntervalo);
        Switch swActivar = (Switch) findViewById(R.id.swActivar);
        // Se inicializan las vistas en base a los valores de las preferencias.
        SharedPreferences preferencias = getApplicationContext()
                .getSharedPreferences("alarmas", Context.MODE_PRIVATE);
        txtMensaje.setText(preferencias.getString(AvisarReceiver.PREF_MENSAJE,
                getString(R.string.quillo_ponte_ya_a_currar)));
        txtIntervalo.setText(preferencias.getInt(AvisarReceiver.PREF_INTERVALO,
                AvisarReceiver.DEFAULT_INTERVAL) + "");
        swActivar
                .setChecked(AvisarReceiver.isAlarmaOn(getApplicationContext()));
        swActivar.setOnCheckedChangeListener(this);
    }

    // Cuando cambia el estado de la vista interruptor.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Dependiendo del estado en el que ha quedado
        if (isChecked) {
            // Se programa la alarma con los datos introducidos por el usuario.
            String mensaje = TextUtils.isEmpty(txtMensaje.getText().toString()) ? getString(R.string.quillo_ponte_ya_a_currar)
                    : txtMensaje.getText().toString();
            int intervalo;
            try {
                intervalo = Integer.parseInt(txtIntervalo.getText().toString());
            } catch (NumberFormatException e) {
                intervalo = AvisarReceiver.DEFAULT_INTERVAL;
            }
            AvisarReceiver.programarAlarma(getApplicationContext(), mensaje,
                    intervalo);
        } else {
            // Se desactiva la alarma.
            AvisarReceiver.cancelarAlarma(getApplicationContext());
        }

    }

}