package es.iessaladillo.pedrojoya.pr129.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr129.data.ClockLiveData;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final ClockLiveData clock = new ClockLiveData();
    private final MutableLiveData<Boolean> running = new MutableLiveData<>();

    public MainFragmentViewModel() {
        running.setValue(false);
    }

    public ClockLiveData getClock() {
        return clock;
    }

    public LiveData<Boolean> getRunning() {
        return running;
    }

    void startOrStop() {
        if (running.getValue() != null && running.getValue()) {
            running.setValue(false);
            clock.stop();
        } else {
            running.setValue(true);
            clock.start();
        }
    }

    @Override
    protected void onCleared() {
        clock.stop();
        super.onCleared();
    }

}
