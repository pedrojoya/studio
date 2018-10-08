package es.iessaladillo.pedrojoya.pr237.ui.main;

import androidx.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

class MainFragmentLiveData extends MutableLiveData<Integer> {

    private final SecundaryTask asyncTask;

    public MainFragmentLiveData(Integer steps) {
        asyncTask = new SecundaryTask(this);
        asyncTask.execute(steps);
    }

    public void cancel() {
        asyncTask.cancel(false);
    }

    public boolean isWorking() {
        return asyncTask.getStatus() == AsyncTask.Status.RUNNING;
    }

    static class SecundaryTask extends AsyncTask<Integer, Integer, Integer> {

        private final MutableLiveData<Integer> mutableLiveData;

        SecundaryTask(MutableLiveData<Integer> mutableLiveData) {
            this.mutableLiveData = mutableLiveData;
        }

        @Override
        protected void onPreExecute() {
            mutableLiveData.setValue(0);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int steps = params[0];
            for (int i = 0; i < steps && !isCancelled(); i++) {
                work();
                mutableLiveData.postValue(i + 1);
            }
            return steps;
        }

        @Override
        protected void onCancelled() {
            mutableLiveData.setValue(0);
            Log.d("Mia", "Tarea cancelada");
        }

        @Override
        protected void onPostExecute(Integer result) {
            mutableLiveData.setValue(result);
        }

        private void work() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
