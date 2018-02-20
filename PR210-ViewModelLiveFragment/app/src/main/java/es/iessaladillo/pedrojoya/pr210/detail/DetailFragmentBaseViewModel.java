package es.iessaladillo.pedrojoya.pr210.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public abstract class DetailFragmentBaseViewModel extends ViewModel {

    public abstract LiveData<String> getCurrentItem();

    @SuppressWarnings("unused")
    public abstract void setCurrentItem(String item);

}
