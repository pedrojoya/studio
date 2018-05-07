package es.iessaladillo.pedrojoya.pr244;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

public class MainActivityViewModel extends ViewModel {

    public ObservableField<String> name = new ObservableField<>();
    public ObservableBoolean isValidForm = new ObservableBoolean(name) {

        @Override
        public void notifyChange() {
            boolean newValue = !TextUtils.isEmpty(name.get()) && name.get().length() > 1;
            if (get() != newValue) {
                set(newValue);
                super.notifyChange();
            }
        }

    };

//    public LiveData<Boolean> isValidForm = Transformations.switchMap(name, this::checkIsValidForm);
//
//    private LiveData<Boolean> checkIsValidForm(String name) {
//        MutableLiveData<Boolean> l = new MutableLiveData<Boolean>();
//        l.setValue(!TextUtils.isEmpty(name) && name.length() > 1);
//        return l;
//    }

//    public MainActivityViewModel() {
//        name.setValue("");
//    }

    public void reverse() {
//        name.postValue(new StringBuilder(name.getValue()).reverse().toString());
        name.set(new StringBuilder(name.get()).reverse().toString());
    }

}
