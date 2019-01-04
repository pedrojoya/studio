package es.iessaladillo.pedrojoya.pr129.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr129.data.ClockLiveData;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final ClockLiveData clockLiveData = new ClockLiveData();
    private final MutableLiveData<Boolean> running = new MutableLiveData<>();

    public MainFragmentViewModel() {
        running.setValue(false);
    }

    public ClockLiveData getClockLiveData() {
        return clockLiveData;
    }

    public LiveData<Boolean> getRunning() {
        return running;
    }

    void startOrStop() {
        if (running.getValue() != null && running.getValue()) {
            running.setValue(false);
            clockLiveData.stop();
        } else {
            running.setValue(true);
            clockLiveData.start();
        }
    }

    @Override
    protected void onCleared() {
        clockLiveData.stop();
        super.onCleared();
    }

}
