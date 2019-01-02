package es.iessaldillo.pedrojoya.pr191.ui.main;

import androidx.annotation.IdRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<Integer> currentOption = new MutableLiveData<>();

    LiveData<Integer> getCurrentOption() {
        return currentOption;
    }

    void setCurrentOption(@IdRes int currentOptionMenuItemResIsd) {
        currentOption.postValue(currentOptionMenuItemResIsd);
    }

}
