package es.iessaladillo.pedrojoya.pr045.ui.main;

import android.arch.lifecycle.ViewModel;

class MainActivityViewModel extends ViewModel {

    private boolean detailOpen;

    boolean isDetailOpen() {
        return detailOpen;
    }

    void toggleDetailOpen() {
        detailOpen = !detailOpen;
    }

}
