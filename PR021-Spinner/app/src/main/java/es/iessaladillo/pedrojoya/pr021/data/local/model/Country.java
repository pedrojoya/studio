package es.iessaladillo.pedrojoya.pr021.data.local.model;

public class Country {

    private final int flagResId;
    private final String name;

    public Country(int flagResId, String name) {
        this.flagResId = flagResId;
        this.name = name;
    }

    public int getFlagResId() {
        return flagResId;
    }

    public String getName() {
        return name;
    }
}
