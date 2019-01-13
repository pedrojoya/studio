package es.iessaladillo.pedrojoya.pr237.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr237.base.Event;
import es.iessaladillo.pedrojoya.pr237.base.Resource;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel2 extends ViewModel {

    private WorkingTask lastWorkingTask;
    private final LiveData<Resource<Event<String>>> workingTask;
    private final MutableLiveData<Integer> workingTaskTrigger;

    public MainFragmentViewModel2() {
        workingTaskTrigger = new MutableLiveData<>();
        workingTask = Transformations.switchMap(workingTaskTrigger, steps -> {
            lastWorkingTask = new WorkingTask(steps);
            return lastWorkingTask;
        });
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

}
