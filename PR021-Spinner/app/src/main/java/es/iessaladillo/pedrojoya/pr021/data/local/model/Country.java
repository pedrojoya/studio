package es.iessaladillo.pedrojoya.pr021.data.local.model;

import androidx.annotation.NonNull;

public class Country {

    private final int flagResId;
    @NonNull private final String name;

    public Country(int flagResId, @NonNull String name) {
        this.flagResId = flagResId;
        this.name = name;
    }

    public int getFlagResId() {
        return flagResId;
    }

    @NonNull public String getName() {
        return name;
    }
}
