package es.iessaladillo.pedrojoya.pr134.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import es.iessaladillo.pedrojoya.pr134.base.BatteryStatusLiveData;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends AndroidViewModel {

    private final BatteryStatusLiveData batteryStatusLiveData;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        batteryStatusLiveData = BatteryStatusLiveData.getInstance(application);
    }

    BatteryStatusLiveData getBatteryStatusLiveData() {
        return batteryStatusLiveData;
    }

}
