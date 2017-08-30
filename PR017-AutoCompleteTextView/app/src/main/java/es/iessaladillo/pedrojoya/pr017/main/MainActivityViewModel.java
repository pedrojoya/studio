package es.iessaladillo.pedrojoya.pr017.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr017.data.Repository;
import es.iessaladillo.pedrojoya.pr017.data.model.Word;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private String loadedWord = "";
    private ArrayList<Word> words;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<Word> getWords() {
        if (words == null) {
            words = (ArrayList<Word>) repository.getWords();
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
