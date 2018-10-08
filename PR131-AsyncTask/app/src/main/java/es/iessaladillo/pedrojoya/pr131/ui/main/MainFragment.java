package es.iessaladillo.pedrojoya.pr131.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr131.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircle;
    private Button btnStart;
    private Button btnCancel;
    private Task task;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
    }

    private void setupViews(View view) {
        prbBar = ViewCompat.requireViewById(view, R.id.prbBar);
        lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        prbCircle = ViewCompat.requireViewById(view, R.id.prbCircle);
        btnStart = ViewCompat.requireViewById(view, R.id.btnStart);
        btnCancel = ViewCompat.requireViewById(view, R.id.btnCancel);

        btnStart.setOnClickListener(v -> start());
        btnCancel.setOnClickListener(v -> cancel());
        updateViews(0);
    }

    private void start() {
        task = new Task(this);
        task.execute(getResources().getInteger(R.integer.activity_main_steps));
    }

    private void cancel() {
        task.cancel(true);
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            // PRUEBA A COMENTAR ESTA LÍNEA. LA TAREA SE SEGUIRÁ EJECUTANDO PERO NO SERÁN LOS
            // CAMBIOS REFLEJADOS EN LA INTERFAZ USUARIO PORQUE SE ESTÁ REFERENCIANDO A UNA
            // INSTANCIA ANTIGUA DE LA ACTIVIDAD.
            task.cancel(true);
        }
        super.onDestroy();
    }

    private void updateViews(int step) {
        boolean working = step > 0 && step < getResources().getInteger(
                R.integer.activity_main_steps) && task != null
                && task.getStatus() == AsyncTask.Status.RUNNING;
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

    public static class Task extends AsyncTask<Integer, Integer, Integer> {

        private final WeakReference<MainFragment> mainFragmentWeakReference;
        private Integer maxSteps;

        Task(MainFragment mainFragment) {
            mainFragmentWeakReference = new WeakReference<>(mainFragment);
        }

        @Override
        protected void onPreExecute() {
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null && mainFragment.isVisible()) {
                mainFragment.updateViews(0);
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
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null && mainFragment.isVisible()) {
                mainFragment.updateViews(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null && mainFragment.isVisible()) {
                mainFragment.updateViews(0);
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null && mainFragment.isVisible()) {
                mainFragment.updateViews(maxSteps);
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
