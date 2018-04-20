package es.iessaladillo.pedrojoya.pr131.ui.main;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr131.R;

public class MainActivity extends AppCompatActivity implements SecundaryTaskFragment.Callbacks {

    private static final String TAG_TAREA = "tag_tarea";

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircle;
    private Button btnStart;
    private Button btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        prbBar = ActivityCompat.requireViewById(this, R.id.prbBar);
        lblMessage = ActivityCompat.requireViewById(this, R.id.lblMessage);
        prbCircle = ActivityCompat.requireViewById(this, R.id.prbCircle);
        btnStart = ActivityCompat.requireViewById(this, R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar();
            }
        });
        btnCancel = ActivityCompat.requireViewById(this, R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
        updateViews(0);
    }

    // Cuando se hace click en btnStart.
    private void iniciar() {
        // Se crea el fragmento que ejecuta la tarea secundaria y se añade
        // al gestor de fragmentos.
        SecundaryTaskFragment frg = SecundaryTaskFragment.newInstance(
                getResources().getInteger(R.integer.activity_main_steps));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rlRoot, frg, TAG_TAREA)
                .commit();
    }

    // Cuando se hace click en btnCancel.
    private void cancelar() {
        SecundaryTaskFragment frg = (SecundaryTaskFragment) getSupportFragmentManager().findFragmentByTag(
                TAG_TAREA);
        if (frg != null) {
            frg.cancel();
/*
            if (frg.getTareaSecundaria().getStatus() == AsyncTask.Status.RUNNING) {
                frg.getTareaSecundaria().cancel(true);
            }
*/
        }
    }

    private void updateViews(int step) {
        SecundaryTaskFragment frg = (SecundaryTaskFragment) getSupportFragmentManager().findFragmentByTag(
                TAG_TAREA);
        boolean working = step < getResources().getInteger(R.integer.activity_main_steps)
                && frg != null && frg.isRunning();
        if (!working) {
            lblMessage.setText("");
        }
        prbBar.setProgress(step);
        lblMessage.setText(getString(R.string.activity_main_lblMessage, step,
                getResources().getInteger(R.integer.activity_main_steps)));
        btnStart.setEnabled(!working);
        btnCancel.setEnabled(working);
        prbBar.setVisibility(working ? View.VISIBLE : View.INVISIBLE);
        lblMessage.setVisibility(working ? View.VISIBLE : View.INVISIBLE);
        prbCircle.setVisibility(working ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onPreExecute() {
        // Se hacen visibles las vistas para el progreso.
        updateViews(0);
    }

    @Override
    public void onProgressUpdate(int step) {
        updateViews(step);
    }

    @Override
    public void onPostExecute(int result) {
        // Se resetean las vistas.
        updateViews(getResources().getInteger(R.integer.activity_main_steps));
    }

    @Override
    public void onCancelled() {
        updateViews(0);
    }

}
