package es.iessaladillo.pedrojoya.pr017.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr017.data.local.Database;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Word> queryWords() {
        return database.queryWords();
    }

    @Override
    public void addWord(Word word) {
        database.insertWord(word);
    }

    @Override
    public void deleteWord(Word word) {
        database.deleteWord(word);
    }

}
