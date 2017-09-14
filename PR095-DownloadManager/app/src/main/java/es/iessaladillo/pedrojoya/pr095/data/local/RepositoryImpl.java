package es.iessaladillo.pedrojoya.pr095.data.local;

import java.util.List;

import es.iessaladillo.pedrojoya.pr095.data.model.Song;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Database database;

    private RepositoryImpl(Database database) {
        this.database = database;
    }

    public static RepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new RepositoryImpl(database);
        }
        return instance;
    }

    @Override
    public List<Song> getSongs() {
        return database.getSongs();
    }

}
