package es.iessaladillo.pedrojoya.pr050.ui.photo;

import androidx.annotation.IdRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class PhotoFragmentViewModel extends ViewModel {

    private final MutableLiveData<Integer> effectId = new MutableLiveData<>();

    PhotoFragmentViewModel(@IdRes int defaultEffectId) {
        effectId.postValue(defaultEffectId);
    }

    LiveData<Integer> getEffectId() {
        return effectId;
    }

    void setEffectId(int effectResId) {
        effectId.postValue(effectResId);
    }

}
