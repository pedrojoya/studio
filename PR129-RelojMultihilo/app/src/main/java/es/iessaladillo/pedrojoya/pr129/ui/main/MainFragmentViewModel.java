package es.iessaladillo.pedrojoya.pr129.ui.main;

import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final ClockLiveData clockLiveData = new ClockLiveData();

    public ClockLiveData getClockLiveData() {
        return clockLiveData;
    }

    @Override
    protected void onCleared() {
        clockLiveData.stop();
        super.onCleared();
    }

    public void start() {
        clockLiveData.start();
    }

    public void stop() {
        clockLiveData.stop();
    }

    public boolean isClockRunning() {
        return clockLiveData.isRunning();
    }

}
