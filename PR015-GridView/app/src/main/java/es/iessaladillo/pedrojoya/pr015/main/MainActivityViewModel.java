package es.iessaladillo.pedrojoya.pr015.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr015.data.Repository;
import es.iessaladillo.pedrojoya.pr015.data.local.model.Word;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<Word> words;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Word> getWords() {
        if (words == null) {
            words = repository.queryWords();
        }
        return words;
    }

}
