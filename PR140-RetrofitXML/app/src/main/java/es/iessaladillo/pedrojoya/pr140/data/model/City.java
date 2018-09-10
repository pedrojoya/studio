package es.iessaladillo.pedrojoya.pr140.data.model;

import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public class City {

    private final String name;
    private final String code;

    public City(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

}
