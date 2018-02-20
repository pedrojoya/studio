package es.iessaladillo.pedrojoya.pr210.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

@SuppressWarnings("WeakerAccess")
public class DetailActivityViewModel extends DetailFragmentBaseViewModel {

    private final MutableLiveData<String> currentItem = new MutableLiveData<>();

    @Override
    public LiveData<String> getCurrentItem() {
        return currentItem;
    }

    @Override
    public void setCurrentItem(String item) {
        currentItem.setValue(item);
    }

}
