package es.iessaladillo.pedrojoya.pr254.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class GalleryFragmentViewModel extends ViewModel {

    private final MutableLiveData<Integer> _value = new MutableLiveData<>();

    GalleryFragmentViewModel() {
        _value.setValue(0);
    }

    public LiveData<Integer> getValue() {
        return _value;
    }

    void incrementValue() {
        Integer currentValue = _value.getValue();
        if (currentValue == null) {
            currentValue = 0;
        }
        _value.setValue(currentValue + 1);
    }

}
