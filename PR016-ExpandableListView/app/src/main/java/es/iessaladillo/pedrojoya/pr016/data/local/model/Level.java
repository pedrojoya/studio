package es.iessaladillo.pedrojoya.pr016.data.local.model;

public class Level {

    private final long id;
    private final String name;

    public Level(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
