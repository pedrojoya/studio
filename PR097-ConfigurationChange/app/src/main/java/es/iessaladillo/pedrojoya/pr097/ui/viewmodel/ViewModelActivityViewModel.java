package es.iessaladillo.pedrojoya.pr097.ui.viewmodel;

import android.arch.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class ViewModelActivityViewModel extends ViewModel {
    private int count = 0;

    int getCount() {
        return count;
    }

    void increment() {
        count++;
    }
}
