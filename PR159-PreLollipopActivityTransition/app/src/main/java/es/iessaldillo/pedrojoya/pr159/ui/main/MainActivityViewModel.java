package es.iessaldillo.pedrojoya.pr159.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaldillo.pedrojoya.pr159.data.Repository;
import es.iessaldillo.pedrojoya.pr159.data.model.Word;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<Word> data;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Word> getData() {
        if (data == null) {
            data = repository.getWords();
        }
        return data;
    }

}
