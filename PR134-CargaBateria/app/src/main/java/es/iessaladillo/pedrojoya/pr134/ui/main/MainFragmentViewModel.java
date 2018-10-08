package es.iessaladillo.pedrojoya.pr134.ui.main;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr134.base.BatteryStatus;
import es.iessaladillo.pedrojoya.pr134.base.BatteryStatusLiveData;
import es.iessaladillo.pedrojoya.pr134.base.ConnectionStatus;
import es.iessaladillo.pedrojoya.pr134.base.ConnectionStatusLiveData;

class MainFragmentViewModel extends ViewModel {

    private final LiveData<BatteryStatus> batteryStatusLiveData;
    private final LiveData<ConnectionStatus> connectionStatusLiveData;

    MainFragmentViewModel(Context context) {
        batteryStatusLiveData = new BatteryStatusLiveData(context);
        connectionStatusLiveData = new ConnectionStatusLiveData(context);
    }

    public LiveData<BatteryStatus> getBatteryStatusLiveData() {
        return batteryStatusLiveData;
    }

    public LiveData<ConnectionStatus> getConnectionStatusLiveData() {
        return connectionStatusLiveData;
    }

}
