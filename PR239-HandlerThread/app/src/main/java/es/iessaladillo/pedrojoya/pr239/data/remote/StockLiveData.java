package es.iessaladillo.pedrojoya.pr239.data.remote;

import android.arch.lifecycle.LiveData;

public class StockLiveData extends LiveData<Integer> {

    private final StockManager stockManager = new StockManager();
    private final StockManager.Listener listener = this::postValue;
    private boolean stopped = true;

    @Override
    protected void onActive() {
        super.onActive();
        if (!stopped) stockManager.addListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (!stopped) {
            stockManager.removeListener(listener);
        }
    }

    public void stopListener() {
        if (!stopped) {
            stockManager.removeListener(listener);
            stopped = true;
        }
    }

    public void addListener() {
        if (stopped) {
            stockManager.addListener(listener);
            stopped = false;
        }
    }

}
