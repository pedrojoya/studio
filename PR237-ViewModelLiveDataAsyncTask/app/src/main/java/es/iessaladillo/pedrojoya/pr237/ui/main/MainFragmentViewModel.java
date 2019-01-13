package es.iessaladillo.pedrojoya.pr237.ui.main;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr237.base.Event;
import es.iessaladillo.pedrojoya.pr237.base.Resource;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<Integer> workingTaskTrigger = new MutableLiveData<>();
    private final LiveData<Resource<Event<String>>> workingTask;
    private WorkTask lastWorkingTask = null;

    public MainFragmentViewModel() {
        workingTask = Transformations.switchMap(workingTaskTrigger, this::doWork);
    }

    private LiveData<Resource<Event<String>>> doWork(Integer steps) {
        MutableLiveData<Resource<Event<String>>> result = new MutableLiveData<>();
        lastWorkingTask = new WorkTask(steps, result);
        lastWorkingTask.execute();
        return result;
    }

    @SuppressWarnings("SameParameterValue")
    public void startWorking(int steps) {
        workingTaskTrigger.setValue(steps);
    }

    public void cancelWorking() {
        if (lastWorkingTask != null) {
            lastWorkingTask.cancel(true);
        }
    }

    public LiveData<Resource<Event<String>>> getWorkingTask() {
        return workingTask;
    }

    @Override
    protected void onCleared() {
        cancelWorking();
        super.onCleared();
    }

    private static class WorkTask extends AsyncTask<Void, Void, Void> {

        private final Integer steps;
        private final MutableLiveData<Resource<Event<String>>> result;

        public WorkTask(Integer steps, MutableLiveData<Resource<Event<String>>> result) {
            this.steps = steps;
            this.result = result;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            result.postValue(Resource.loading(0));
            for (int i = 0; i < steps && !isCancelled(); i++) {
                try {
                    work();
                } catch (InterruptedException e) {
                    return null;
                }
                result.postValue(Resource.loading(i + 1));
            }
            if (!isCancelled()) {
                result.postValue(Resource.success(new Event<>("Task completed successfully")));
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            result.postValue(Resource.error(new Exception("Task cancelled by user")));
        }

        private void work() throws InterruptedException {
            Thread.sleep(1000);
        }

    }

}
