package es.iessaladillo.pedrojoya.pr134;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView lblNivel;
    private ProgressBar pbNivel;

    private BatteryReceiver mReceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se crea el receptor.
        mReceptor = new BatteryReceiver();
    }

    private void initVistas() {
        lblNivel = (TextView) findViewById(R.id.lblNivel);
        pbNivel = (ProgressBar) findViewById(R.id.pbNivel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se registra el receptor con el filtro para la acción adecuado.
        IntentFilter filtro = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceptor, filtro);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Se quita del registro el receptor.
        unregisterReceiver(mReceptor);
    }

    private class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Se obtienen los datos relevantes.
            int estado = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean estaCargando =
                    estado == BatteryManager.BATTERY_STATUS_CHARGING ||
                            estado == BatteryManager.BATTERY_STATUS_FULL;
            int enchufado = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean cargaPorUsb = enchufado == BatteryManager.BATTERY_PLUGGED_USB;
            boolean cargaPorEnchufe = enchufado == BatteryManager.BATTERY_PLUGGED_AC;
            int nivel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int escala = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float porcentaje = nivel / (float)escala;
            // Se actualizan los datos en la actividad.
            StringBuilder sb = new StringBuilder();
            if (estaCargando) {
                sb.append(getString(R.string.cargando)).append(" ");
                if (cargaPorUsb) {
                    sb.append(getString(R.string.por_usb)).append(" ");
                }
                else if (cargaPorEnchufe) {
                    sb.append(getString(R.string.conectado)).append(" ");
                }
            }
            else {
                sb.append(getString(R.string.nivel_bateria)).append(" ");
            }
            sb.append("(").append(nivel).append("%)");
            lblNivel.setText(sb.toString());
            pbNivel.setProgress(nivel);
        }

    }

}
