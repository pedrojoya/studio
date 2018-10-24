package es.iessaladillo.pedrojoya.pr015.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr015.data.Repository;
import es.iessaladillo.pedrojoya.pr015.data.local.model.Word;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<Word> words;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public List<Word> getWords() {
        if (words == null) {
            words = repository.queryWords();
        }
        return words;
    }

}
