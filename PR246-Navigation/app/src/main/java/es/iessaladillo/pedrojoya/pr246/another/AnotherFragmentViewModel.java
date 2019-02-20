package es.iessaladillo.pedrojoya.pr246.another;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class AnotherFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> _name = new MutableLiveData<>();

    public LiveData<String> getName() {
        return _name;
    }

    AnotherFragmentViewModel(@NonNull String initialName) {
        _name.setValue(initialName);
    }

}
