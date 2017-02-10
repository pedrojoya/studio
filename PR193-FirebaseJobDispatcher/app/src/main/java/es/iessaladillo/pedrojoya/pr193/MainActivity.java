package es.iessaladillo.pedrojoya.pr193;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txtMensaje)
    EditText txtMensaje;
    @BindView(R.id.txtIntervalo)
    EditText txtIntervalo;
    @BindView(R.id.swActivar)
    SwitchCompat swActivar;

    private String mTrabajoTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Se establecen los valores a partir de las preferencias.
        SharedPrefHelper prefs = new SharedPrefHelper(getResources(),
                PreferenceManager.getDefaultSharedPreferences(this));
        txtMensaje.setText(prefs.getString(R.string.pref_mensaje,
                getString(R.string.quillo_ponte_ya_a_currar)));
        txtIntervalo.setText(String.valueOf(
                prefs.getInt(R.string.pref_intervalo, PlanificadorService.DEFAULT_INTERVAL)));
        swActivar.setChecked(prefs.getBoolean(R.string.pref_activo, false));
    }

    @SuppressWarnings("UnusedParameters")
    @OnCheckedChanged(R.id.swActivar)
    public void swActivarOnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Dependiendo del estado en el que ha quedado
        if (isChecked) {
            // Se planifica el trabajo con los datos introducidos por el usuario.
            String mensaje = TextUtils.isEmpty(txtMensaje.getText().toString()) ? getString(
                    R.string.quillo_ponte_ya_a_currar) : txtMensaje.getText().toString();
            int intervalo;
            try {
                intervalo = Integer.parseInt(txtIntervalo.getText().toString());
            } catch (NumberFormatException e) {
                intervalo = PlanificadorService.DEFAULT_INTERVAL;
            }
            mTrabajoTag = PlanificadorService.planificarTrabajo(this, mensaje, intervalo / 1000);
            if (!TextUtils.isEmpty(mTrabajoTag)) {
                Toast.makeText(this, getString(R.string.trabajo_planificado_id, mTrabajoTag),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Se ha producido un error en la planificación.
                Toast.makeText(this, R.string.error_planificacion, Toast.LENGTH_SHORT).show();
            }

        } else {
            // Se desactiva la planificación del trabajo.
            PlanificadorService.cancelarPlanificacionTrabajo(this, mTrabajoTag);
        }
    }

}
