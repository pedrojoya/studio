package es.iessaladillo.pedrojoya.pr092.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private ArrayList<String> data;

    public List<String> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

}
