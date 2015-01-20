package es.iessaladillo.pedrojoya.pr109.Model;

import java.util.List;

public class Resultado<T> {

    private List<T> results;

    public Resultado(List<T> results) {
        this.results = results;
    }

    public Resultado() {}

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
