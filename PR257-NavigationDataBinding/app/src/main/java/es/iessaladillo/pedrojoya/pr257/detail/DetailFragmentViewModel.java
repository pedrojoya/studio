package es.iessaladillo.pedrojoya.pr257.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class DetailFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> _name = new MutableLiveData<>();

    public LiveData<String> getName() {
        return _name;
    }

    DetailFragmentViewModel(@NonNull Bundle arguments) {
        DetailFragmentArgs detailFragmentArgs = DetailFragmentArgs.fromBundle(arguments);
        _name.setValue(detailFragmentArgs.getName());
    }

}
