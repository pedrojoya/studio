package es.iessaladillo.pedrojoya.pr173.ui.main;

import androidx.lifecycle.ViewModel;

class MainFragmentViewModel extends ViewModel {

    private int bsbState;

    MainFragmentViewModel(int bsbInitialState) {
        this.bsbState = bsbInitialState;
    }

    int getBsbState() {
        return bsbState;
    }

    void setBsbState(int bsbState) {
        this.bsbState = bsbState;
    }

}
