package es.iessaladillo.pedrojoya.pr118.datos;


import android.content.SearchRecentSuggestionsProvider;

public class BusquedaProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "es.iessaladillo.pedrojoya.pr118.busquedaprovider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public BusquedaProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}