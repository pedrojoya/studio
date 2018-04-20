package es.iessaladillo.pedrojoya.pr131.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class SecundaryTaskFragment extends Fragment {

    private static final String ARG_MAX_STEPS = "ARG_MAX_STEPS";

    private SecundaryTask secundaryTask;
    private int maxSteps;

    @SuppressWarnings("UnusedParameters")
    public interface Callbacks {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onPostExecute(int result);

        void onCancelled();
    }

    private Callbacks listener;

    public static SecundaryTaskFragment newInstance(int maxSteps) {
        SecundaryTaskFragment frg = new SecundaryTaskFragment();
        Bundle argumentos = new Bundle();
        argumentos.putInt(ARG_MAX_STEPS, maxSteps);
        frg.setArguments(argumentos);
        return frg;
    }

    public SecundaryTaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            maxSteps = getArguments().getInt(ARG_MAX_STEPS);
        }
        secundaryTask = new SecundaryTask();
        secundaryTask.execute(maxSteps);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (activity instanceof Callbacks) {
            listener = (Callbacks) activity;
        } else {
            throw new RuntimeException(
                    activity.toString() + " must implement SecundaryTaskFragment.Callbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void cancel() {
        secundaryTask.cancel(true);
    }

    public boolean isRunning() {
        return secundaryTask.getStatus() == AsyncTask.Status.RUNNING;
    }

    // Clase interna para la Tarea Secundaria. Tipos recibidos:
    // - Entrada: El tipo del valor recibido por doInBackground y que se pasa en
    // el método execute().
    // - Progreso: El tipo del valor recibido por onProgressUpdate y que se pasa
    // al hacer publishProgress().
    // - Salida: El tipo del valor recibido por onPostExecute y que corresponde
    // al valor de retorno del doInBackground.
    @SuppressLint("StaticFieldLeak")
    public class SecundaryTask extends AsyncTask<Integer, Integer, Integer> {

        // Llamado antes de lanzar el hilo secundario. Se ejecuta en el hilo
        // principal.
        @Override
        protected void onPreExecute() {
            if (listener != null) {
                listener.onPreExecute();
            }
        }

        // Llamado al lanzar el hilo secundario y corresponde al código que éste
        // ejecuta. Recibe lo que se le pase al método execute() cuando se
        // ejecute la tarea asíncrona. Se puede informar del progreso mediante
        // el método publishProgress().
        @Override
        protected Integer doInBackground(Integer... params) {
            int maxSteps = params[0];
            for (int i = 0; i < maxSteps && !isCancelled(); i++) {
                doWork();
                publishProgress(i + 1);
            }
            return maxSteps;
        }

        // Cada vez que se llama a publishProgress()
        @Override
        protected void onProgressUpdate(Integer... values) {
            if (listener != null) {
                listener.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if (listener != null) {
                listener.onCancelled();
            }
            listener = null;
        }

        // Es lanzado automáticamente cuando se termina de ejecutar
        // doInBackground. Recibe lo que haya retornado éste. Se ejecuta en el
        // hilo principal.
        @Override
        protected void onPostExecute(Integer result) {
            if (listener != null) {
                listener.onPostExecute(result);
            }
        }

        // Simula un trabajo de 1 segundo.
        private void doWork() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}