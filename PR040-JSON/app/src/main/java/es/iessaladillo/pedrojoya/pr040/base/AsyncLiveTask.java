package es.iessaladillo.pedrojoya.pr040.base;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AsyncLiveTask<T> extends LiveData<T> {

    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            doAsync();
            return null;
        }

        @Override
        protected void onCancelled() {
            doOnCancelled();
        }
    };

    protected AsyncLiveTask() {
        asyncTask.execute();
    }

    @WorkerThread
    protected abstract void doAsync();

    @SuppressWarnings("EmptyMethod")
    @MainThread
    protected abstract void doOnCancelled();

    public void cancel(boolean mayInterruptIfRunning) {
        asyncTask.cancel(mayInterruptIfRunning);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isCancelled() {
        return asyncTask.isCancelled();
    }

    public boolean isWorking() {
        return asyncTask.getStatus() == AsyncTask.Status.RUNNING;
    }

}
