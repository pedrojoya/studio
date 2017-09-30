package es.iessaldillo.pedrojoya.pr159.data;

import java.util.List;

import es.iessaldillo.pedrojoya.pr159.data.model.Word;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Database database;

    private RepositoryImpl(Database database) {
        this.database = database;
    }

    public synchronized static RepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new RepositoryImpl(database);
        }
        return instance;
    }

    @Override
    public List<Word> getWords() {
        return database.getWords();
    }

    @Override
    public void addWord(Word word) {
        database.addWord(word);
    }

    @Override
    public void deleteWord(int position) {
        database.deleteWord(position);
    }

}
