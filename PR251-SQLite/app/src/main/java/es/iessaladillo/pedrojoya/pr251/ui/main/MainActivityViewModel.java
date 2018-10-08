package es.iessaladillo.pedrojoya.pr251.ui.main;

import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr251.base.Event;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<Event<Integer>> infoMessage = new MutableLiveData<>();

    public LiveData<Event<Integer>> getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(@StringRes int messageResId) {
        infoMessage.postValue(new Event<>(messageResId));
    }

}
