package es.iessaladillo.pedrojoya.pr045.ui.main;

import androidx.lifecycle.ViewModel;

class MainActivityViewModel extends ViewModel {

    private boolean detailOpen;

    boolean isDetailOpen() {
        return detailOpen;
    }

    void toggleDetailOpen() {
        detailOpen = !detailOpen;
    }

}
