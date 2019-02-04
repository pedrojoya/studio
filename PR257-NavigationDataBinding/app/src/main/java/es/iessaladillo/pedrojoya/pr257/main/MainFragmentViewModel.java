package es.iessaladillo.pedrojoya.pr257.main;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    public final MutableLiveData<String> name = new MutableLiveData<>();
    public LiveData<Boolean> validForm = Transformations.map(name,
        value -> !TextUtils.isEmpty(value));

    public MainFragmentDirections.ActionMainToDetail getActionMainToDetail() {
        String nameString = name.getValue();
        if (nameString == null) nameString = "";
        return MainFragmentDirections.actionMainToDetail(nameString);
    }

    public MainFragmentDirections.ActionMainToAnother getActionMainToAnother() {
        String nameString = name.getValue();
        if (nameString == null) nameString = "";
        return MainFragmentDirections.actionMainToAnother(nameString);
    }

}
