package es.iessaladillo.pedrojoya.pr131.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import es.iessaladillo.pedrojoya.pr131.R;

public class MainActivity extends AppCompatActivity {

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircle;
    private Button btnStart;
    private Button btnCancel;
    private SecundaryTask secundaryTask;

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
                start();
            }
        });
        btnCancel = ActivityCompat.requireViewById(this, R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        updateViews(0);
    }

    private void start() {
        secundaryTask = new SecundaryTask(this);
        secundaryTask.execute(getResources().getInteger(R.integer.activity_main_steps));
    }

    private void cancel() {
        secundaryTask.cancel(true);
    }

    @Override
    protected void onDestroy() {
        if (secundaryTask != null) {
            // PRUEBA A COMENTAR ESTA LÍNEA. LA TAREA SE SEGUIRÁ EJECUTANDO PERO NO SERÁN LOS
            // CAMBIOS REFLEJADOS EN LA INTERFAZ USUARIO PORQUE SE ESTÁ REFERENCIANDO A UNA
            // INSTANCIA ANTIGUA DE LA ACTIVIDAD.
            secundaryTask.cancel(true);
        }
        super.onDestroy();
    }

    private void updateViews(int step) {
        Log.d("Mia", "Paso " + step);
        boolean working = step > 0 && step < getResources().getInteger(
                R.integer.activity_main_steps) && secundaryTask != null
                && secundaryTask.getStatus() == AsyncTask.Status.RUNNING;
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

    public static class SecundaryTask extends AsyncTask<Integer, Integer, Integer> {

        private final WeakReference<MainActivity> mainActivityWeakReference;
        private Integer maxSteps;

        public SecundaryTask(MainActivity mainActivity) {
            this.mainActivityWeakReference = new WeakReference<>(mainActivity);
        }

        @Override
        protected void onPreExecute() {
            if (mainActivityWeakReference.get() != null) {
                mainActivityWeakReference.get().updateViews(0);
            }
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            maxSteps = params[0];
            for (int i = 0; i < maxSteps && !isCancelled(); i++) {
                doWork();
                publishProgress(i + 1);
            }
            return maxSteps;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mainActivityWeakReference.get() != null) {
                mainActivityWeakReference.get().updateViews(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            Log.d("Mia", "Task cancelled");
            if (mainActivityWeakReference.get() != null) {
                mainActivityWeakReference.get().updateViews(0);
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (mainActivityWeakReference.get() != null) {
                mainActivityWeakReference.get().updateViews(maxSteps);
            }
        }

        private void doWork() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
