package es.iessaladillo.pedrojoya.pr017.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr017.data.Repository;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    @NonNull
    private String loadedWord = "";
    private List<Word> words;
    @NonNull private final Repository repository;

    public MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public List<Word> getWords() {
        if (words == null) {
            words = repository.queryWords();
        }
        return words;
    }

    @NonNull
    public String getLoadedWord() {
        return loadedWord;
    }

    public void setLoadedWord(@NonNull String loadedWord) {
        this.loadedWord = loadedWord;
    }

}
