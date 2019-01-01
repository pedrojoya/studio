package es.iessaladillo.pedrojoya.pr105.ui.main;

import androidx.annotation.IdRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<Integer> currentOption = new MutableLiveData<>();

    LiveData<Integer> getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(@IdRes int optionMenuItemResId) {
        currentOption.postValue(optionMenuItemResId);
    }

}
