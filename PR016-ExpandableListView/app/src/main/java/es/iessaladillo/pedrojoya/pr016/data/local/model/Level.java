package es.iessaladillo.pedrojoya.pr016.data.local.model;

public class Level {

    private long id;
    private final String name;

    @SuppressWarnings("WeakerAccess")
    public Level(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Level(String name) {
        this(0, name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
