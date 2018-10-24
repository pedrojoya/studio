package es.iessaladillo.pedrojoya.pr017.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr017.data.local.Database;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    @NonNull
    public List<Word> queryWords() {
        return database.queryWords();
    }

    @Override
    public void addWord(@NonNull Word word) {
        database.insertWord(word);
    }

    @Override
    public void deleteWord(@NonNull Word word) {
        database.deleteWord(word);
    }

}
