package es.iessaladillo.pedrojoya.pr237.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private MainFragmentLiveData lastTask;
    private final LiveData<Integer> task;
    private final MutableLiveData<Integer> numSteps;

    public MainFragmentViewModel() {
        numSteps = new MutableLiveData<>();
        task = Transformations.switchMap(numSteps, steps -> {
            lastTask = new MainFragmentLiveData(steps);
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
