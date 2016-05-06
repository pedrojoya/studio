package es.iessaladillo.pedrojoya.pr131;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        TareaSecundariaFragment.Callbacks {

    private static final String TAG_TAREA = "tag_tarea";

    private ProgressBar prbBarra;
    private TextView lblMensaje;
    private ProgressBar prbCirculo;
    private Button btnIniciar;
    private Button btnCancelar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        if (savedInstanceState != null) {
            restaurarControles();
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        prbBarra = (ProgressBar) findViewById(R.id.prbBarra);
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        prbCirculo = (ProgressBar) findViewById(R.id.prbCirculo);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar();
            }
        });
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
        actualizarVistas(false);
    }

    private void restaurarControles() {
        // Si el fragmento existe y la tarea está aún ejecutándose.
        TareaSecundariaFragment frg = (TareaSecundariaFragment) getSupportFragmentManager().findFragmentByTag(TAG_TAREA);
        if(frg != null) {
            if (frg.getTareaSecundaria().getStatus() == AsyncTask.Status.RUNNING) {
                prbBarra.setVisibility(View.VISIBLE);
                lblMensaje.setVisibility(View.VISIBLE);
                prbCirculo.setVisibility(View.VISIBLE);
                btnIniciar.setEnabled(false);
                btnCancelar.setEnabled(true);
            }
        }
    }

    // Cuando se hace click en btnIniciar.
    private void iniciar() {
        // Se crea el fragmento que ejecuta la tarea secundaria y se añade
        // al gestor de fragmentos.
        TareaSecundariaFragment frg = TareaSecundariaFragment.newInstance(
                getResources().getInteger(R.integer.numPasos));
        getSupportFragmentManager().beginTransaction().add(frg, TAG_TAREA).commit();
    }

    // Cuando se hace click en btnCancelar.
    private void cancelar() {
        TareaSecundariaFragment frg = (TareaSecundariaFragment) getSupportFragmentManager().findFragmentByTag(TAG_TAREA);
        if (frg != null) {
            if (frg.getTareaSecundaria().getStatus() == AsyncTask.Status.RUNNING) {
                frg.getTareaSecundaria().cancel(true);
            }
        }
    }

    // Actualiza el estado de las vistas. Recibe si la tarea está iniciada o no.
    private void actualizarVistas(boolean iniciada) {
        if (!iniciada) {
            prbBarra.setProgress(0);
            lblMensaje.setText("");
        }
        btnIniciar.setEnabled(!iniciada);
        btnCancelar.setEnabled(iniciada);
        prbBarra.setVisibility(iniciada?View.VISIBLE:View.INVISIBLE);
        lblMensaje.setVisibility(iniciada?View.VISIBLE:View.INVISIBLE);
        prbCirculo.setVisibility(iniciada?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public void onPreExecute() {
        // Se hacen visibles las vistas para el progreso.
        actualizarVistas(true);
    }

    @Override
    public void onProgressUpdate(int progress) {
        // Se actualiza la barra.
        lblMensaje.setText(getString(R.string.trabajando, progress,
                getResources().getInteger(R.integer.numPasos)));
        prbBarra.setProgress(progress);
    }

    @Override
    public void onPostExecute(int result) {
        // Se resetean las vistas.
        actualizarVistas(false);
    }

    @Override
    public void onCancelled() {
        actualizarVistas(false);
        Toast.makeText(this, R.string.tarea_cancelada, Toast.LENGTH_SHORT).show();
    }

}
