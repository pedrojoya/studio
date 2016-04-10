package es.iessaladillo.pedrojoya.pr174;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_INTERVAL = 5000;
    private static final int TRABAJO_ID = 1;

    @Bind(R.id.txtMensaje)
    EditText txtMensaje;
    @Bind(R.id.txtIntervalo)
    EditText txtIntervalo;
    @Bind(R.id.swActivar)
    SwitchCompat swActivar;

    private JobScheduler mPlanificador;
    private SharedPrefHelper mPrefs;
    private int mTrabajoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Se obtiene el planificador de trabajos.
        mPlanificador = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // Se establecen los valores a partir de las preferencias.
        mPrefs = new SharedPrefHelper(getResources(),
                PreferenceManager.getDefaultSharedPreferences(this));
        txtMensaje.setText(mPrefs.getString(R.string.pref_mensaje, getString(R.string.quillo_ponte_ya_a_currar)));
        txtIntervalo.setText(String.valueOf(
                mPrefs.getInt(R.string.pref_intervalo, DEFAULT_INTERVAL)));
        swActivar.setChecked(mPrefs.getBoolean(R.string.pref_activo, false));
    }

    @OnCheckedChanged(R.id.swActivar)
    public void swActivarOnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Dependiendo del estado en el que ha quedado
        if (isChecked) {
            // Se planifica el trabajo con los datos introducidos por el usuario.
            String mensaje = TextUtils.isEmpty(txtMensaje.getText().toString()) ?
                    getString(R.string.quillo_ponte_ya_a_currar)
                    : txtMensaje.getText().toString();
            int intervalo;
            try {
                intervalo = Integer.parseInt(txtIntervalo.getText().toString());
            } catch (NumberFormatException e) {
                intervalo = DEFAULT_INTERVAL;
            }
            planificarTrabajo(mensaje, intervalo);
        } else {
            // Se desactiva la planificación del trabajo..
            cancelarPlanificacionTrabajo();

        }
    }

    // Planifica un trabajo periódico persistente con el mensaje
    // y el intervalo recibidos.
    private void planificarTrabajo(String mensaje, int intervalo) {
        // Se obtiene el constructor de trabajos aportándole el ID que queremos
        // para el trabajo y el nombre de la clase correspondiente al servicio
        // que será llamado cuando se "dispare" el trabajo.
        JobInfo.Builder builder = new JobInfo.Builder(TRABAJO_ID,
                new ComponentName(getPackageName(),
                        PlanificadorService.class.getName()));
        // Se especifican las condiciones de disparo.
        builder.setPeriodic(intervalo);
        builder.setPersisted(true);
        PersistableBundle extras = new PersistableBundle();
        extras.putString(PlanificadorService.KEY_MENSAJE, mensaje);
        builder.setExtras(extras);
        // Se planifica el trabajo.
        mTrabajoId = mPlanificador.schedule(builder.build());
        if (mTrabajoId > 0) {
            Toast.makeText(this,
                    "Trabajo planificado: " + mTrabajoId,
                    Toast.LENGTH_SHORT).show();
            // Se guardan las preferencia.
            mPrefs.applyString(R.string.pref_mensaje, mensaje);
            mPrefs.applyInt(R.string.pref_intervalo, intervalo);
            mPrefs.applyBoolean(R.string.pref_activo, true);
        }
        else {
            // Se ha producido un error en la planificación.
            Toast.makeText(this,
                    "Error en la planificación del trabajo",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Cancela la planificación del trabajo.
    private void cancelarPlanificacionTrabajo() {
        // Se cancela.
        mPlanificador.cancel(mTrabajoId);
        // Se guarda la preferencia.
        mPrefs.applyBoolean(R.string.pref_activo, false);
    }

}
