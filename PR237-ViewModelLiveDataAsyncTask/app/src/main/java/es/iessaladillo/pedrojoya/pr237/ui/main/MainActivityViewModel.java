package es.iessaladillo.pedrojoya.pr237.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private MainActivityLiveData lastTask;
    private final LiveData<Integer> task;
    private final MutableLiveData<Integer> numSteps;

    public MainActivityViewModel() {
        numSteps = new MutableLiveData<>();
        task = Transformations.switchMap(numSteps, steps -> {
            lastTask = new MainActivityLiveData(steps);
            return lastTask;
        });
    }

    @SuppressWarnings("SameParameterValue")
    public void startWorking(int steps) {
        numSteps.setValue(steps);
    }

    public void cancelWorking() {
        if (lastTask != null) {
            lastTask.cancel();
        }
    }

    public boolean isWorking() {
        return lastTask != null && lastTask.isWorking();
    }

    public LiveData<Integer> getTask() {
        return task;
    }

    @Override
    protected void onCleared() {
        cancelWorking();
        super.onCleared();
    }
}
