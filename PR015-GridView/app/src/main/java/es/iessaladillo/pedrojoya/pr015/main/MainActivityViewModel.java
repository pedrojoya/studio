package es.iessaladillo.pedrojoya.pr015.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr015.data.Repository;
import es.iessaladillo.pedrojoya.pr015.data.model.Word;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private ArrayList<Word> data;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<Word> getData() {
        if (data == null) {
            data = (ArrayList<Word>) repository.getWords();
        }
        return data;
    }

}
