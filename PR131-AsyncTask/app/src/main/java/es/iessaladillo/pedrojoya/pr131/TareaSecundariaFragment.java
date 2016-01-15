package es.iessaladillo.pedrojoya.pr131;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class TareaSecundariaFragment extends Fragment {

    private static final String ARG_NUMPASOS = "numpasos";

    private TareaSecundaria mTareaSecundaria;

    // Definimos una interfaz para que la actividad sea informada cuando se
    // produzca algún evento en la tarea secundaria.
    public interface Callbacks {

        void onPreExecute();
        void onProgressUpdate(int progress);
        void onPostExecute(int result);
        void onCancelled();
    }
    private Callbacks mListener;

    public static TareaSecundariaFragment newInstance(int numPasos) {
        TareaSecundariaFragment frg = new TareaSecundariaFragment();
        Bundle argumentos = new Bundle();
        argumentos.putInt(ARG_NUMPASOS, numPasos);
        frg.setArguments(argumentos);
        return frg;
    }

    public TareaSecundariaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // El fragmento no será destruido si se cambia la orientación.
        setRetainInstance(true);
        mTareaSecundaria = new TareaSecundaria();
        mTareaSecundaria.execute(getArguments().getInt(ARG_NUMPASOS));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Callbacks) {
            mListener = (Callbacks) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " debe implementar TareaSecundariaFragment.Callbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Al ponerlo a null se evitan memory leaks de la actividad.
        mListener = null;
    }

    public TareaSecundaria getTareaSecundaria() {
        return mTareaSecundaria;
    }

    // Clase interna para la Tarea Secundaria. Tipos recibidos:
    // - Entrada: El tipo del valor recibido por doInBackground y que se pasa en
    // el método execute().
    // - Progreso: El tipo del valor recibido por onProgressUpdate y que se pasa
    // al hacer publishProgress().
    // - Salida: El tipo del valor recibido por onPostExecute y que corresponde
    // al valor de retorno del doInBackground.
    public class TareaSecundaria extends AsyncTask<Integer, Integer, Integer> {

        // Llamado antes de lanzar el hilo secundario. Se ejecuta en el hilo
        // principal.
        @Override
        protected void onPreExecute() {
            if (mListener != null) {
                // Se informa al listener.
                mListener.onPreExecute();
            }
        }

        // Llamado al lanzar el hilo secundario y corresponde al código que éste
        // ejecuta. Recibe lo que se le pase al método execute() cuando se
        // ejecute la tarea asíncrona. Se puede informar del progreso mediante
        // el método publishProgress().
        @Override
        protected Integer doInBackground(Integer... params) {
            int numTrabajos = params[0];
            // Se realizan los pasos.
            for (int i = 0; i < numTrabajos && !isCancelled(); i++) {
                // Se pone a trabajar.
                trabajar();
                // Informa del progreso.
                publishProgress(i + 1);
            }
            // Se retorna el número de trabajos realizados.
            return numTrabajos;
        }

        // Cada vez que se llama a publishProgress()
        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mListener != null) {
                // Se informa al listener.
                mListener.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if (mListener != null) {
                // Se informa al listener.
                mListener.onCancelled();
            }
            // Se libera el mListener.
            mListener = null;
        }

        // Es lanzado automáticamente cuando se termina de ejecutar
        // doInBackground. Recibe lo que haya retornado éste. Se ejecuta en el
        // hilo principal.
        @Override
        protected void onPostExecute(Integer result) {
            if (mListener != null) {
                mListener.onPostExecute(result);
            }
        }

        // Simula un trabajo de 1 segundo.
        private void trabajar() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}