package es.iessaladillo.pedrojoya.pr239.ui.main;

import androidx.lifecycle.ViewModel;

import es.iessaladillo.pedrojoya.pr239.data.remote.StockLiveData;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final StockLiveData stockLiveData = new StockLiveData();

    public StockLiveData getStockLiveData() {
        return stockLiveData;
    }

    public void start() {
        stockLiveData.addListener();
    }

    public void stop() {
        stockLiveData.stopListener();
    }

}
