package es.iessaladillo.pedrojoya.pr017.main;

import androidx.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr017.data.Repository;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private String loadedWord = "";
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

    public String getLoadedWord() {
        return loadedWord;
    }

    public void setLoadedWord(String loadedWord) {
        this.loadedWord = loadedWord;
    }

}
