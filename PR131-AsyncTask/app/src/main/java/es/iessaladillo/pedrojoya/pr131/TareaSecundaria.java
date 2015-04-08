package es.iessaladillo.pedrojoya.pr131;

import android.os.AsyncTask;

// Clase interna para la Tarea Secundaria. Tipos recibidos:
// - Entrada: El tipo del valor recibido por doInBackground y que se pasa en
// el método execute().
// - Progreso: El tipo del valor recibido por onProgressUpdate y que se pasa
// al hacer publishProgress().
// - Salida: El tipo del valor recibido por onPostExecute y que corresponde
// al valor de retorno del doInBackground.
class TareaSecundaria extends AsyncTask<Integer, Integer, Integer> {

    // Definimos una interfaz para que la actividad sea informada cuando se
    // haya finalizado la tarea.
    public interface Callbacks {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onPostExecute(int result);
    }

    // Variables a nivel de clase.
    private Callbacks mListener;

    // Constructor.
    public TareaSecundaria(Callbacks listener) {
        mListener = listener;
    }

    // Llamado antes de lanzar el hilo secundario. Se ejecuta en el hilo
    // principal.
    @Override
    protected void onPreExecute() {
        mListener.onPreExecute();
    }

    // Llamado al lanzar el hilo secundario y corresponde al código que éste
    // ejecuta. Recibe lo que se le pase al método execute() cuando se
    // ejecute la tarea asíncrona. Se puede informar del progreso mediante
    // el método publishProgress().
    @Override
    protected Integer doInBackground(Integer... params) {
        int numTrabajos = params[0];
        // Se realizan los pasos.
        for (int i = 0; i < numTrabajos; i++) {
            // Se pone a trabajar.
            trabajar();
            // Si la tarea ha sido cancelada se sale del bucle.
            if (isCancelled()) {
                break;
            }
            // Informa del progreso.
            publishProgress(i + 1);
        }
        // Se retorna el número de trabajos realizados.
        return numTrabajos;
    }

    // Cada vez que se llama a publishProgress()
    @Override
    protected void onProgressUpdate(Integer... values) {
        mListener.onProgressUpdate(values[0]);
    }

    @Override
    protected void onCancelled() {
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
